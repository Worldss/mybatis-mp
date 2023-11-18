package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MapperEntitys;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseDelete;

/**
 * 删除链路
 */
public class DeleteChain extends BaseDelete<DeleteChain> {

    public static DeleteChain of(MybatisMapper mapper){
        return new DeleteChain(mapper);
    }

    public DeleteChain(MybatisMapper mapper) {
        this.mapper = mapper;
        this.entityType = MapperEntitys.get(mapper.getClass());
    }

    protected final MybatisMapper mapper;

    protected final Class<?> entityType;

    private void setDefault() {
        if (this.getDeleteTable() == null && this.getFrom() == null) {
            //自动设置实体类
            this.delete(this.entityType);
            this.from(this.entityType);
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
