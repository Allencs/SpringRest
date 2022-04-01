package com.allen;

import com.allen.service.StaffInfo;
import com.allen.service.proxiedBeans.StaffInfoService;
import com.allen.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootTest
public class MyApiApplicationTests implements ApplicationContextAware {

	ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
		UserService userService = (UserService) this.applicationContext.getBean("staffInfoServiceImpl");
		System.out.println("Bean[userService]: " + userService.getClass().getTypeName());

		StaffInfoService staffInfoService = (StaffInfoService) this.applicationContext.getBean("staffInfoService");
		System.out.println("Bean[staffInfoService]" + staffInfoService.getClass().getTypeName());

		StaffInfo staffInfo = (StaffInfo) this.applicationContext.getBean("staffInfo");
		System.out.println("Bean[staffInfo]" + staffInfo.getClass().getTypeName());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
