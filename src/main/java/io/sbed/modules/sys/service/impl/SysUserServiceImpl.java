package io.sbed.modules.sys.service.impl;

import io.sbed.common.Constant;
import io.sbed.modules.sys.dao.SysMenuDao;
import io.sbed.modules.sys.dao.SysUserDao;
import io.sbed.modules.sys.entity.SysMenu;
import io.sbed.modules.sys.entity.SysUser;
import io.sbed.modules.sys.service.SysRoleService;
import io.sbed.modules.sys.service.SysUserRoleService;
import io.sbed.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        SysUser sysUser = sysUserDao.queryByUserName(username);
        return sysUser;
    }

    @Override
    public SysUser queryObject(Long id) {
        SysUser sysUser = sysUserDao.queryObject(id);
        return sysUser;
    }

    @Override
    public List<SysUser> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
        user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        sysUserDao.save(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());

    }

    @Override
    @Transactional
    public void update(SysUser user) {

        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserDao.update(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids) {
        sysUserDao.deleteBatch(ids);

        //删除用户与角色关系
        sysUserRoleService.deleteBatch(ids);
    }

    @Override
    @Transactional
    public int updatePassword(SysUser user, String password, String newPassword) {

        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("password", password);
        map.put("newPassword", newPassword);
        return sysUserDao.updatePassword(map);
    }

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenu> menuList = sysMenuDao.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

}
