package org.mybatis.mp.core.query;

import org.mybatis.mp.core.util.StringPool;
import org.mybatis.mp.db.annotations.ResultTable;
import org.mybatis.mp.db.annotations.Table;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class TableQuery<T> {

    private Class tableClass;

    public TableQuery() {
        super();


    }

    public Class getTableClass() {
        Class aClass = getClass();
        Type gSuperclass = aClass.getGenericSuperclass();
        if (gSuperclass instanceof ParameterizedType) {
            try {
                ParameterizedType pType = (ParameterizedType) gSuperclass;
                Type[] types = pType.getActualTypeArguments();
                for (Type type : types) {
                    Class desClass = (Class<T>) type;
                    System.out.println(desClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        TableQuery<StringPool> tableQuery =new TableQuery<StringPool>(){{

        }};
        System.out.println(tableQuery.getTableClass());
    }

}
