package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseUpdate;
import db.sql.api.impl.cmd.struct.Where;

import java.util.Objects;

/**
 * 更新链路
 */
public class UpdateChain extends BaseUpdate<UpdateChain> {

    protected final MybatisMapper mapper;

    public UpdateChain(MybatisMapper mapper) {
        this.mapper = mapper;
    }

    public UpdateChain(MybatisMapper mapper, Where where) {
        super(where);
        this.mapper = mapper;
    }

    public static UpdateChain of(MybatisMapper mapper) {
        return new UpdateChain(mapper);
    }

    public static UpdateChain of(MybatisMapper mapper, Where where) {
        return new UpdateChain(mapper, where);
    }

    private void setDefault() {
        if (Objects.isNull(this.getUpdateTable())) {
            //自动设置实体类
            this.update(mapper.getEntityType());
        }
    }

    /**
     * 执行
     *
     * @return
     */
    public int execute() {
        this.setDefault();
        return mapper.update(this);
    }
}
