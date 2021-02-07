package com.backstage.system.service.impl;

import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.constant.Constant;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.result.ServiceResultHelper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.core.tree.TreeUtil;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.dao.customized.AgencyCustomizedMapper;
import com.backstage.system.dao.gen.AgencyGeneratedMapper;
import com.backstage.system.dto.request.AgencyRequest;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.gen.AgencyCriteria;
import com.backstage.system.entity.customized.AgencyAO;
import com.backstage.system.service.IAgencyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机构服务
 *
 * @author yangfeng
 */
@Service
public class AgencyService extends AbstractBaseAOService<AgencyAO, AgencyCriteria> implements IAgencyService {

    @Resource
    private AgencyGeneratedMapper agencyGeneratedMapper;

    @Resource
    private AgencyCustomizedMapper agencyCustomizedMapper;


    @Override
    protected BaseGeneratedMapper<AgencyAO, AgencyCriteria> getGeneratedMapper() {
        return agencyGeneratedMapper;
    }


    /**
     * 查询机构
     *
     * @return
     */
    @Override
    public ServiceResult<List<AgencyAO>> list(AgencyRequest request) {
        UserAO user = AuthUtil.getCurrentUser();
        List<AgencyAO> allAgencyList = agencyCustomizedMapper.list(null);
        //超级管理员
        if (null != user && user.isSuperAdmin() && StringUtils.isEmpty(request.getName())
                && StringUtils.isEmpty(request.getCode())) {
            return TreeUtil.sortTreeNodes(allAgencyList, "");
        }
        request.setDm(user.getAgencyCode());
        List<AgencyAO> agencyChildren = agencyCustomizedMapper.list(request);
        List<AgencyAO> parentList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(agencyChildren)) {
            for (AgencyAO child : agencyChildren) {
                TreeUtil.getAllParentListWithChild(allAgencyList, child.getCode(), parentList);
            }
        }
        //排序并构造树结构
        return TreeUtil.sortTreeNodes(parentList, "");
    }


    /**
     * 编辑时选择上级机构要排除自身
     *
     * @param id
     * @return
     */
    @Override
    public ServiceResult<List<AgencyAO>> listOthers(String id) {
        List<AgencyAO> agencyAOList = agencyCustomizedMapper.listOthers(id);
        if (!CollectionUtils.isEmpty(agencyAOList)) {
            return TreeUtil.sortTreeNodes(agencyAOList, "");
        }
        return ServiceResultHelper.genResultWithSuccess();
    }


    /**
     * 删除机构及所有子机构
     *
     * @param agencyId
     * @return
     */
    @Override
    public ServiceResult<Boolean> delete(String agencyId) {
        List<AgencyAO> agencyAOList = agencyCustomizedMapper.list(null);
        if (!CollectionUtils.isEmpty(agencyAOList)) {
            AgencyAO parent = null;
            for (AgencyAO agency : agencyAOList) {
                if (agency.getId().equals(agencyId)) {
                    parent = agency;
                    break;
                }
            }
            //递归获取所有子节点
            if (parent != null) {
                List<AgencyAO> childNodes = new ArrayList<>();
                TreeUtil.getAllChildNodes(parent, agencyAOList, childNodes);
                childNodes.add(parent);
                agencyCustomizedMapper.deleteBatch(childNodes);
            }
        }
        return ServiceResultHelper.genResultWithSuccess();
    }


    /**
     * 保存
     *
     * @param agency
     * @return
     */
    @Override
    public ServiceResult<Boolean> save(AgencyAO agency) {
        if (agency == null || StringUtils.isEmpty(agency.getCode())) {
            return ServiceResult.error("参数错误，机构编码不能为空");
        }
        //检查版本是否已经存在
        if (!checkBeforeSave(agency)) {
            return ServiceResultHelper.genResultWithFaild("机构已存在", -1);
        }
        if (null == agency.getParentCode()) {
            agency.setParentCode("");
        }
        if (StringUtils.isEmpty(agency.getId())) { //新增
            agency.setCreateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            agency.setCreater(AuthUtil.getCurrentUser().getId());
            agency.setStatus(Constant.VALID_FLG.toString());
        } else {
            agency.setUpdateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            agency.setUpdater(AuthUtil.getCurrentUser().getId());
        }
        return saveOrUpdate(agency);
    }

    /**
     * 保存前进行检查
     *
     * @return
     */
    public boolean checkBeforeSave(AgencyAO agency) {
        AgencyCriteria criteria = new AgencyCriteria();
        if (StringUtils.isEmpty(agency.getId())) {
            criteria.createCriteria().andCodeEqualTo(agency.getCode())
                    .andStatusNotEqualTo(Constant.INVALID_FLG.toString());
        } else {
            //修改
            criteria.createCriteria().andCodeEqualTo(agency.getCode())
                    .andIdNotEqualTo(agency.getId())
                    .andStatusNotEqualTo(Constant.INVALID_FLG.toString());
        }
        ServiceResult<List<AgencyAO>> result = selectByCriteria(criteria);
        if (result != null && result.isSucceed() && !CollectionUtils.isEmpty(result.getData())) {
            return false;
        }
        return true;
    }

    /**
     * 根据编码获取机构
     *
     * @param code
     * @return
     */
    @Override
    public AgencyAO getByCode(String code) {
        AgencyCriteria criteria = new AgencyCriteria();
        criteria.createCriteria().andCodeEqualTo(code);
        ServiceResult<List<AgencyAO>> serviceResult = selectByCriteria(criteria);
        if (serviceResult != null && serviceResult.isSucceed() && !CollectionUtils.isEmpty(serviceResult.getData())) {
            return serviceResult.getData().get(0);
        }
        return null;
    }


}
