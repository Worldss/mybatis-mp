package cn.mybatis.mp.core.mybatis.configuration;

import cn.mybatis.mp.core.mybatis.mapper.context.SQLCmdContext;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Supplier;

public class PreparedParameterHandler implements ParameterHandler {

    private final SQLCmdContext cmdContext;

    private final MybatisConfiguration configuration;

    public PreparedParameterHandler(MybatisConfiguration configuration, SQLCmdContext cmdContext) {
        this.cmdContext = cmdContext;
        this.configuration = configuration;
    }

    @Override    public Object getParameterObject() {
        return cmdContext;
    }

    @Override    public void setParameters(PreparedStatement ps) throws SQLException {
        Object[] params = cmdContext.getSQLCmdParams();
        int length = params.length;
        for (int i = 0; i < length; i++) {
            Object value = params[i];
            if (value instanceof MybatisParameter) {
                MybatisParameter parameter = (MybatisParameter) value;
                Object realValue = parameter.getValue();
                if (value instanceof Supplier) {
                    realValue = ((Supplier) value).get();
                }
                TypeHandler typeHandler = this.configuration.buildTypeHandler(realValue.getClass(), parameter.getTypeHandler());
                typeHandler.setParameter(ps, i + 1, realValue, parameter.getJdbcType());
            } else {
                TypeHandler typeHandler = configuration.getTypeHandlerRegistry().getTypeHandler(value.getClass());
                if (typeHandler != null) {
                    typeHandler.setParameter(ps, i + 1, value, JdbcType.UNDEFINED);
                } else {
                    ps.setObject(i + 1, value);
                }
            }
        }
    }
}
