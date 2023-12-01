package cn.mybatis.mp.core.util;

import cn.mybatis.mp.core.MybatisMpConfig;
import cn.mybatis.mp.core.db.reflect.TableFieldInfo;
import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.mybatis.mapper.BaseMapper;
import cn.mybatis.mp.core.sql.executor.BaseUpdate;
import cn.mybatis.mp.core.sql.executor.MybatisCmdFactory;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;
import cn.mybatis.mp.db.annotations.LogicDelete;
import db.sql.api.cmd.executor.method.condition.compare.Compare;
import db.sql.api.impl.cmd.basic.TableField;
import db.sql.api.impl.cmd.struct.Where;

import java.io.Serializable;
import java.util.Objects;

/**
 * 逻辑删除工具类
 */
public final class LogicDeleteUtil {
    /**
     * 是否需要逻辑删除
     *
     * @param tableInfo
     * @return
     */
    public static boolean isNeedLogicDelete(TableInfo tableInfo) {
        return MybatisMpConfig.isLogicDeleteSwitchOpen() && Objects.nonNull(tableInfo.getLogicDeleteFieldInfo());
    }


    /**
     * 获取删除后的值
     *
     * @param logicDeleteFieldInfo
     * @return
     */
    public static Object getLogicAfterValue(TableFieldInfo logicDeleteFieldInfo) {
        Object value;
        LogicDelete logicDelete=logicDeleteFieldInfo.getLogicDeleteAnnotation();
        Class type = logicDeleteFieldInfo.getField().getType();
        if (MybatisMpConfig.isDefaultValueKeyFormat(logicDelete.afterValue())) {
            value = MybatisMpConfig.getDefaultValue(type, logicDelete.afterValue());
        } else {
            value = DefaultValueConvertUtil.convert(logicDelete.afterValue(), type);
        }
        if (value == null) {
            throw new RuntimeException(String.format("Unable to obtain deleted value，please use MybatisMpConfig.setDefaultValue(\"s%\") to resolve it", logicDelete.afterValue()));
        }
        return value;
    }

    /**
     * 设置逻辑删除字段值  例如： set deleted=1
     *
     * @param baseUpdate
     * @param entity
     * @param tableInfo
     */
    public static void setLogicDeleteUpdate(BaseUpdate baseUpdate, Class entity, TableInfo tableInfo) {
        TableField logicDeleteTableField = baseUpdate.$().field(entity, tableInfo.getLogicDeleteFieldInfo().getField().getName(), 1);
        baseUpdate.set(logicDeleteTableField, getLogicAfterValue(tableInfo.getLogicDeleteFieldInfo()));
    }

    /**
     * 根据ID 进行逻辑删除操作
     *
     * @param baseMapper
     * @param entityType
     * @param tableInfo
     * @param id
     * @return
     */
    public static int logicDelete(BaseMapper baseMapper, Class entityType, TableInfo tableInfo, Serializable id) {
        return UpdateChain.of(baseMapper)
                .update(entityType)
                .connect(self -> {
                    LogicDeleteUtil.setLogicDeleteUpdate(self, entityType, tableInfo);
                    self.eq(self.$().field(entityType, tableInfo.getIdFieldInfo().getField().getName(), 1), id);
                })
                .execute();
    }

    /**
     * 根据where 执行逻辑删除操作
     *
     * @param baseMapper
     * @param entityType
     * @param tableInfo
     * @param where
     * @return
     */
    public static int logicDelete(BaseMapper baseMapper, Class entityType, TableInfo tableInfo, Where where) {
        return UpdateChain.of(baseMapper, where)
                .update(entityType)
                .connect(self -> {
                    LogicDeleteUtil.setLogicDeleteUpdate(self, entityType, tableInfo);
                })
                .execute();
    }


    /**
     * 添加租户条件
     *
     * @param compare
     * @param cmdFactory
     * @param entity
     * @param storey
     */
    public static final void addLogicDeleteCondition(Compare compare, MybatisCmdFactory cmdFactory, Class entity, int storey) {
        if (!MybatisMpConfig.isLogicDeleteSwitchOpen()) {
            return;
        }
        TableInfo tableInfo = Tables.get(entity);
        if (Objects.isNull(tableInfo.getLogicDeleteFieldInfo())) {
            return;
        }

        Object logicBeforeValue = tableInfo.getLogicDeleteFieldInfo().getLogicDeleteInitValue();
        TableField tableField = cmdFactory.field(entity, tableInfo.getLogicDeleteFieldInfo().getField().getName(), storey);
        if (Objects.isNull(logicBeforeValue)) {
            compare.isNull(tableField);
        } else {
            compare.ne(tableField, logicBeforeValue);
        }
    }
}
