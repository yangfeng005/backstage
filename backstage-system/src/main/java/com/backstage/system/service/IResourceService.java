package com.backstage.system.service;

import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.ResourceAO;
import com.backstage.system.entity.gen.ResourceCriteria;

import java.util.List;

public interface IResourceService extends IBaseAOService<ResourceAO, ResourceCriteria> {

    /**
     * 根据用户id获取与权限相关的所有资源
     *
     * @return
     */
    ServiceResult<List<ResourceAO>> getResource(String userId);

    /**
     * 获取资源树
     *
     * @return
     */
    ServiceResult<List<ResourceAO>> getResourceTreeNode();


    /**
     * 权限和资源表整合成总的资源
     *
     * @return
     */
    List<ResourceAO> getResourceWithPrivilege();


    /**
     * 根据用户用户名获取与权限相关的资源
     *
     * @return
     */
    List<ResourceAO> getResourceByUserName(String userName);
}
