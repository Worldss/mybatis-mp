package cn.mybatis.mp.spring.boot.demo.service.impl;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.dao.SysRoleDao;
import cn.mybatis.mp.spring.boot.demo.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    @Transactional
    public void save(SysRole sysRole) {
        sysRoleDao.save(sysRole);
    }

    @Override
    @Transactional
    public void update(SysRole sysRole) {
        sysRoleDao.update(sysRole);
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        return sysRoleDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Pager<SysRole> search(String name, Pager<SysRole> pager) {
        return sysRoleDao.search(name, pager);
    }
}
