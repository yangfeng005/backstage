package com.backstage.system.shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.backstage.core.constant.Constant;
import com.backstage.core.jwt.JWTToken;
import com.backstage.core.jwt.JWTUtil;
import com.backstage.core.result.ServiceResult;
import com.backstage.system.entity.customized.RoleAO;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.customized.VUserPrivilegeAO;
import com.backstage.system.service.IRoleService;
import com.backstage.system.service.IUserService;
import com.backstage.system.service.IVUserPrivilegeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录授权realm
 *
 * @author yangfeng
 */
@Service
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private IRoleService roleService;

    @Resource
    private IVUserPrivilegeService vUserPrivilegeService;

    @Resource
    private IUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 为当前登录用户授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserAO user = (UserAO) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据登录名获取登用户信息
        if (user != null && !StringUtils.isEmpty(user.getUserName())) {
            //设置用户角色
            ServiceResult<List<RoleAO>> rolesResult = roleService.getRolesByUserName(user.getUserName());
            if (rolesResult != null && rolesResult.isSucceed() && !CollectionUtils.isEmpty(rolesResult.getData())) {
                for (RoleAO role : rolesResult.getData()) {
                    authorizationInfo.addRole(role.getName());
                }
            }
            //设置权限
            List<VUserPrivilegeAO> privileges = vUserPrivilegeService.queryPrivilegeByUserName(user.getUserName());
            if (!CollectionUtils.isEmpty(privileges)) {
                for (VUserPrivilegeAO privilege : privileges) {
                    if (privilege == null || StringUtils.isEmpty(privilege.getPrivilegeCode())) {
                        continue;
                    }
                    //权限操作代码
                    authorizationInfo.addStringPermission(privilege.getPrivilegeCode());
                }
            }
            return authorizationInfo;
        }
        return null;
    }

    /**
     * 验证当前登录的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getFieldValue(token, "userName");
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("token错误!");
        }
        UserAO user = userService.getUserByName(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (Constant.DISABLE.equals(user.getStatus())) {
            throw new AuthenticationException("账号已被禁用!");
        }
        try {
            if (JWTUtil.verify(token, "userName", username)) {
                return new SimpleAuthenticationInfo(user, token, getName());
            } else {
                throw new AuthenticationException("token认证失败!");
            }
        } catch (TokenExpiredException e) {
            throw new AuthenticationException("token已过期!");
        } catch (SignatureVerificationException e) {
            throw new AuthenticationException("密码不正确!");
        }
    }

    /**
     * 超级管理员
     *
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        UserAO user = (UserAO) principals.getPrimaryPrincipal();
        return user.isSuperAdmin() || super.isPermitted(principals, permission);
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        UserAO user = (UserAO) principals.getPrimaryPrincipal();
        return user.isSuperAdmin() || super.hasRole(principals, roleIdentifier);
    }
}
