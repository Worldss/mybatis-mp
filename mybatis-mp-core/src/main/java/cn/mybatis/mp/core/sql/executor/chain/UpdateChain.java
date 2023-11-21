package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseUpdate;

/**
 * 更新链路
 */
public class UpdateChain extends BaseUpdate<UpdateChain> {

    public static UpdateChain of(MybatisMapper mapper) {
        return new UpdateChain(mapper);
    }

    public UpdateChain(MybatisMapper mapper) {
        this.mapper = mapper;
    }

    protected final MybatisMapper mapper;


    private void setDefault() {
        if (this.getUpdateTable() == null || this.updateTable.getTables() == null || this.updateTable.getTables().length == 0) {
            //自动设置实体类
            this.update(mapper.getMapperType());
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
