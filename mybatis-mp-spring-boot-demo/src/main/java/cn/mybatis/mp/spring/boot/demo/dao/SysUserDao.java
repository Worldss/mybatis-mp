package cn.mybatis.mp.spring.boot.demo.dao;

import cn.mybatis.mp.core.mvc.Dao;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysUser;
import cn.mybatis.mp.spring.boot.demo.vo.SysUserVo;

/**
 * <p>
 * Dao 接口
 * </p>
 *
 * @author
 * @since 2023-11-16
 */
public interface SysUserDao extends Dao<SysUser, Integer> {

    SysUserVo getUserInfo(Integer id);

    <T> Pager<T> search(String name, Pager<T> pager, Class<T> returnType);
}
