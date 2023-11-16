package com.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.test.DO.AppUser;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;

/**
 * <p>
 * 微信用户 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-11-16
 */
@Mapper
public interface AppUserMapper extends MybatisMapper<AppUser> {

}
