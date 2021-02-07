package com.backstage.system.dto.request;


import com.backstage.core.request.BaseRequest;

import java.io.Serializable;

/**
 * @author yangfeng
 * @date 2019/11/14
 * @description 机构请求
 */
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
