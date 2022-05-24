package com.allen.controller;

import com.allen.dao.NewStaff;
import com.allen.dto.ResponseData;
import com.allen.dto.StaffServiceResponse;
import com.allen.model.Staff;
import com.allen.service.StaffInfo;
import com.allen.service.proxiedBeans.StaffInfoService;
import com.allen.service.UserService;
import com.allen.jwt.JWTUtil;
import com.allen.model.StaffProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="myApi基础接口信息")
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

    @ApiOperation(value = "员工信息查询【JDK代理】")
    @RequestMapping(value = "/user/staffInfoJDK", method = RequestMethod.POST) //公开HTTP端点
    public StaffProperties personProperties_JDK() {
        return staffInfoServiceImpl.getPersonInfo();
    }

    @ApiOperation(value = "员工信息查询【CGLIB代理】")
    @RequestMapping(value = "/user/staffInfoCGLIB", method = RequestMethod.POST)
    public StaffProperties personProperties_CGLIB() {
        return staffInfoService.getPersonInfo();
    }

    @ApiOperation(value = "员工信息查询")
    @RequestMapping(value = "/user/staffInfo", method = RequestMethod.POST)
    public ResponseData personProperties(@RequestBody Staff staff) {
        return staffInfo.getPersonInfo(staff);
    }

    @ApiOperation(value = "token获取")
    @RequestMapping(value = "/user/token", method = RequestMethod.GET)
    public String GetToken() {

        String token = jwtUtil.createJWT("1", "allen", "myApi", 1000 * 60 * 5);//2分钟
        return token;
    }

    @ApiOperation(value = "新建员工信息")
    @RequestMapping(value = "/user/newStaffInfo", method = RequestMethod.POST)
    public StaffServiceResponse newStaffInfo() {
//        return CommonResponse.success(newStaff.create());
        return (StaffServiceResponse) StaffServiceResponse.success(new StaffProperties());
    }
}
