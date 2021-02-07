package com.backstage.core.request;

import java.io.Serializable;

/**
 * 基本请求
 *
 * @author yangfeng
 * @date 2018-06-04 12:57
 */
public class BaseRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 第几页
     */
    private Integer pageNo = 1;
    /**
     * 每页数量
     */
    private Integer pageSize = 10;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
