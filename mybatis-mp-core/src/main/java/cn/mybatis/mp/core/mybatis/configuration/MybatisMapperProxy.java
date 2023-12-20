package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.db.reflect.TableInfo;
import cn.mybatis.mp.core.db.reflect.Tables;
import cn.mybatis.mp.core.util.GenericUtil;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

public class MybatisMapperProxy<T> implements InvocationHandler {

    public final static String ENTITY_TYPE_METHOD_NAME = "getEntityType";
    public final static String MAPPER_TYPE_METHOD_NAME = "getMapperType";
    public final static String TABLE_INFO_METHOD_NAME = "getTableInfo";
    public final static String MAP_WITH_KEY_METHOD_NAME = "$mapWithKey";
    private final Object mapperProxy;
    private final Class<T> mapperInterface;
    private final SqlSession sqlSession;
    private Class<?> entityType;
    private TableInfo tableInfo;

    public MybatisMapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Object mapperProxy) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.mapperProxy = mapperProxy;
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
        if (method.isDefault()) {
            return Proxy.getInvocationHandler(mapperProxy).invoke(proxy, method, args);
        }
        if (method.getName().equals(ENTITY_TYPE_METHOD_NAME)) {
            return getEntityType();
        } else if (method.getName().equals(MAPPER_TYPE_METHOD_NAME)) {
            return this.mapperInterface;
        } else if (method.getName().equals(TABLE_INFO_METHOD_NAME)) {
            return getTableInfo();
        } else if (method.getName().equals(MAP_WITH_KEY_METHOD_NAME)) {
            return executeForMapWithKey(method, args);
        }

        return method.invoke(mapperProxy, args);
    }


    private <K, V> Map<K, V> executeForMapWithKey(Method method, Object[] args) {
        MapKeySQLCmdQueryContext queryContext = (MapKeySQLCmdQueryContext) args[0];
        String statementId = mapperInterface.getName() + "." + method.getName();
        Map result = sqlSession.selectMap(statementId, queryContext, queryContext.getKey());
        return result;
    }
}
