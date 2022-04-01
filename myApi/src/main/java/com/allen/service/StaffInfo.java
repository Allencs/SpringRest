package com.allen.service;

import com.allen.dto.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: allen
 * @Date: 2022/3/26 10:20 PM
 * @Description: 不进行AOP操作
 **/

@Service
public class StaffInfo extends AbstractStaffInfoService{

    public PersonProperties getPersonInfo() {
        return getNewStaff();
    }
}
