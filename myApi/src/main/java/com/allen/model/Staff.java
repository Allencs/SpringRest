package com.allen.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: allen
 * @Date: 2022/4/16 10:24
 * @Description: 员工类
 **/
public class Staff {

    @ApiModelProperty(value = "名称")
    public String userName;

    @ApiModelProperty(value = "密码")
    public String pwd;
}
