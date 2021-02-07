package com.backstage.system.dto.request;

import java.io.Serializable;

/**
 * @author yangfeng
 * @date 2019/11/14
 * @description 机构请求
 */
public class AgencyRequest implements Serializable {

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构代码
     */
    private String code;


    /**
     * 机场代码
     */
    private String dm;

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

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }
}
