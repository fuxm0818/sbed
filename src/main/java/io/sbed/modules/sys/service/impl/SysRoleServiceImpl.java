package io.sbed.modules.sys.service.impl;

import io.sbed.common.shiro.ShiroRealm;
import io.sbed.modules.sys.dao.SysRoleDao;
import io.sbed.modules.sys.dao.SysUserDao;
import io.sbed.modules.sys.dao.SysUserRoleDao;
import io.sbed.modules.sys.entity.SysRole;
import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.service.SysRoleMenuService;
import io.sbed.modules.sys.service.SysRoleService;
import io.sbed.modules.sys.service.SysUserRoleService;
import io.sbed.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ShiroRealm shiroRealm;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysRole queryObject(Long id) {
        return sysRoleDao.queryObject(id);
    }

    @Override
    public List<SysRole> queryList(Map<String, Object> map) {
        return sysRoleDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysRoleDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysRole role) {
        role.setCreateTime(new Date());
        sysRoleDao.save(role);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
        //清除权限缓存
        this.clearCachedAuthz(role.getId());
    }

    @Override
    @Transactional
    public void update(SysRole role) {
        sysRoleDao.update(role);
        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
        //清除权限缓存
        this.clearCachedAuthz(role.getId());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userIds) {
        sysRoleDao.deleteBatch(userIds);
        //删除角色与菜单关系
        sysRoleMenuService.deleteBatch(userIds);
        //清除权限缓存
        for (long id : userIds) {
            SysUser user = sysUserDao.queryObject(id);
            if (null != user) {
                shiroRealm.clearAuthorizationInfoCache(user);
            }
        }
    }

    public void clearCachedAuthz(long roleId) {
        List<SysUser> users = sysUserRoleDao.queryUserList(roleId);
        for (SysUser user : users) {
            shiroRealm.clearAuthorizationInfoCache(user);
        }

    }

}
