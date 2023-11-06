package cn.mybatis.mp.spring.boot.demo.service;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;

import java.util.List;

public interface SysRoleService {

    void save(SysRole sysRole);

    void update(SysRole sysRole);

    int delete(Integer id);

    List<SysRole> all();

    Pager<SysRole> search(String name,Pager<SysRole> pager);
}
