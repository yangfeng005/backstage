package com.backstage.system.dao.customized;

import com.backstage.core.mapper.BaseCustomizedMapper;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.entity.gen.RoleCriteria;
import com.backstage.system.dto.request.RoleRequest;

import java.util.List;

/**
 * Role 数据存取接口.
 *
 * @author yangfeng
 * @version 1.0.0, Jun 14, 2018
 */
public interface RoleCustomizedMapper extends BaseCustomizedMapper<RoleAO, RoleCriteria> {

    List<RoleAO> getRolesByUserId(String userId);

    List<RoleAO> getRolesByUserName(String userName);


    /**
     * 查询角色
     *
     * @param request
     * @return
     */
    List<RoleAO> listByCondition(RoleRequest request);
}
