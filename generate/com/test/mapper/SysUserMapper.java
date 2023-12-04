package com.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.test.DO.SysUser;
import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@Mapper
public interface SysUserMapper extends MybatisMapper<SysUser> {

}
