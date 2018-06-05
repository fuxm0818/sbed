package io.sbed.modules.sys.service;

import io.sbed.modules.sys.entity.SysLog;

import java.util.List;
import java.util.Map;

public interface SysLogService {
	
	SysLog queryObject(Long id);
	
	List<SysLog> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysLog sysLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
