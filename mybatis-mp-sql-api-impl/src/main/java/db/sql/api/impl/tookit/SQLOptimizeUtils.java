package db.sql.api.impl.tookit;

import db.sql.api.Cmd;
import db.sql.api.SqlBuilderContext;
import db.sql.api.cmd.JoinMode;
import db.sql.api.cmd.basic.CmdList;
import db.sql.api.cmd.basic.CountAll;
import db.sql.api.cmd.basic.SQL1;
import db.sql.api.cmd.basic.UnionsCmdLists;
import db.sql.api.cmd.executor.IQuery;
import db.sql.api.cmd.struct.Joins;
import db.sql.api.cmd.struct.query.IUnion;
import db.sql.api.cmd.struct.query.Unions;
import db.sql.api.impl.cmd.dbFun.Count;
import db.sql.api.impl.cmd.struct.Join;
import db.sql.api.impl.cmd.struct.Limit;
import db.sql.api.impl.cmd.struct.query.GroupBy;
import db.sql.api.impl.cmd.struct.query.OrderBy;
import db.sql.api.impl.cmd.struct.query.Select;
import db.sql.api.tookit.CmdUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 优化sql工具
 * 优化join
 * 优化count的order by 和 left join
 */
public final class SQLOptimizeUtils {

    private static boolean isCanRemoveLeftJoin(Join current, List<Join> joinList, boolean forClass, Map<Class, Cmd> classCmdMap) {
        for (Join j : joinList) {
            if (j == current) {
                continue;
            }
            if (j.getOn().contain(current.getSecondTable())) {
                return false;
            }
        }

        for (Map.Entry<Class, Cmd> entry : classCmdMap.entrySet()) {
            Class c = entry.getKey();
            //前面已经检测了
            if (c == Joins.class) {
                continue;
            }
            if (forClass && c == Select.class) {
                Select select = (Select) entry.getValue();
                if (select.isDistinct()) {
                    if (select.contain(current.getSecondTable())) {
                        //包含在distinct中 不行
                        return false;
                    }
                    continue;
                } else {
                    continue;
                }
            }
            if (entry.getValue().contain(current.getSecondTable())) {
                return false;
            }
        }
        return true;
    }

    private static boolean removeLeftJoin(List<Join> joinList, boolean forCount, Map<Class, Cmd> classCmdMap) {
        boolean hasLeftJoin = false;
        for (Join join : joinList) {
            if (join.getMode() == JoinMode.LEFT) {
                hasLeftJoin = true;
                break;
            }
        }

        if (!hasLeftJoin) {
            //未包含left join 不优化
            return false;
        }

        //循环中是否优化过
        boolean removeOne = false;

        int size = joinList.size();
        while (true) {
            boolean remove = false;
            for (int i = size - 1; i >= 0; i--) {
                //从后面扫描 因为从习惯上 最后一个是最容易被优化的
                Join join = joinList.get(i);
                if (join.getMode() == JoinMode.LEFT) {
                    //判断是否能删除
                    if (isCanRemoveLeftJoin(join, joinList, forCount, classCmdMap)) {
                        removeOne = true;
                        remove = true;
                        joinList.remove(i);
                        --size;
                        break;
                    }
                }
            }
            if (!remove) {
                //假如一个都没有优化，说明无法再优化了
                break;
            }
        }
        return removeOne;
    }

    private static void optimizedCmdList(Map<Class, Cmd> classCmdMap, boolean forCount, boolean optimizeOrderBy, boolean optimizeJoins, boolean isUnionQuery) {

        if (forCount) {
            if (!isUnionQuery) {
                //非union查询 可删除分页
                classCmdMap.remove(Limit.class);
            }
        }
        if (optimizeOrderBy) {
            if (isUnionQuery) {
                //union查询 没有分页 可删除order by
                if (!classCmdMap.containsKey(Limit.class)) {
                    classCmdMap.remove(OrderBy.class);
                }
            } else {
                //非union查询 可删除order by
                classCmdMap.remove(OrderBy.class);
            }
        }

        if (optimizeJoins) {
            //删选组件
            Joins joins = (Joins) classCmdMap.get(Joins.class);
            if (Objects.nonNull(joins)) {
                List<Join> joinList = new ArrayList<>(joins.getJoins());
                if (removeLeftJoin(joinList, forCount, classCmdMap)) {
                    if (joinList.isEmpty()) {
                        classCmdMap.remove(Joins.class);
                    } else {
                        classCmdMap.put(Joins.class, new Joins(joinList));
                    }
                }
            }
        }


        Unions unions = (Unions) classCmdMap.get(Unions.class);
        if (Objects.nonNull(unions) && (optimizeOrderBy || optimizeJoins)) {
            // 优化union
            // 无法优化 select 和 order by
            List<IUnion> unionList = unions.getUnions();
            List<CmdList> cmdListList = new ArrayList<>(unionList.size());
            for (IUnion union : unionList) {
                Map<Class, Cmd> unionCmdClassMap = new HashMap<>();
                List<Cmd> unionCmdList = union.getUnionQuery().cmds();
                unionCmdList.stream().forEach(cmd -> unionCmdClassMap.put(cmd.getClass(), cmd));
                optimizedCmdList(unionCmdClassMap, false, optimizeOrderBy, optimizeJoins, true);
                unionCmdList = (List<Cmd>) unionCmdClassMap.values().stream().sorted(union.getUnionQuery().comparator()).collect(Collectors.toList());
                CmdList cmdList = new CmdList(union.getOperator(), unionCmdList);
                cmdListList.add(cmdList);
            }
            classCmdMap.remove(Unions.class);
            classCmdMap.put(UnionsCmdLists.class, new UnionsCmdLists(cmdListList));
        }

        Select select = (Select) classCmdMap.get(Select.class);
        if (forCount && !isUnionQuery && !select.isDistinct()) {
            Select newSelect = new Select().select(SQL1.INSTANCE);
            classCmdMap.put(Select.class, newSelect);
        }
    }

    /**
     * 获取优化后的查询
     * 只优化left joins
     *
     * @param query   查询语句
     * @param context 构建SQL上下文
     * @return
     */
    public static StringBuilder getOptimizedSql(IQuery query, SqlBuilderContext context) {
        Map<Class, Cmd> classCmdMap = new HashMap<>();
        List<Cmd> cmdList = query.cmds();
        cmdList.stream().forEach(cmd -> classCmdMap.put(cmd.getClass(), cmd));
        optimizedCmdList(classCmdMap, false, false, true, classCmdMap.containsKey(Unions.class));
        cmdList = (List<Cmd>) classCmdMap.entrySet().stream().map(Map.Entry::getValue).sorted(query.comparator()).collect(Collectors.toList());
        return CmdUtils.join(context, new StringBuilder(), cmdList);
    }

    /**
     * 从一个query里获取count SQL
     *
     * @param query    查询语句
     * @param context  构建SQL上下文
     * @param optimize 是否优化
     * @return SQL StringBuilder
     */
    public static StringBuilder getCountSqlFromQuery(IQuery query, SqlBuilderContext context, boolean optimize) {
        if (!optimize) {
            //不优化直接包裹一层
            return new StringBuilder("SELECT COUNT(*) FROM (").append(CmdUtils.join(context, new StringBuilder(), query.sortedCmds())).append(") AS T");
        }
        return getOptimizedCountSql(query, context);
    }

    /**
     * 获取优化后的count sql
     *
     * @param query      查询语句
     * @param context    构建SQL上下文
     * @param sqlBuilder SQL拼接 StringBuilder
     * @return SQL StringBuilder
     */
    public static StringBuilder getOptimizedCountSql(IQuery query, SqlBuilderContext context) {
        Map<Class, Cmd> classCmdMap = new HashMap<>();
        List<Cmd> cmdList = query.cmds();
        cmdList.stream().forEach(cmd -> classCmdMap.put(cmd.getClass(), cmd));
        optimizedCmdList(classCmdMap, true, true, true, classCmdMap.containsKey(Unions.class));

        boolean needWarp = false;
        if (classCmdMap.containsKey(Unions.class) || classCmdMap.containsKey(UnionsCmdLists.class)) {
            //说明包含union查询
            needWarp = true;
        } else if (classCmdMap.containsKey(GroupBy.class)) {
            //包含分组查询
            needWarp = true;
        }

        if (!needWarp) {
            Select select = (Select) classCmdMap.get(Select.class);
            Select newSelect = new Select();
            if (select.isDistinct()) {
                newSelect.select(new Count(select));
            } else {
                newSelect.select(CountAll.INSTANCE);
            }
            classCmdMap.put(Select.class, newSelect);
        }
        cmdList = (List<Cmd>) classCmdMap.values().stream().sorted(query.comparator()).collect(Collectors.toList());
        if (needWarp) {
            return new StringBuilder("SELECT COUNT(*) FROM (").append(CmdUtils.join(context, new StringBuilder(), cmdList)).append(") AS T");
        }
        return CmdUtils.join(context, new StringBuilder(), cmdList);
    }
}
