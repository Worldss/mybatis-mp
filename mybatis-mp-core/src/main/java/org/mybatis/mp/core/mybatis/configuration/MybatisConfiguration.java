package org.mybatis.mp.core.mybatis.configuration;


import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.mybatis.mp.core.mybatis.mapper.MapperTables;
import org.mybatis.mp.core.mybatis.provider.TableSQLProvider;

import java.lang.reflect.Field;


public class MybatisConfiguration extends Configuration {

    /**
     * 列名否是下划线命名
     */
    private boolean columnUnderline = true;

    /**
     * 表名否是下划线命名
     */
    private boolean tableUnderline = true;

    public MybatisConfiguration(Environment environment) {
        super(environment);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        if (ms.getSqlSource() instanceof ProviderSqlSource) {
            MetaObject metaObject = newMetaObject(ms.getSqlSource());
            Class providerType = (Class) metaObject.getValue("providerType");
            if (providerType == TableSQLProvider.class) {
                TableIdGeneratorWrapper.addEntityKeyGenerator(ms, getEntityClass(ms));
            }
        }
        ResultMapWrapper.replaceResultMap(ms);
        super.addMappedStatement(ms);
    }


    public <T> void addMapper(Class<T> type) {
        if (MapperTables.add(type)) {

        }
        super.addMapper(type);
    }

    private String getMapperName(MappedStatement ms) {
        return ms.getId().substring(0, ms.getId().lastIndexOf("."));
    }

    private Class getEntityClass(MappedStatement ms) {
        return MapperTables.get(getMapperName(ms));
    }

    public ResultMapping buildResultMapping(Field property, String columnName, JdbcType jdbcType, Class<? extends TypeHandler<?>> typeHandlerClass) {
        return new ResultMapping.Builder(this, property.getName())
                .column(columnName)
                .javaType(property.getType())
                .jdbcType(jdbcType)
                .typeHandler(this.buildTypeHandler(property, typeHandlerClass))
                .build();
    }

    public TypeHandler buildTypeHandler(Field property, Class<? extends TypeHandler<?>> typeHandlerClass) {
        if (typeHandlerClass == UnknownTypeHandler.class) {
            TypeHandler typeHandler = this.getTypeHandlerRegistry().getTypeHandler(property.getType());
            if (typeHandler != null) {
                return null;
            }
        }

        TypeHandler typeHandler = this.getTypeHandlerRegistry().getMappingTypeHandler(typeHandlerClass);
        if (typeHandler != null) {
            return typeHandler;
        }
        typeHandler = this.getTypeHandlerRegistry().getInstance(property.getType(), typeHandlerClass);
        this.getTypeHandlerRegistry().register(typeHandler);
        return typeHandler;
    }


    public boolean isColumnUnderline() {
        return columnUnderline;
    }

    public void setColumnUnderline(boolean columnUnderline) {
        this.columnUnderline = columnUnderline;
    }

    public boolean isTableUnderline() {
        return tableUnderline;
    }

    public void setTableUnderline(boolean tableUnderline) {
        this.tableUnderline = tableUnderline;
    }
}



