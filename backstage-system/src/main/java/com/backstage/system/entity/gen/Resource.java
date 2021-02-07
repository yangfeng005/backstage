package com.backstage.system.entity.gen;

import com.backstage.core.tree.TreeBase;

import java.io.Serializable;

public class Resource extends TreeBase implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.level
     *
     * @mbggenerated
     */
    private Integer level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.parent_code
     *
     * @mbggenerated
     */
    private String parentCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.rank
     *
     * @mbggenerated
     */
    private Integer rank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.img
     *
     * @mbggenerated
     */
    private String img;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_sys_resource.isleaf
     *
     * @mbggenerated
     */
    private Integer isleaf;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_sys_resource
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.id
     *
     * @return the value of t_sys_resource.id
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.id
     *
     * @param id the value for t_sys_resource.id
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.name
     *
     * @return the value of t_sys_resource.name
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.name
     *
     * @param name the value for t_sys_resource.name
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.code
     *
     * @return the value of t_sys_resource.code
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.code
     *
     * @param code the value for t_sys_resource.code
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.level
     *
     * @return the value of t_sys_resource.level
     * @mbggenerated
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.level
     *
     * @param level the value for t_sys_resource.level
     * @mbggenerated
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.parent_code
     *
     * @return the value of t_sys_resource.parent_code
     * @mbggenerated
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.parent_code
     *
     * @param parentCode the value for t_sys_resource.parent_code
     * @mbggenerated
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.rank
     *
     * @return the value of t_sys_resource.rank
     * @mbggenerated
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.rank
     *
     * @param rank the value for t_sys_resource.rank
     * @mbggenerated
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.img
     *
     * @return the value of t_sys_resource.img
     * @mbggenerated
     */
    public String getImg() {
        return img;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.img
     *
     * @param img the value for t_sys_resource.img
     * @mbggenerated
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.url
     *
     * @return the value of t_sys_resource.url
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.url
     *
     * @param url the value for t_sys_resource.url
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.description
     *
     * @return the value of t_sys_resource.description
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.description
     *
     * @param description the value for t_sys_resource.description
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.type
     *
     * @return the value of t_sys_resource.type
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.type
     *
     * @param type the value for t_sys_resource.type
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_sys_resource.isleaf
     *
     * @return the value of t_sys_resource.isleaf
     * @mbggenerated
     */
    public Integer getIsleaf() {
        return isleaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_sys_resource.isleaf
     *
     * @param isleaf the value for t_sys_resource.isleaf
     * @mbggenerated
     */
    public void setIsleaf(Integer isleaf) {
        this.isleaf = isleaf;
    }
}