package com.test.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.mapper.SysUserMapper;
import com.test.dao.SysUserDao;
import com.test.DO.SysUser;
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
public class SysUserDaoImpl extends DaoImpl<SysUser,Integer> implements SysUserDao{

    @Autowired
    public SysUserDaoImpl (SysUserMapper sysUserMapper){
        super(sysUserMapper);
    }
}
