package com.backstage.system.dto.request;


import com.backstage.core.request.BaseRequest;

/**
 * @author yangfeng
 * @date 2020/04/14
 * @description 操作日志请求
 */
public class SysLogRequest extends BaseRequest {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 操作时间
     */
    private String operateTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
