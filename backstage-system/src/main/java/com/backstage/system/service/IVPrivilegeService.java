package com.backstage.system.service;

import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.VPrivilegeAO;
import com.backstage.system.entity.gen.VPrivilegeCriteria;

import java.util.List;

public interface IVPrivilegeService extends IBaseAOService<VPrivilegeAO, VPrivilegeCriteria> {

    /**
     * 根据资源id获取权限
     * @param resourceId
     * @return
     */
    List<VPrivilegeAO> getPrivilegesByResourceId(String resourceId);

}
