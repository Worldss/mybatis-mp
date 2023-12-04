package com.test.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.mapper.SysRoleMapper;
import com.test.dao.SysRoleDao;
import com.test.DO.SysRole;
import cn.mybatis.mp.core.mvc.impl.DaoImpl;

/**
 * <p>
 *  Dao 实现类
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@Repository
public class SysRoleDaoImpl extends DaoImpl<SysRole,Integer> implements SysRoleDao{

    @Autowired
    public SysRoleDaoImpl (SysRoleMapper sysRoleMapper){
        super(sysRoleMapper);
    }
}
