package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MapperEntitys;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseUpdate;

/**
 * 更新链路
 */
public class UpdateChain extends BaseUpdate<UpdateChain> {

    public UpdateChain(MybatisMapper mapper) {
        this.mapper = mapper;
        this.entityType = MapperEntitys.get(mapper.getClass());
    }

    protected final MybatisMapper mapper;

    protected final Class<?> entityType;

    private void setDefault() {
        if (this.getUpdateTable() == null || this.updateTable.getTables() == null || this.updateTable.getTables().length == 0) {
            //自动设置实体类
            this.update(this.entityType);
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
