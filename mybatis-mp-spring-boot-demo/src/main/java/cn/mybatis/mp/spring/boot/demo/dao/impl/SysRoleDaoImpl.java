package cn.mybatis.mp.spring.boot.demo.dao.impl;

import cn.mybatis.mp.core.mvc.impl.DaoImpl;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.dao.SysRoleDao;
import cn.mybatis.mp.spring.boot.demo.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Dao 实现类
 * </p>
 *
 * @author
 * @since 2023-11-16
 */
@Repository
public class SysRoleDaoImpl extends DaoImpl<SysRole, Integer> implements SysRoleDao {

    @Autowired
    public SysRoleDaoImpl(SysRoleMapper sysRoleMapper) {
        super(sysRoleMapper);
    }

    @Override
    public Pager<SysRole> search(String name, Pager<SysRole> pager) {
        return queryChain().like(SysRole::getName, name).paging(pager);
    }
}
