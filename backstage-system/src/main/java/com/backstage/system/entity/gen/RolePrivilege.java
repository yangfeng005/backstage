package com.backstage.system.entity.gen;

import java.io.Serializable;

public class RolePrivilege implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column jp_sys_role_privilege.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column jp_sys_role_privilege.role_id
     *
     * @mbggenerated
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column jp_sys_role_privilege.privilege_id
     *
     * @mbggenerated
     */
    private String privilegeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table jp_sys_role_privilege
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jp_sys_role_privilege.id
     *
     * @return the value of jp_sys_role_privilege.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jp_sys_role_privilege.id
     *
     * @param id the value for jp_sys_role_privilege.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jp_sys_role_privilege.role_id
     *
     * @return the value of jp_sys_role_privilege.role_id
     *
     * @mbggenerated
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jp_sys_role_privilege.role_id
     *
     * @param roleId the value for jp_sys_role_privilege.role_id
     *
     * @mbggenerated
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column jp_sys_role_privilege.privilege_id
     *
     * @return the value of jp_sys_role_privilege.privilege_id
     *
     * @mbggenerated
     */
    public String getPrivilegeId() {
        return privilegeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column jp_sys_role_privilege.privilege_id
     *
     * @param privilegeId the value for jp_sys_role_privilege.privilege_id
     *
     * @mbggenerated
     */
    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }
}