package com.test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.AppUserService;
import com.test.mapper.AppUserMapper;
import com.test.dao.AppUserDao;
import com.test.DO.AppUser;
import cn.mybatis.mp.core.sql.executor.chain.UpdateChain;
import cn.mybatis.mp.core.sql.executor.chain.QueryChain;
import cn.mybatis.mp.core.sql.executor.chain.InsertChain;
import cn.mybatis.mp.core.sql.executor.chain.DeleteChain;
import cn.mybatis.mp.core.mvc.impl.ServiceImpl;

/**
 * <p>
 * 微信用户 Service 实现类
 * </p>
 *
 * @author 
 * @since 2023-11-16
 */
@Service
public class AppUserServiceImpl extends ServiceImpl implements AppUserService{

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private AppUserMapper appUserMapper;

    private QueryChain queryChain() {
        return new QueryChain(appUserMapper);
    }

    private UpdateChain updateChain() {
        return new UpdateChain(appUserMapper);
    }

    private InsertChain insertChain(){
        return new InsertChain(appUserMapper);
    }

    private DeleteChain deleteChain(){
        return new DeleteChain(appUserMapper);
    }

}
