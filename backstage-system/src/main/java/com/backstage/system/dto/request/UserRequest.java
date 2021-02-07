package com.backstage.system.dto.request;

import com.backstage.core.request.BaseRequest;

/**
 * 用户请求对象
 *
 * @yangfeng
 * @date 2018-06-04 12:57
 */
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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
}
