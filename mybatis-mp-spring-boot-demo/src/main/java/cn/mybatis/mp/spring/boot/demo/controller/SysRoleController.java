package cn.mybatis.mp.spring.boot.demo.controller;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.service.SysRoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RequestMapping("/sys/role")
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/save")
    public SysRole save(SysRole sysRole) {
        sysRole.setCreateTime(LocalDateTime.now());
        sysRoleService.save(sysRole);
        return sysRole;
    }

    @RequestMapping("/update")
    public SysRole update(SysRole sysRole) {
        sysRole.setCreateTime(LocalDateTime.now());
        sysRoleService.update(sysRole);
        return sysRole;
    }

    @RequestMapping("/delete/{id}")
    public int delete(@PathVariable Integer id) {
        return sysRoleService.delete(id);
    }


    @RequestMapping("/search")
    public Pager<SysRole> search(String name, Pager<SysRole> pager) {
        return sysRoleService.search(name, pager);
    }
}
