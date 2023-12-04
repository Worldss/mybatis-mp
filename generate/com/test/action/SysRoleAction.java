package com.test.action;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.SysRoleService;
import com.test.DO.SysRole;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author 
 * @since 2023-12-04
 */
@RestController
@RequestMapping("/sysRoleDao")
public class SysRoleAction {

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/get")
    public Object get(Integer id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/save")
    public Object save(SysRole sysRole){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/update")
    public Object update(SysRole sysRole){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/delete")
    public Object delete(Integer id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/find")
    public Object find(Pager<SysRole> pager){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

}
