package com.backstage.core.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础树结构
 *
 * @param <T>
 * @author yangfeng
 */
public class TreeBase<T> {

    private String code;

    private String parentCode;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 子类
     */
    private List<T> children = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
