package io.sbed.modules.sys.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * @author heguoliang
 * @Description: TODO(部门管理)
 * @date 2017-6-23 15:07
 */
public class SysDept implements Serializable {
	
	//部门ID
	private Long id;
	//上级部门ID，一级部门为0
	private Long parentId;
	//部门名称
	@NotBlank(message="部门名称不能为空")
	private String name;
	//上级部门名称
	private String parentName;
	//排序
	private Integer orderNum;
	/**
	 * ztree属性
	 */
	private Boolean open;

	private List<?> list;


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	/**
	 * 设置：上级部门ID，一级部门为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级部门ID，一级部门为0
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}
