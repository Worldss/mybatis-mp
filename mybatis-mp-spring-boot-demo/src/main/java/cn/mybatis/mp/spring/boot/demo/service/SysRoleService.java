package cn.mybatis.mp.spring.boot.demo.service;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;

public interface SysRoleService {

    void save(SysRole sysRole);

    void update(SysRole sysRole);

    int delete(Integer id);

    Pager<SysRole> search(String name,Pager<SysRole> pager);
}
