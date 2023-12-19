package db.sql.api.cmd.struct;

public interface IWhereIgnoreMethod<SELF> {
    /**
     * 构建条件时忽略null值
     *
     * @param bool 是否忽略
     * @return 当前类
     */
    SELF ignoreNullValueInCondition(boolean bool);

    /**
     * 构建条件时忽略空字符串
     *
     * @param bool 是否忽略
     * @return 当前类
     */
    SELF ignoreEmptyInCondition(boolean bool);

    /**
     * 构建条件时,是否对字符串进行trim
     *
     * @param bool 是否进行trim
     * @return 当前类
     */
    SELF trimStringInCondition(boolean bool);
}
