package com.backstage.system.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangfeng
 * @date 2019/11/14
 * @description 机构请求
 */
@Data
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
     * 当前用户组织机构代码
     */
    private String currentCode;

}
