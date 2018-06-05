package io.sbed.modules.sys.service.impl;

import com.qiniu.util.StringUtils;
import io.sbed.modules.sys.dao.SysDeptDao;
import io.sbed.modules.sys.entity.SysDept;
import io.sbed.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

	@Autowired
	private SysDeptDao sysDeptDao;
	
	@Override
	public SysDept queryObject(Long deptId){
		return sysDeptDao.queryObject(deptId);
	}
	
	@Override
	public List<SysDept> queryList(Map<String, Object> map){
		return sysDeptDao.queryList(map);
	}
	
	@Override
	@Transactional
	public void save(SysDept sysDept){
		sysDeptDao.save(sysDept);
	}
	
	@Override
	@Transactional
	public void update(SysDept sysDept){
		sysDeptDao.update(sysDept);
	}
	
	@Override
	@Transactional
	public void delete(Long deptId){
		sysDeptDao.delete(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptDao.queryDetpIdList(parentId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	public void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
