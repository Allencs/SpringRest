package com.allen.service;

import com.alibaba.fastjson.JSON;
import com.allen.model.Staff;
import com.allen.model.StaffProperties;
import com.allen.util.UuidUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: allen
 * @Date: 2022/3/26 10:35 PM
 * @Description:
 **/
public abstract class AbstractStaffInfoService {
    public static AtomicInteger counter = new AtomicInteger(0);

    public StaffProperties getNewStaff() {
        StaffProperties staffProperties = new StaffProperties();
        staffProperties.setCode(UuidUtil.getStrUuid());
        staffProperties.setCompany("PerfMa");
        staffProperties.setJob("PerformanceTestEngineer");
        staffProperties.setMessage("you are " + counter.incrementAndGet() + "st staff");
        staffProperties.setName("Mr.JiaLiDun-" + counter.get());
        return staffProperties;
    }

    public Boolean authenticate(Staff staff) {
        return staff.userName.equals("GoodBoy") && staff.pwd.equals("root123");
    }
}
