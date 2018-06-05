package io.sbed.modules.sys.controller;

import io.sbed.common.Constant;
import io.sbed.common.annotation.SysLog;
import io.sbed.common.exception.SbedException;
import io.sbed.common.utils.PageUtils;
import io.sbed.common.utils.Query;
import io.sbed.common.utils.Result;
import io.sbed.modules.sys.entity.SysContent;
import io.sbed.modules.sys.service.SysContentService;
import io.sbed.modules.sys.service.SysContentTaxonomyService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author heguoliang
 * @Description: TODO(内容管理)
 * @date 2017-08-29 10:44:09
 */
@RestController
@RequestMapping("/sys/content")
public class SysContentController extends AbstractController{

	@Autowired
	private SysContentService sysContentService;

	@Autowired
	private SysContentTaxonomyService sysContentTaxonomyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:content:list")
	public Result list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysContent> contentList = sysContentService.queryList(query);
		int total = sysContentService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(contentList, total, query.getLimit(), query.getPage());
		
		return Result.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:content:info")
	public Result info(@PathVariable("id") Long id){
		SysContent content = sysContentService.queryObject(id);
		//查询角色对应的菜单
		Integer[] types={Constant.TaxonomyType.CATEGORY.getValue(), Constant.TaxonomyType.FEATURE.getValue()};
		Integer[] tagType={Constant.TaxonomyType.TAG.getValue()};
		List<Long> taxonomyIdList = sysContentTaxonomyService.queryTaxonomyIdList(id, types);
		List<String> tagNames = sysContentTaxonomyService.queryTaxonomyNameList(id, tagType);
		content.setTaxonomyIdList(taxonomyIdList);
		content.setTagNames(tagNames.toArray());
		return Result.ok().put("content", content);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存内容")
	@RequestMapping("/save")
	@RequiresPermissions("sys:content:save")
	public Result save(@RequestBody SysContent content){
		verifyForm(content);
		sysContentService.save(content);
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改内容")
	@RequestMapping("/update")
	@RequiresPermissions("sys:content:update")
	public Result update(@RequestBody SysContent content){
		verifyForm(content);
		sysContentService.update(content);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除内容")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:content:delete")
	public Result delete(@RequestBody Long[] ids){
		sysContentService.deleteBatch(ids);
		
		return Result.ok();
	}

	private void verifyForm(SysContent content){
		if(StringUtils.isBlank(content.getTitle())){
			throw new SbedException("标题不能为空");
		}
		if(StringUtils.isBlank(content.getSlug())){
			throw new SbedException("别名不能为空");
		}
		if(StringUtils.isBlank(content.getText())){
			throw new SbedException("内容不能为空");
		}
	}
	
}
