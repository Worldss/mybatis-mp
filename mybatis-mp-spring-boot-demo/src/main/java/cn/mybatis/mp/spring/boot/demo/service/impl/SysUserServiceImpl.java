package cn.mybatis.mp.spring.boot.demo.service.impl;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.core.sql.executor.Query;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.DO.SysUser;
import cn.mybatis.mp.spring.boot.demo.mapper.SysUserMapper;
import cn.mybatis.mp.spring.boot.demo.service.SysUserService;
import cn.mybatis.mp.spring.boot.demo.vo.SysUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void save(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUserMapper.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public int delete(Integer id) {
        return sysUserMapper.deleteById(id);
    }

    @Override
    public SysUser get(Integer id) {
        return sysUserMapper.getById(id);
    }

    @Override
    public SysUserVo getUserInfo(Integer id) {
        return sysUserMapper.get(new Query()
                .select(SysUser.class)
                .select(SysRole.class)
                .select(SysUser::getName, c -> c.concat("").as("copy_name"))
                .from(SysUser.class)
                .join(SysUser.class, SysRole.class)
                .eq(SysUser::getId, id)
                .setReturnType(SysUserVo.class)
        );
    }

    @Override
    public Pager<SysUserVo> search(String name, Pager<SysUserVo> pager, Class returnType) {
        return sysUserMapper.paging(new Query()
                        .select(SysUser.class)
                        .select(SysRole.class)
                        .select(SysUser::getName, c -> c.concat("").as("copy_name"))
                        .from(SysUser.class)
                        .join(SysUser.class, SysRole.class)
                        .like(SysUser::getName, name)
                        .setReturnType(returnType)
                , pager);
    }
}
