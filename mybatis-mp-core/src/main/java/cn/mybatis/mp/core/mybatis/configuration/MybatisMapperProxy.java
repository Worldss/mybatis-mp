package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.util.GenericUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MybatisMapperProxy<T> implements InvocationHandler {

    private final Object mapperProxy;

    private final Class<T> mapperInterface;

    private final Class<?> entityType;

    private final static String ENTITY_TYPE_METHOD_NAME = "getEntityType";

    private final static String MAPPER_TYPE_METHOD_NAME = "getMapperType";

    public MybatisMapperProxy(Class<T> mapperInterface, Object mapperProxy) {
        this.mapperInterface = mapperInterface;
        this.mapperProxy = mapperProxy;
        this.entityType = GenericUtil.getGenericInterfaceClass(mapperInterface).get(0);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals(ENTITY_TYPE_METHOD_NAME)) {
            return this.entityType;
        } else if (method.getName().equals(MAPPER_TYPE_METHOD_NAME)) {
            return this.mapperInterface;
        }
        if (method.isDefault()) {
            return Proxy.getInvocationHandler(mapperProxy).invoke(proxy, method, args);
        }
        return method.invoke(mapperProxy, args);
    }
}
