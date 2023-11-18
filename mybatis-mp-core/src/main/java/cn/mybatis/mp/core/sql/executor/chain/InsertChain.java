package cn.mybatis.mp.core.sql.executor.chain;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.core.sql.executor.BaseInsert;

public class InsertChain extends BaseInsert<InsertChain> {

    public static InsertChain of(MybatisMapper mapper){
        return new InsertChain(mapper);
    }

    public InsertChain(MybatisMapper mapper) {
        this.mapper = mapper;
    }

    protected final MybatisMapper mapper;

    /**
     * 执行
     *
     * @return
     */
    public int execute() {
        return mapper.save(this);
    }
}
