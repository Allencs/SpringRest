package com.allen.dto;

import com.allen.model.StaffProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: allen
 * @Date: 2022/4/16 10:47
 * @Description: 服务统一返回对象
 **/
public class ResponseData {

    @ApiModelProperty(value = "状态码；成功：200，失败：100")
    public Integer code;

    @ApiModelProperty(value = "错误信息")
    public String message;

    @ApiModelProperty(value = "人员详细信息")
    public StaffProperties staffProperties;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StaffProperties getStaffProperties() {
        return staffProperties;
    }

    public void setStaffProperties(StaffProperties staffProperties) {
        this.staffProperties = staffProperties;
    }
}
