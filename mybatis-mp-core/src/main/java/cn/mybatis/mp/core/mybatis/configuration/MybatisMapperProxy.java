package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.util.GenericUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class MybatisMapperProxy<T> implements InvocationHandler {

    private final static String ENTITY_TYPE_METHOD_NAME = "getEntityType";
    private final static String MAPPER_TYPE_METHOD_NAME = "getMapperType";
    private final static String TABLE_INFO_METHOD_NAME = "getTableInfo";
    private final Object mapperProxy;
    private final Class<T> mapperInterface;
    private Class<?> entityType;
    private TableInfo tableInfo;

    public MybatisMapperProxy(Class<T> mapperInterface, Object mapperProxy) {
        this.mapperInterface = mapperInterface;
        this.mapperProxy = mapperProxy;
        this.entityType = GenericUtil.getGenericInterfaceClass(mapperInterface).get(0);
        this.tableInfo = Tables.get(this.entityType);
    }

    private Class<?> getEntityType() {
        if (Objects.isNull(this.entityType)) {
            this.entityType = GenericUtil.getGenericInterfaceClass(mapperInterface).get(0);
        }
        return this.entityType;
    }

    private TableInfo getTableInfo() {
        if (Objects.isNull(this.tableInfo)) {
            this.tableInfo = Tables.get(this.getEntityType());
        }
        return this.tableInfo;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals(ENTITY_TYPE_METHOD_NAME)) {
            return getEntityType();
        } else if (method.getName().equals(MAPPER_TYPE_METHOD_NAME)) {
            return this.mapperInterface;
        } else if (method.getName().equals(TABLE_INFO_METHOD_NAME)) {
            return getTableInfo();
        }
        if (method.isDefault()) {
            return Proxy.getInvocationHandler(mapperProxy).invoke(proxy, method, args);
        }
        return method.invoke(mapperProxy, args);
    }
}
