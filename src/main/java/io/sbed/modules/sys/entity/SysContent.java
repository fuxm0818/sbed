package io.sbed.modules.sys.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author heguoliang
 * @Description: TODO(内容管理)
 * @date 2017-08-29 10:44:09
 */
public class SysContent implements Serializable {
	
	//
	private Long id;
	//标题
	private String title;
	//内容
	private String text;
	//别名
	private String slug;
	//缩略图
	private String thumbnail;
	//价格
	private BigDecimal amount;
	//状态   0：草稿   1：正常   2：删除
	private Integer status;
	//排序号
	private Integer orderNum;
	//用户ID
	private Long userId;
	//关联的对象ID
	private Long objectId;
	//标识
	private String flag;
	//备注
	private String remark;
	//SEO关键字
	private String metaKeywords;
	//SEO描述内容
	private String metaDescription;
	//创建时间
	private Date createTime;
	//最后修改时间
	private Date modifyTime;

	private List<Long> taxonomyIdList;
	private Object[] tagNames;

	private String categorys;
	private String features;
	private String tags;

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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取：内容
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置：别名
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	/**
	 * 获取：别名
	 */
	public String getSlug() {
		return slug;
	}
	/**
	 * 设置：缩略图
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * 获取：缩略图
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	/**
	 * 设置：价格
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：状态   0：草稿   1：正常   2：删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态   0：草稿   1：正常   2：删除
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：排序号
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序号
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：关联的对象ID
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	/**
	 * 获取：关联的对象ID
	 */
	public Long getObjectId() {
		return objectId;
	}
	/**
	 * 设置：标识
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * 获取：标识
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：SEO关键字
	 */
	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}
	/**
	 * 获取：SEO关键字
	 */
	public String getMetaKeywords() {
		return metaKeywords;
	}
	/**
	 * 设置：SEO描述内容
	 */
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	/**
	 * 获取：SEO描述内容
	 */
	public String getMetaDescription() {
		return metaDescription;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：最后修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：最后修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	public List<Long> getTaxonomyIdList() {
		return taxonomyIdList;
	}

	public void setTaxonomyIdList(List<Long> taxonomyIdList) {
		this.taxonomyIdList = taxonomyIdList;
	}

	public Object[] getTagNames() {
		return tagNames;
	}

	public void setTagNames(Object[] tagNames) {
		this.tagNames = tagNames;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
