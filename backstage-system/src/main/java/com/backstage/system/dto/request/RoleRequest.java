package com.backstage.system.dto.request;


import com.backstage.core.request.BaseRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yangfeng
 * @date 2019/11/14
 * @description 机构请求
 */
@Data
public class RoleRequest extends BaseRequest implements Serializable {

    private Integer status;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 机构code
     */
    private String agencyCode;

    /**
     * 机构名称
     */
    private String agencyName;
}
