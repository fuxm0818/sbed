package io.sbed.modules.sys.entity;

/**
 * @author heguoliang
 * @Description: TODO(系统配置信息)
 * @date 2017-6-23 15:07
 */
public class SysConfig {

	private Long id;

	private String key;

	private String value;

	private String remark;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
