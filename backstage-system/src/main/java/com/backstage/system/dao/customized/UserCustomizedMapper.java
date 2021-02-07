package com.backstage.system.dao.customized;

import com.backstage.core.mapper.BaseCustomizedMapper;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.gen.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User 数据存取接口.
 *
 * @author yangfeng
 * @version 1.0.0, Jun 14, 2018
 */
public interface UserCustomizedMapper extends BaseCustomizedMapper<UserAO, UserCriteria> {

    List<UserAO> getUsersByRoleId(String roleId);


    /**
     * 条件查询
     *
     * @param systemSuperAdmin 系统超级管理员
     * @param currentUserName  当前登录用户
     * @param status
     * @param userName
     * @param trueName
     * @param agencyCode       机构code
     * @return
     */
    List<UserAO> listByCondition(@Param("systemSuperAdmin") String systemSuperAdmin, @Param("currentUserName") String currentUserName,
                                 @Param("status") Integer status, @Param("userName") String userName,
                                 @Param("trueName") String trueName, @Param("agencyCode") String agencyCode);


    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    UserAO getUserByName(@Param("userName") String userName);
}
