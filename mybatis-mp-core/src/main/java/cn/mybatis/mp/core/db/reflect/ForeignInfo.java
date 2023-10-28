package cn.mybatis.mp.core.db.reflect;

public class ForeignInfo {

    private final Class foreignEntity;

    private final TableFieldInfo tableFieldInfo;

    public ForeignInfo(Class foreignEntity, TableFieldInfo tableFieldInfo) {
        this.foreignEntity = foreignEntity;
        this.tableFieldInfo = tableFieldInfo;
    }

    public Class getForeignEntity() {
        return foreignEntity;
    }

    public TableFieldInfo getTableFieldInfo() {
        return tableFieldInfo;
    }
}
