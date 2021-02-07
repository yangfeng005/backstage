package com.backstage.system.auth;

import com.backstage.system.entity.customized.UserAO;
import org.apache.shiro.SecurityUtils;

/**
 * 用户认证工具类
 *
 * @author yangfeng
 */
public class AuthUtil {
    public static UserAO getCurrentUser() {
        UserAO user = (UserAO) SecurityUtils.getSubject().getPrincipal();
        return user != null ? user : new UserAO();
    }
}
