package com.backstage.system.entity.gen;

import java.io.Serializable;

public class TimeCost implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.user
     *
     * @mbggenerated
     */
    private String user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.ip
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.action_name
     *
     * @mbggenerated
     */
    private String actionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.operate_object
     *
     * @mbggenerated
     */
    private String operateObject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.operate_content
     *
     * @mbggenerated
     */
    private String operateContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.start_time
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.end_time
     *
     * @mbggenerated
     */
    private String endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.time_cost
     *
     * @mbggenerated
     */
    private String timeCost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_time_cost.create_time
     *
     * @mbggenerated
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_time_cost
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.id
     *
     * @return the value of t_time_cost.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.id
     *
     * @param id the value for t_time_cost.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.user
     *
     * @return the value of t_time_cost.user
     *
     * @mbggenerated
     */
    public String getUser() {
        return user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.user
     *
     * @param user the value for t_time_cost.user
     *
     * @mbggenerated
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.ip
     *
     * @return the value of t_time_cost.ip
     *
     * @mbggenerated
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.ip
     *
     * @param ip the value for t_time_cost.ip
     *
     * @mbggenerated
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.action_name
     *
     * @return the value of t_time_cost.action_name
     *
     * @mbggenerated
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.action_name
     *
     * @param actionName the value for t_time_cost.action_name
     *
     * @mbggenerated
     */
    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.operate_object
     *
     * @return the value of t_time_cost.operate_object
     *
     * @mbggenerated
     */
    public String getOperateObject() {
        return operateObject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.operate_object
     *
     * @param operateObject the value for t_time_cost.operate_object
     *
     * @mbggenerated
     */
    public void setOperateObject(String operateObject) {
        this.operateObject = operateObject == null ? null : operateObject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.operate_content
     *
     * @return the value of t_time_cost.operate_content
     *
     * @mbggenerated
     */
    public String getOperateContent() {
        return operateContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.operate_content
     *
     * @param operateContent the value for t_time_cost.operate_content
     *
     * @mbggenerated
     */
    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent == null ? null : operateContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.start_time
     *
     * @return the value of t_time_cost.start_time
     *
     * @mbggenerated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.start_time
     *
     * @param startTime the value for t_time_cost.start_time
     *
     * @mbggenerated
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.end_time
     *
     * @return the value of t_time_cost.end_time
     *
     * @mbggenerated
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.end_time
     *
     * @param endTime the value for t_time_cost.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.time_cost
     *
     * @return the value of t_time_cost.time_cost
     *
     * @mbggenerated
     */
    public String getTimeCost() {
        return timeCost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.time_cost
     *
     * @param timeCost the value for t_time_cost.time_cost
     *
     * @mbggenerated
     */
    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost == null ? null : timeCost.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_time_cost.create_time
     *
     * @return the value of t_time_cost.create_time
     *
     * @mbggenerated
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_time_cost.create_time
     *
     * @param createTime the value for t_time_cost.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}