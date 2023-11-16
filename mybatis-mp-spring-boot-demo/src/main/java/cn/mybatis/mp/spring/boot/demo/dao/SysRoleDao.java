package cn.mybatis.mp.spring.boot.demo.dao;

import cn.mybatis.mp.core.mvc.Dao;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;

/**
 * <p>
 * Dao 接口
 * </p>
 *
 * @author
 * @since 2023-11-16
 */
public interface SysRoleDao extends Dao<SysRole, Integer> {

    Pager<SysRole> search(String name, Pager<SysRole> pager);
}
