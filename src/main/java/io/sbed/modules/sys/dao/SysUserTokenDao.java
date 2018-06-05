package io.sbed.modules.sys.dao;

import io.sbed.modules.sys.entity.SysUserToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserToken> {
    
    SysUserToken queryByUserId(Long userId);

    SysUserToken queryByToken(String token);
	
}
