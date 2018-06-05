package io.sbed.modules.sys.service;

import io.sbed.modules.sys.entity.SysContent;
import java.util.List;
import java.util.Map;

/**
 * @author heguoliang
 * @Description: TODO(内容管理)
 * @date 2017-08-29 10:44:09
 */
public interface SysContentService {
	
	SysContent queryObject(Long id);
	
	List<SysContent> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysContent content);
	
	void update(SysContent content);
	
	void deleteBatch(Long[] ids);

}
