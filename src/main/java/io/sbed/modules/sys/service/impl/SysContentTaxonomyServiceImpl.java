package io.sbed.modules.sys.service.impl;

import io.sbed.common.Constant;
import io.sbed.modules.sys.dao.SysContentTaxonomyDao;
import io.sbed.modules.sys.entity.SysTaxonomy;
import io.sbed.modules.sys.service.SysContentTaxonomyService;
import io.sbed.modules.sys.service.SysTaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysContentTaxonomyService")
public class SysContentTaxonomyServiceImpl implements SysContentTaxonomyService {

	@Autowired
	private SysContentTaxonomyDao sysContentTaxonomyDao;

	@Autowired
	private SysTaxonomyService sysTaxonomyService;

	@Override
	@Transactional
	public void saveOrUpdate(Long contentId, List<Long> taxonomyIdList, Object[] tagNames) {
		//先删除内容与分类专题关系
		this.delete(contentId);

		if(taxonomyIdList.isEmpty() && tagNames==null){
			return ;
		}

		if(tagNames!=null){
			for(Object tagName:tagNames){
				SysTaxonomy sysTaxonomy=sysTaxonomyService.queryObjectBySlug(tagName.toString());
				if(sysTaxonomy==null){
					sysTaxonomy=new SysTaxonomy();
					sysTaxonomy.setName(tagName.toString());
					sysTaxonomy.setSlug(tagName.toString());
					sysTaxonomy.setType(Constant.TaxonomyType.TAG.getValue());
					sysTaxonomy.setCreateTime(new Date());
					sysTaxonomyService.save(sysTaxonomy);

				}
				taxonomyIdList.add(sysTaxonomy.getId());
			}
		}

		//保存内容与分类专题关系
		Map<String, Object> map = new HashMap<>();
		map.put("contentId", contentId);
		map.put("taxonomyIdList", taxonomyIdList);
		sysContentTaxonomyDao.save(map);
	}

	@Override
	public List<Long> queryTaxonomyIdList(Long contentId, Integer[] types) {
		return sysContentTaxonomyDao.queryTaxonomyIdList(contentId, types);
	}

	@Override
	public List<String> queryTaxonomyNameList(Long contentId, Integer[] types) {
		return sysContentTaxonomyDao.queryTaxonomyNameList(contentId, types);
	}

	@Override
	@Transactional
	public void delete(Long contentId) {
		sysContentTaxonomyDao.delete(contentId);
	}

	@Override
	public void deleteBatch(Long[] contentIds) {
		sysContentTaxonomyDao.deleteBatch(contentIds);
	}
}
