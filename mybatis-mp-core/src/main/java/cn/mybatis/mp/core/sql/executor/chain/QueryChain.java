package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.BaseMapper;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.BaseQuery;
import db.sql.api.impl.cmd.struct.Where;
import org.apache.ibatis.cursor.Cursor;

import java.util.List;
import java.util.Objects;

/**
 * 查询链路
 */
public class QueryChain extends BaseQuery<QueryChain> {

    protected final BaseMapper mapper;

    public QueryChain(BaseMapper mapper) {
        this.mapper = mapper;
    }

    public QueryChain(BaseMapper mapper, Where where) {
        super(where);
        this.mapper = mapper;
    }

    public static QueryChain of(BaseMapper mapper) {
        return new QueryChain(mapper);
    }

    public static QueryChain of(BaseMapper mapper, Where where) {
        return new QueryChain(mapper, where);
    }

    private void setDefault() {
        if (Objects.isNull(this.select)) {
            this.select(mapper.getEntityType());
        }
        if (Objects.isNull(this.from)) {
            this.from(mapper.getEntityType());
        }
        if (Objects.isNull(this.returnType)) {
            this.setReturnType(mapper.getEntityType());
        }
    }

    /**
     * 获取单个对象
     *
     * @param <R>
     * @return
     */
    public <R> R get() {
        return this.get(true);
    }

    /**
     * 获取单个对象
     *
     * @param optimize 是否自动优化
     * @param <R>
     * @return
     */
    public <R> R get(boolean optimize) {
        this.setDefault();
        return (R) mapper.get(this, optimize);
    }

    /**
     * 获取列表
     *
     * @param <R>
     * @return
     */
    public <R> List<R> list() {
        return this.list(true);
    }

    /**
     * 获取列表
     *
     * @param optimize 是否自动优化
     * @param <R>
     * @return
     */
    public <R> List<R> list(boolean optimize) {
        this.setDefault();
        return mapper.list(this, optimize);
    }

    /**
     * 获取列表
     *
     * @param <R>
     * @return
     */
    public <R> Cursor<R> cursor() {
        return this.cursor(true);
    }

    /**
     * 获取列表
     *
     * @param optimize 是否自动优化
     * @param <R>
     * @return
     */
    public <R> Cursor<R> cursor(boolean optimize) {
        this.setDefault();
        return mapper.cursor(this, optimize);
    }


    /**
     * 获取条数
     *
     * @return
     */
    public Integer count() {
        if (this.select == null) {
            this.selectCountAll();
        }
        this.setDefault();
        return mapper.count(this);
    }

    /**
     * 判断是否存在
     *
     * @return
     */
    public boolean exists() {
        return this.exists(true);
    }

    /**
     * 判断是否存在
     *
     * @param optimize 是否自动优化
     * @return
     */
    public boolean exists(boolean optimize) {
        if (this.select == null) {
            this.select1();
        }
        this.limit(1);
        this.setDefault();
        return mapper.exists(this, optimize);
    }

    /**
     * 分页查询
     *
     * @param pager
     * @param <R>
     * @return
     */
    public <R> Pager<R> paging(Pager<R> pager) {
        this.setDefault();
        return mapper.paging(this, pager);
    }
}
