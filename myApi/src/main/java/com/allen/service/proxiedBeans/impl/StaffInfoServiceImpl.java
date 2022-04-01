package com.allen.service.proxiedBeans.impl;

import com.allen.dto.PersonProperties;
import com.allen.service.AbstractStaffInfoService;
import com.allen.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: allen
 * @Date: 2022/3/26 2:05 PM
 * @Description: 实现接口的Bean【测试JDK代理模式性能】
 **/

@Service(value = "staffInfoServiceImpl")
public class StaffInfoServiceImpl extends AbstractStaffInfoService implements UserService {

    @Override
    public PersonProperties getPersonInfo() {
        return getNewStaff();
    }
}
