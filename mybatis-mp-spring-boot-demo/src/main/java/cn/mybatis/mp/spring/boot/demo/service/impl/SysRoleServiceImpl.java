package cn.mybatis.mp.spring.boot.demo.service.impl;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.mapper.SysRoleMapper;
import cn.mybatis.mp.spring.boot.demo.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional
    public void save(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    @Transactional
    public void update(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        return sysRoleMapper.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Pager<SysRole> search(String name, Pager<SysRole> pager) {
        return sysRoleMapper.paging(new Query().like(SysRole::getName, name), pager);
    }
}
