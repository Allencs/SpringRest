package com.allen.dto;

import org.springframework.beans.factory.annotation.Value;

public class PersonProperties {

	@Value("${StaffInfo.name}")
	private String name;
	@Value("${StaffInfo.job}")
	private String job;
	@Value("${StaffInfo.company}")
	private String company;
	@Value("${StaffInfo.message}")
	private String message;
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
