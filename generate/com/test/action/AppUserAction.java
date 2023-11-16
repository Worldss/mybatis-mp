package com.test.action;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.service.AppUserService;
import com.test.DO.AppUser;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;

/**
 * <p>
 * 微信用户 控制器
 * </p>
 *
 * @author 
 * @since 2023-11-16
 */
@RestController
@RequestMapping("/appUserDao")
public class AppUserAction {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/get")
    public Object get(Long id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/save")
    public Object save(AppUser appUser){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @PostMapping("/update")
    public Object update(AppUser appUser){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/delete")
    public Object delete(Long id){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

    @GetMapping("/find")
    public Object find(Pager<AppUser> pager){
        // TODO 代码自动生成 未实现（注意）
        return null;
    }

}
