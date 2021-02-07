package com.backstage.system.entity.gen;

import java.io.Serializable;

public class VPrivilege implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.resource_id
     *
     * @mbggenerated
     */
    private String resourceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.action_id
     *
     * @mbggenerated
     */
    private String actionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.resource_code
     *
     * @mbggenerated
     */
    private String resourceCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.resource_name
     *
     * @mbggenerated
     */
    private String resourceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.resource_type
     *
     * @mbggenerated
     */
    private String resourceType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.parent_code
     *
     * @mbggenerated
     */
    private String parentCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.action_name
     *
     * @mbggenerated
     */
    private String actionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_privilege.privilege_code
     *
     * @mbggenerated
     */
    private String privilegeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_privilege
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.id
     *
     * @return the value of v_privilege.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.id
     *
     * @param id the value for v_privilege.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.resource_id
     *
     * @return the value of v_privilege.resource_id
     *
     * @mbggenerated
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.resource_id
     *
     * @param resourceId the value for v_privilege.resource_id
     *
     * @mbggenerated
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId == null ? null : resourceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.action_id
     *
     * @return the value of v_privilege.action_id
     *
     * @mbggenerated
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.action_id
     *
     * @param actionId the value for v_privilege.action_id
     *
     * @mbggenerated
     */
    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.resource_code
     *
     * @return the value of v_privilege.resource_code
     *
     * @mbggenerated
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.resource_code
     *
     * @param resourceCode the value for v_privilege.resource_code
     *
     * @mbggenerated
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.resource_name
     *
     * @return the value of v_privilege.resource_name
     *
     * @mbggenerated
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.resource_name
     *
     * @param resourceName the value for v_privilege.resource_name
     *
     * @mbggenerated
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.resource_type
     *
     * @return the value of v_privilege.resource_type
     *
     * @mbggenerated
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.resource_type
     *
     * @param resourceType the value for v_privilege.resource_type
     *
     * @mbggenerated
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.parent_code
     *
     * @return the value of v_privilege.parent_code
     *
     * @mbggenerated
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.parent_code
     *
     * @param parentCode the value for v_privilege.parent_code
     *
     * @mbggenerated
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.action_name
     *
     * @return the value of v_privilege.action_name
     *
     * @mbggenerated
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.action_name
     *
     * @param actionName the value for v_privilege.action_name
     *
     * @mbggenerated
     */
    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_privilege.privilege_code
     *
     * @return the value of v_privilege.privilege_code
     *
     * @mbggenerated
     */
    public String getPrivilegeCode() {
        return privilegeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_privilege.privilege_code
     *
     * @param privilegeCode the value for v_privilege.privilege_code
     *
     * @mbggenerated
     */
    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode == null ? null : privilegeCode.trim();
    }
}