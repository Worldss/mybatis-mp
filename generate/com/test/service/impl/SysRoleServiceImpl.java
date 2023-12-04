package com.test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.SysRoleService;
import com.test.mapper.SysRoleMapper;
import com.test.dao.SysRoleDao;
import com.test.DO.SysRole;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.sql.executor.chain.InsertChain;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.mvc.impl.ServiceImpl;

/**
 * <p>
 *  Service 实现类
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl implements SysRoleService{

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    private QueryChain queryChain() {
        return QueryChain.of(sysRoleMapper);
    }

    private UpdateChain updateChain() {
        return UpdateChain.of(sysRoleMapper);
    }

    private InsertChain insertChain(){
        return InsertChain.of(sysRoleMapper);
    }

    private DeleteChain deleteChain(){
        return DeleteChain.of(sysRoleMapper);
    }

}
