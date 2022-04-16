package com.allen.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: allen
 * @Date: 2022/4/16 10:24
 * @Description: 员工信息
 **/

public class StaffProperties {

	@ApiModelProperty(value = "名称")
	@Value("${StaffInfo.name}")
	private String name;
	@ApiModelProperty(value = "职位")
	@Value("${StaffInfo.job}")
	private String job;
	@ApiModelProperty(value = "公司名称")
	@Value("${StaffInfo.company}")
	private String company;
	@ApiModelProperty(value = "信息")
	@Value("${StaffInfo.message}")
	private String message;
	@ApiModelProperty(value = "业务状态码；成功：200；失败：100")
	@Value("${StaffInfo.code}")
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String messjob) {
		this.message = messjob;
	}
	
	
	
}
