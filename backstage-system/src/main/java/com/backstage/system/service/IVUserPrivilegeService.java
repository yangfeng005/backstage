package com.backstage.system.service;

import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.VUserPrivilegeAO;
import com.backstage.system.entity.gen.VUserPrivilegeCriteria;

import java.util.List;

public interface IVUserPrivilegeService extends IBaseAOService<VUserPrivilegeAO, VUserPrivilegeCriteria> {

    /**
     * 根据用户名获取用户权限
     *
     * @param userName
     * @return
     */
    List<VUserPrivilegeAO> queryPrivilegeByUserName(String userName);

}
