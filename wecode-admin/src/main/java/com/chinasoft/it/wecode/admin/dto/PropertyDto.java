package com.chinasoft.it.wecode.admin.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.chinasoft.it.wecode.admin.util.PropertyConstant;
import com.chinasoft.it.wecode.common.dto.BaseDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改时，id 使用路径参数
 * 
 * 基本类型校验放在DTO
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "数据字典DTO")
public class PropertyDto extends BaseDto {

	private static final long serialVersionUID = 5152075623436625922L;

	/**
	 * parent id
	 */
	@ApiModelProperty(name = "父节点ID", required = false, example = PropertyConstant.ROOT)
	@Length(max = 36)
	private String pid;

	/**
	 * name
	 */
	@NotNull
	@Length(min = 1, max = 255)
	@ApiModelProperty(name = "字典Key", required = true)
	private String name;

	/**
	 * value
	 */
	@Length(max = 1000)
	@ApiModelProperty(name = "字典Value", required = true)
	private String value;

	@ApiModelProperty(name = "字典Value 类型", allowableValues = "simple, item", example = "simple")
	private String valueType;

	/**
	 * remark
	 */
	@Length(max = 20000)
	private String remark;

	/**
	 * sorted field
	 */
	@ApiModelProperty(name = "排序", required = false, example = "1")
	@Range
	private Long seq;

	@ApiModelProperty(name = "状态，0失效，1生效", required = false, example = "1")
	@Range(min = 0, max = 1)
	private Integer status;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
