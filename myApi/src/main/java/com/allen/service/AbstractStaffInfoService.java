package com.allen.service;

import com.allen.dto.PersonProperties;
import com.allen.util.UuidUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: allen
 * @Date: 2022/3/26 10:35 PM
 * @Description:
 **/
public abstract class AbstractStaffInfoService {
    public static AtomicInteger counter = new AtomicInteger(0);

    public PersonProperties getNewStaff() {
        PersonProperties personProperties = new PersonProperties();
        personProperties.setCode(UuidUtil.getStrUuid());
        personProperties.setCompany("PerfMa");
        personProperties.setJob("PerformanceTestEngineer");
        personProperties.setMessage("you are " + counter.incrementAndGet() + "st staff");
        personProperties.setName("Mr.JiaLiDun-" + counter.get());
        return personProperties;
    }
}
