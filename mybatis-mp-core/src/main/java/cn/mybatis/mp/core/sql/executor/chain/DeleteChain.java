package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseDelete;
import db.sql.api.impl.cmd.struct.Where;

/**
 * 删除链路
 */
public class DeleteChain extends BaseDelete<DeleteChain> {

    protected final MybatisMapper mapper;

    public DeleteChain(MybatisMapper mapper) {
        this.mapper = mapper;
    }

    public DeleteChain(MybatisMapper mapper, Where where) {
        super(where);
        this.mapper = mapper;
    }

    public static DeleteChain of(MybatisMapper mapper) {
        return new DeleteChain(mapper);
    }

    public static DeleteChain of(MybatisMapper mapper, Where where) {
        return new DeleteChain(mapper, where);
    }

    private void setDefault() {
        if (this.getDeleteTable() == null && this.getFrom() == null) {
            //自动设置实体类
            this.delete(mapper.getEntityType());
            this.from(mapper.getEntityType());
        }
    }

    /**
     * 执行
     *
     * @return
     */
    public int execute() {
        this.setDefault();
        return mapper.delete(this);
    }
}
