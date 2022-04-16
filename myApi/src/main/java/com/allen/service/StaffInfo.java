package com.allen.service;

import com.allen.dto.ResponseData;
import com.allen.model.Staff;
import org.springframework.stereotype.Service;

/**
 * @Author: allen
 * @Date: 2022/3/26 10:20 PM
 * @Description: 不进行AOP操作
 **/

@Service
public class StaffInfo extends AbstractStaffInfoService{

    public ResponseData getPersonInfo(Staff staff) {
        ResponseData responseData = new ResponseData();
        if (authenticate(staff)) {
            responseData.setCode(200);
            responseData.setMessage("操作成功");
            responseData.setStaffProperties(getNewStaff());
            return responseData;
        }
        responseData.setCode(100);
        responseData.setMessage("用户名或密码错误");
       return responseData;
    }
}
