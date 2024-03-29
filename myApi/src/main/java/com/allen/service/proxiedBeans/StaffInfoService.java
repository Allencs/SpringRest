package com.allen.service.proxiedBeans;

import com.allen.model.StaffProperties;
import com.allen.service.AbstractStaffInfoService;
import org.springframework.stereotype.Service;

/**
 * @Author: allen
 * @Date: 2022/3/26 8:37 PM
 * @Description: 不实现接口的Bean【测试CGLIB代理模式性能】
 **/

@Service(value = "staffInfoService")
public class StaffInfoService extends AbstractStaffInfoService {

    public StaffProperties getPersonInfo() {
        return getNewStaff();
    }
}
