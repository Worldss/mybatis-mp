package cn.mybatis.mp.spring.boot.demo.dao.impl;

import cn.mybatis.mp.core.mvc.impl.DaoImpl;
import cn.mybatis.mp.core.mybatis.mapper.context.Pager;
import cn.mybatis.mp.spring.boot.demo.DO.SysRole;
import cn.mybatis.mp.spring.boot.demo.DO.SysUser;
import cn.mybatis.mp.spring.boot.demo.dao.SysUserDao;
import cn.mybatis.mp.spring.boot.demo.mapper.SysUserMapper;
import cn.mybatis.mp.spring.boot.demo.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Dao 实现类
 * </p>
 *
 * @author
 * @since 2023-11-16
 */
@Repository
public class SysUserDaoImpl extends DaoImpl<SysUser, Integer> implements SysUserDao {

    @Autowired
    public SysUserDaoImpl(SysUserMapper sysUserMapper) {
        super(sysUserMapper);
    }

    @Override
    public SysUserVo getUserInfo(Integer id) {
        return queryChain()
                .select(SysUser.class)
                .select(SysRole.class)
                .select(SysUser::getName, c -> c.concat("").as("copy_name"))
                .from(SysUser.class)
                .join(SysUser.class, SysRole.class)
                .eq(SysUser::getId, id)
                .setReturnType(SysUserVo.class)
                .get();

    }

    @Override
    public <T> Pager<T> search(String name, Pager<T> pager, Class<T> returnType) {
        return queryChain()
                .select(SysUser.class)
                .select(SysRole.class)
                .select(SysUser::getName, c -> c.concat("").as("copy_name"))
                .from(SysUser.class)
                .join(SysUser.class, SysRole.class)
                .like(SysUser::getName, name)
                .setReturnType(returnType)
                .paging(pager);
    }
}
