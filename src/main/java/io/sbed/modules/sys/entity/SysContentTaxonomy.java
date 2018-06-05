package io.sbed.modules.sys.entity;

import java.io.Serializable;

/**
 * @author heguoliang
 * @Description: TODO(内容与分类对应关系)
 * @date 2017-08-30 14:29:48
 */
public class SysContentTaxonomy implements Serializable {
	
	//
	private Long id;
	//内容ID
	private Long contentId;
	//分类ID
	private Long taxonomyId;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：内容ID
	 */
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	/**
	 * 获取：内容ID
	 */
	public Long getContentId() {
		return contentId;
	}
	/**
	 * 设置：分类ID
	 */
	public void setTaxonomyId(Long taxonomyId) {
		this.taxonomyId = taxonomyId;
	}
	/**
	 * 获取：分类ID
	 */
	public Long getTaxonomyId() {
		return taxonomyId;
	}

}
