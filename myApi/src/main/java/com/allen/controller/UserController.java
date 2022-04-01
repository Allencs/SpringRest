package com.allen.controller;


import com.alibaba.fastjson.JSONObject;
import com.allen.dao.NewStaff;
import com.allen.service.StaffInfo;
import com.allen.service.proxiedBeans.StaffInfoService;
import com.allen.service.UserService;
import com.allen.jwt.JWTUtil;
import com.allen.dto.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    @Qualifier(value = "staffInfoServiceImpl")
    UserService staffInfoServiceImpl;

    @Autowired
    @Qualifier(value = "staffInfoService")
    StaffInfoService staffInfoService;

    @Autowired
    StaffInfo staffInfo;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    NewStaff newStaff;

    @RequestMapping(value = "/user/staffInfoJDK", method = RequestMethod.POST) //公开HTTP端点
    public PersonProperties personProperties_JDK() {
        return staffInfoServiceImpl.getPersonInfo();
    }

    @RequestMapping(value = "/user/staffInfoCGLIB", method = RequestMethod.POST)
    public PersonProperties personProperties_CGLIB() {
        return staffInfoService.getPersonInfo();
    }

    @RequestMapping(value = "/user/staffInfo", method = RequestMethod.POST)
    public PersonProperties personProperties() {
        return staffInfo.getPersonInfo();
    }

    @RequestMapping(value = "/user/token", method = RequestMethod.GET)
    public String GetToken() {

        String token = jwtUtil.createJWT("1", "allen", "myApi", 1000 * 60 * 5);//2分钟
        return token;
    }

    @RequestMapping(value = "/user/newStaffInfo", method = RequestMethod.POST)
    public JSONObject GetPersonInfo(HttpServletRequest request) {
        return newStaff.create();
    }
}
