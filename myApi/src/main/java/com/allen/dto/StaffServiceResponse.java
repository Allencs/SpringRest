package com.allen.dto;

import com.allen.common.CommonResponse;
import com.allen.model.StaffProperties;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: allen
 * @Date: 2022/5/12 22:52
 * @Description:
 **/
public class StaffServiceResponse extends CommonResponse<StaffProperties> {

    @ApiModelProperty(value = "员工信息")
    public StaffProperties data;
}
