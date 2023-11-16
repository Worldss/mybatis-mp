package cn.mybatis.mp.spring.boot.demo.service.impl;

import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysUser;
import cn.mybatis.mp.spring.boot.demo.dao.SysUserDao;
import cn.mybatis.mp.spring.boot.demo.service.SysUserService;
import cn.mybatis.mp.spring.boot.demo.vo.SysUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public void save(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUserDao.save(sysUser);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserDao.update(sysUser);
    }

    @Override
    public int delete(Integer id) {
        return sysUserDao.deleteById(id);
    }

    @Override
    public SysUser get(Integer id) {
        return sysUserDao.getById(id);
    }

    @Override
    public SysUserVo getUserInfo(Integer id) {
        return sysUserDao.getUserInfo(id);
    }

    @Override
    public <T> Pager<T> search(String name, Pager<T> pager, Class<T> returnType) {
        return sysUserDao.search(name, pager, returnType);
    }
}
