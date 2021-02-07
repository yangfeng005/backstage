package com.backstage.system.dto.request;

import com.backstage.core.request.BaseRequest;
import lombok.Data;

/**
 * 用户请求对象
 *
 * @yangfeng
 * @date 2018-06-04 12:57
 */
@Data
public class UserRequest extends BaseRequest {

    /**
     * 账户
     */
    private String userName;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 是否可用
     */
    private Integer status;

    /**
     * 排序
     */
    private String orderBy;


    /**
     * 机构code
     */
    private String agencyCode;
}
