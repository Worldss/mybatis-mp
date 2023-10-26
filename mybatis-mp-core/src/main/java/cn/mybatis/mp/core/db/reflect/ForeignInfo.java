package cn.mybatis.mp.core.db.reflect;

public class ForeignInfo {

    private Class foreignEntityClass;

    private FieldInfo fieldInfo;

    public ForeignInfo(Class foreignEntityClass,FieldInfo fieldInfo){
        this.foreignEntityClass=foreignEntityClass;
        this.fieldInfo=fieldInfo;
    }

    public Class getForeignEntityClass() {
        return foreignEntityClass;
    }

    public void setForeignEntityClass(Class foreignEntityClass) {
        this.foreignEntityClass = foreignEntityClass;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }
}
