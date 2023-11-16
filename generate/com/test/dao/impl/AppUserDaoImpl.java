package com.test.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.mapper.AppUserMapper;
import com.test.dao.AppUserDao;
import com.test.DO.AppUser;
import cn.mybatis.mp.core.mvc.impl.DaoImpl;

/**
 * <p>
 * 微信用户 Dao 实现类
 * </p>
 *
 * @author 
 * @since 2023-11-16
 */
@Repository
public class AppUserDaoImpl extends DaoImpl<AppUser,Long> implements AppUserDao{

    @Autowired
    public AppUserDaoImpl (AppUserMapper appUserMapper){
        super(appUserMapper);
    }
}
