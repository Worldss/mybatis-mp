package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MapperEntitys;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.BaseQuery;

import java.util.List;

/**
 * 查询链路
 */
public class QueryChain extends BaseQuery<QueryChain> {

    public QueryChain(MybatisMapper mapper) {
        this.mapper = mapper;
        this.entityType = MapperEntitys.get(mapper.getClass());
    }

    protected final MybatisMapper mapper;

    protected final Class<?> entityType;

    private void setDefault() {
        if (this.select == null) {
            this.select(entityType);
        }
        if (this.from == null) {
            this.from(entityType);
        }
    }

    public <R> R get() {
        return (R) mapper.get(this, true);
    }

    public <R> List<R> list() {
        return mapper.list(this, true);
    }

    public Integer count() {
        return mapper.count(this);
    }

    public <R> Pager<R> paging(Pager<R> pager) {
        return mapper.paging(this, pager);
    }
}
