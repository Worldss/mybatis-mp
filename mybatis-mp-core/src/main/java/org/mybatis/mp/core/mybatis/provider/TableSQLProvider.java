package org.mybatis.mp.core.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.util.MapUtil;
import org.mybatis.mp.core.db.reflect.FieldInfo;
import org.mybatis.mp.core.db.reflect.TableInfo;
import org.mybatis.mp.core.db.reflect.TableInfos;
import org.mybatis.mp.core.mybatis.mapper.MapperTables;
import org.mybatis.mp.db.IdAutoType;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public class TableSQLProvider {

    public static final String SAVE_NAME = "save";
    public static final String UPDATE_NAME = "update";
    public static final String GET_BY_ID_NAME = "getById";
    public static final String DELETE_BY_ID_NAME = "deleteById";
    private static final Map<String, String> SQL_CACHE_MAP = new ConcurrentHashMap<>();

    private TableSQLProvider() {

    }

    public static String save(Object entity, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(entity.getClass());
        String sql = new SQL() {{
            INSERT_INTO(tableInfo.getBasic().getSchemaAndTableName());
            Predicate<FieldInfo> filter = (item) -> {
                if (item.getValue(entity) != null) {
                    return true;
                }
                if (item.isId()) {
                    return item.getIdAnnotation().value() != IdAutoType.AUTO && item.getIdAnnotation().executeBefore();
                }
                return false;
            };
            tableInfo.getFieldInfos().stream().filter(filter).forEach(item -> {
                VALUES(item.getColumnName(), "#{" + item.getReflectField().getName() + "}");
            });
        }}.toString();

        System.out.println(sql);
        return sql;
    }

    public static String update(Object entity, ProviderContext context) {
        return "";
    }

    private static SQL getTableDefaultSelect(TableInfo tableInfo) {
        return new SQL() {{
            Predicate<FieldInfo> filter = (item) -> {
                return item.getFieldAnnotation() == null || item.getFieldAnnotation().select();
            };
            FROM(tableInfo.getBasic().getSchemaAndTableName());
            tableInfo.getFieldInfos().stream().filter(filter).forEach(item -> {
                SELECT(item.getColumnName());
            });
        }};
    }


    private static String getByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasic().getType() + ".getById", (key) -> {
            return getTableDefaultSelect(tableInfo).WHERE(tableInfo.getIdInfo().getColumnName() + "=#{value}").toString();
        });
    }


    public static String getById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        if (tableInfo.getIdInfo() == null) {
            throw new RuntimeException("ID not found");
        }
        return getByIdSql(tableInfo);
    }

    private static String getDeleteByIdSql(TableInfo tableInfo) {
        return MapUtil.computeIfAbsent(SQL_CACHE_MAP, tableInfo.getBasic().getType() + ".deleteById", (key) -> {
            return new SQL() {{
                DELETE_FROM(tableInfo.getBasic().getSchemaAndTableName());
                WHERE(tableInfo.getIdInfo().getColumnName() + "=#{value}");
            }}.toString();
        });
    }


    public static String deleteById(Serializable id, ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        if (Objects.isNull(tableInfo.getIdInfo())) {
            throw new RuntimeException("ID not found");
        }
        return getDeleteByIdSql(tableInfo);
    }

    public static String all(ProviderContext context) {
        TableInfo tableInfo = TableInfos.get(MapperTables.get(context.getMapperType()));
        return getTableDefaultSelect(tableInfo).toString();
    }
}
