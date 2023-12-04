package com.test.action;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.SysUserService;
import com.test.DO.SysUser;
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
@RequestMapping("/sysUserDao")
public class SysUserAction {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/get")
    public Object get(Integer id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/save")
    public Object save(SysUser sysUser){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/update")
    public Object update(SysUser sysUser){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/delete")
    public Object delete(Integer id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/find")
    public Object find(Pager<SysUser> pager){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

}
