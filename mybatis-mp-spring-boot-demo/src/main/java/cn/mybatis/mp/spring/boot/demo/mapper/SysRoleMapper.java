package cn.mybatis.mp.spring.boot.demo.mapper;

import cn.mybatis.mp.core.mybatis.mapper.MybatisMapper;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-11-16
 */
@Mapper
public interface SysRoleMapper extends MybatisMapper<SysRole> {

}
