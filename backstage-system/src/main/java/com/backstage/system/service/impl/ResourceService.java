package com.backstage.system.service.impl;

import com.backstage.core.constant.Constant;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.core.tree.TreeUtil;
import com.backstage.system.dao.customized.ResourceCustomizedMapper;
import com.backstage.system.dao.gen.ResourceGeneratedMapper;
import com.backstage.system.entity.customized.ResourceAO;
import com.backstage.system.entity.gen.ResourceCriteria;
import com.backstage.system.service.IResourceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService extends AbstractBaseAOService<ResourceAO, ResourceCriteria> implements IResourceService {

    @Resource
    private ResourceGeneratedMapper resourceGeneratedMapper;

    @Resource
    private ResourceCustomizedMapper resourceCustomizedMapper;

    @Override
    protected BaseGeneratedMapper<ResourceAO, ResourceCriteria> getGeneratedMapper() {
        return resourceGeneratedMapper;
    }

    /**
     * 根据用户名称获取与权限相关的所有资源（父子级）
     *
     * @return
     */
    public ServiceResult<List<ResourceAO>> getResource(String userName) {
        //1.查询所有资源
        ServiceResult<List<ResourceAO>> listServiceResult = selectByCriteria(null);
        List<ResourceAO> allResourceList = null;
        if (listServiceResult != null && listServiceResult.isSucceed() && !CollectionUtils.isEmpty(listServiceResult.getData())) {
            allResourceList = listServiceResult.getData();
        }
        //超级管理员
        if (Constant.SYSTEM_SUPER_ADMIN.equals(userName)) {
            return TreeUtil.sortTreeNodes(allResourceList, "");
        }
        //2.查询用户权限关联的资源
        List<ResourceAO> resourceChildren = getResourceByUserName(userName);
        //3.根据权限关联的资源（子级）查询所有父级
        List<ResourceAO> parentResourceList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resourceChildren)) {
            for (ResourceAO child : resourceChildren) {
                TreeUtil.getAllParentListWithChild(allResourceList, child.getCode(), parentResourceList);
            }
        }
        //排序并构造树结构
        return TreeUtil.sortTreeNodes(parentResourceList, "");
    }

    /**
     * 获取资源树
     *
     * @return
     */
    public ServiceResult<List<ResourceAO>> getResourceTreeNode() {
        //查询所有资源
        List<ResourceAO> allResource = getResourceWithPrivilege();
        return TreeUtil.sortTreeNodes(allResource, "");
    }

    /**
     * 权限和资源表整合成总的资源
     *
     * @return
     */
    @Override
    public List<ResourceAO> getResourceWithPrivilege() {
        return resourceCustomizedMapper.getResourceWithPrivilege();
    }

    /**
     * 根据用户id获取与权限相关的资源
     *
     * @return
     */
    @Override
    public List<ResourceAO> getResourceByUserName(String userName) {
        return resourceCustomizedMapper.getResourceByUserName(userName);
    }

}
