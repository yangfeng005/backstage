package com.backstage.system.service.impl;

import com.backstage.common.page.Page;
import com.backstage.common.utils.codec.MD5Util;
import com.backstage.common.utils.codec.SaltUtil;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.constant.Constant;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.result.ServiceResultHelper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.dao.customized.UserCustomizedMapper;
import com.backstage.system.dao.gen.UserGeneratedMapper;
import com.backstage.system.dao.gen.UserRoleGeneratedMapper;
import com.backstage.system.dto.request.UserRequest;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.customized.UserRoleAO;
import com.backstage.system.entity.gen.UserCriteria;
import com.backstage.system.service.IRoleService;
import com.backstage.system.service.IUserRoleService;
import com.backstage.system.service.IUserService;
import com.backstage.system.service.IVUserPrivilegeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @yangfeng
 * @date 2019-09-11
 * 用户服务
 */
@Service
public class UserService extends AbstractBaseAOService<UserAO, UserCriteria> implements IUserService {


    @Resource
    private UserGeneratedMapper userGeneratedMapper;

    @Resource
    private UserRoleGeneratedMapper userRoleGeneratedMapper;

    @Resource
    private UserCustomizedMapper userCustomizedMapper;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IVUserPrivilegeService vUserPrivilegeService;

    @Resource
    private IRoleService roleService;


    @Override
    protected BaseGeneratedMapper<UserAO, UserCriteria> getGeneratedMapper() {
        return userGeneratedMapper;
    }

    @Override
    public UserAO getUserByNameAndPwd(String userName, String pwd) {
        UserCriteria criteria = new UserCriteria();
        criteria.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(pwd);
        return getUserByCriteria(criteria);
    }


    @Override
    public UserAO getUserByName(String userName) {
        return userCustomizedMapper.getUserByName(userName);
    }


    public UserAO getUserByCriteria(UserCriteria criteria) {
        ServiceResult<List<UserAO>> selectByCriteria = selectByCriteria(criteria);
        if (selectByCriteria != null && selectByCriteria.getData() != null) {
            List<UserAO> data = selectByCriteria.getData();
            if (data.size() > 0) {
                return data.get(0);
            }
        }
        return null;
    }


    /**
     * 查询用户
     *
     * @return
     */
    @Override
    public ServiceResult<List<UserAO>> list(UserRequest request) {
        ServiceResult<List<UserAO>> ret = new ServiceResult<>();
        //分页查询用户列表
        PageHelper.startPage(request.getPageNo(), request.getPageSize());
        List<UserAO> userList = userCustomizedMapper.listByCondition(Constant.SYSTEM_SUPER_ADMIN,
                AuthUtil.getCurrentUser().getUserName(), request.getStatus(),
                request.getUserName(), request.getTrueName(), AuthUtil.getCurrentUser().getAgencyCode());
        ret.setData(userList);
        ret.setSucceed(true);
        ret.setAdditionalProperties("page", Page.obtainPage(new PageInfo<>(userList)));
        return ret;
    }


    /**
     * 更新用户状态
     *
     * @param userIds
     * @param status
     * @return
     */
    @Transactional
    @Override
    public ServiceResult<Boolean> updateStatus(String[] userIds, Integer status) {
        Boolean isSuccessd = true;
        if (userIds != null && userIds.length > 0) {
            for (String userId : userIds) {
                UserAO userAO = new UserAO();
                userAO.setStatus(status);
                userAO.setId(userId);
                userAO.setUpdateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
                ServiceResult<Boolean> ret = saveOrUpdate(userAO);
                if (ret == null || !ret.isSucceed()) {
                    isSuccessd = false;
                    break;
                }
            }
            if (isSuccessd) {
                return ServiceResultHelper.genResultWithSuccess();
            }
        }
        return ServiceResult.error(Constant.ErrorCode.DATABASE_OPERATION_ERROR_CODE, Constant.ErrorCode.DATABASE_OPERATION_ERROR_MSG);
    }

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @Override
    public ServiceResult<Boolean> save(UserAO user) {
        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            return ServiceResult.error("参数错误，用户名不能为空!");
        }
        //检查用户是否已经存在
        if (!checkBeforeSave(user)) {
            return ServiceResultHelper.genResultWithFaild("用户已存在", -1);
        }
        if (StringUtils.isEmpty(user.getId())) { //新增
            user.setStatus(Constant.VALID_FLG);//默认启用
            //密码加密
            String salt = SaltUtil.getNextSalt();
            user.setSalt(salt);
            user.setCreater(AuthUtil.getCurrentUser().getId());
            user.setCreateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            user.setPassword(Constant.USER_CS_PWD);//新建用户初始密码
            user.setPassword(MD5Util.MD5Encode(user.getPassword() + salt));
        } else {
            user.setUpdater(AuthUtil.getCurrentUser().getId());
            user.setUpdateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
        }
        return saveOrUpdate(user);
    }

    /**
     * 保存前进行检查
     *
     * @return
     */
    public boolean checkBeforeSave(UserAO user) {
        UserCriteria criteria = new UserCriteria();
        if (StringUtils.isEmpty(user.getId())) {
            criteria.createCriteria().andUserNameEqualTo(user.getUserName())
                    .andStatusNotEqualTo(Constant.INVALID_FLG);
        } else {
            criteria.createCriteria().andUserNameEqualTo(user.getUserName())
                    .andIdNotEqualTo(user.getId()).andStatusNotEqualTo(Constant.INVALID_FLG);
        }
        ServiceResult<List<UserAO>> result = selectByCriteria(criteria);
        if (result != null && result.isSucceed() && !CollectionUtils.isEmpty(result.getData())) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public ServiceResult<Boolean> saveUserRole(String userId, String[] roleIds) {
        //删除原来的角色
        userRoleService.deleteByUserId(userId);
        //用户角色关联
        if (roleIds != null && roleIds.length > 0) {
            List<String> roleIdList = Arrays.asList(roleIds);
            roleIdList.forEach(roleId -> {
                UserRoleAO userRole = new UserRoleAO();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleGeneratedMapper.insert(userRole);
            });
        }
        return ServiceResultHelper.genResultWithSuccess();
    }

    /**
     * 根据角色id查看角色对应的用户
     *
     * @param roleId
     * @return
     */
    @Override
    public List<UserAO> getUsersByRoleId(String roleId) {
        return userCustomizedMapper.getUsersByRoleId(roleId);
    }

    /**
     * 修改密码
     *
     * @param userName
     * @param originPassword
     * @param newPassword
     * @return
     */
    @Override
    public ServiceResult<Integer> updatePwd(String userName, String originPassword, String newPassword) {
        UserAO result = getUserByName(userName);
        if (result != null) {
            String salt = result.getSalt();
            String password = MD5Util.MD5Encode(originPassword + salt);
            if (!password.equals(result.getPassword())) {
                return ServiceResultHelper.genResultWithFaild("原密码不正确", -1);
            }
        }
        return updatePwd(userName, newPassword);
    }

    /**
     * 更新密码
     *
     * @param userName
     * @param newPassword
     * @return
     */
    public ServiceResult<Integer> updatePwd(String userName, String newPassword) {
        UserCriteria criteria = new UserCriteria();
        criteria.createCriteria().andUserNameEqualTo(userName);
        UserAO user = new UserAO();
        //密码加密
        String salt = SaltUtil.getNextSalt();
        user.setSalt(salt);
        user.setPassword(MD5Util.MD5Encode(newPassword + salt));
        user.setUpdater(AuthUtil.getCurrentUser().getId());
        user.setUpdateTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
        return updateByCriteriaSelective(user, criteria);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public ServiceResult<UserAO> getUserById(String id) {
        ServiceResult<UserAO> userRet = selectByPrimaryKey(id);
        return userRet;
    }

    /**
     * 重置密码
     *
     * @return
     */
    @Override
    public ServiceResult<Integer> resetPwd(String userName) {
        return updatePwd(userName, Constant.USER_CS_PWD);
    }


    /**
     * 根据用户名查询用户相关信息，包括角色权限
     *
     * @return
     */
    @Override
    public ServiceResult<UserAO> getByUserName(String userName) {
        UserAO user = getUserByName(userName);
        if (user != null) {
            user.setPrivileges(vUserPrivilegeService.queryPrivilegeByUserName(userName));
            ServiceResult<List<RoleAO>> roleResult = roleService.getRolesByUserName(userName);
            if (roleResult != null && roleResult.isSucceed() && !CollectionUtils.isEmpty(roleResult.getData())) {
                user.setRoles(roleResult.getData());
            }
        }
        return ServiceResultHelper.genResultWithSuccess(user);
    }
}
