package com.backstage.system.controller;

import com.backstage.common.utils.codec.AESUtil;
import com.backstage.common.utils.codec.MD5Util;
import com.backstage.common.utils.http.IPUtil;
import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.constant.Constant;
import com.backstage.core.jwt.JWTUtil;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.result.ServiceResultHelper;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.customized.VUserPrivilegeAO;
import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IAgencyService;
import com.backstage.system.service.IUserService;
import com.backstage.system.service.IVUserPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 登录
 *
 * @author yangfeng
 * @date 2019-07-11 16:02
 */
@Api(tags = "登录")
@RestController
@RequestMapping(value = "pc/login")
@Validated
public class LoginController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IAgencyService agencyService;

    @Resource
    private IVUserPrivilegeService vUserPrivilegeService;


    /**
     * 未登陆认证
     *
     * @param message
     * @return
     */
    @GetMapping("/noLogin")
    public Object noLogin(String message) {
        ServiceResult<String> genResult = ServiceResultHelper.genResult(false, Constant.NO_AUTHENTICATION,
                StringUtils.isNotEmpty(message) ? message : "用户登录信息已失效，请重新登录后再试。", null);
        return genResult;
    }

    /**
     * 未授权
     *
     * @return
     */
    @GetMapping("/unauthorized")
    public Object unauthorized() {
        return ServiceResultHelper.genResult(false, Constant.ErrorCode.PERMISSION_DENIED_CODE,
                Constant.ErrorCode.PERMISSION_DENIED_MSG, null);
    }

    /**
     * 登录操作
     *
     * @param username
     * @param password
     * @param request
     */
    @ApiOperation(value = "登录系统", notes = "登录系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query", required = true),
    })
    @PostMapping(value = "/doLogin")
    @LogOperation(action = "登录系统")
    public Object doLogin(@NotBlank(message = "用户名不能为空") String username,
                          @NotBlank(message = "密码不能为空") String password,
                          HttpServletRequest request) throws Exception {
        ServiceResult<UserAO> genResult;
        // 获取盐值
        String salt;
        UserAO loginUser = userService.getUserByName(username);
        if (loginUser == null) {
            genResult = ServiceResultHelper.genResult(false, Constant.ErrorCode.USER_NOT_EXIST_ERROR,
                    Constant.ErrorCode.USER_NOT_EXIST_ERROR_MSG, null);
            return genResult;
        }
        if (Constant.DISABLE.equals(loginUser.getStatus())) {
            genResult = ServiceResultHelper.genResult(false, Constant.ErrorCode.USER_DISABLE_ERROR,
                    Constant.ErrorCode.USER_DISABLE_ERROR_MSG, null);
            return genResult;
        }
        salt = loginUser.getSalt();
        //解密
        String originPassword = AESUtil.desEncrypt(password);
        password = MD5Util.MD5Encode(originPassword.trim().concat(salt));
        UserAO user = userService.getUserByNameAndPwd(username, password);
        if (user != null) {
            genResult = ServiceResultHelper.genResult(true, Constant.SUCCESS, "登录成功", user);
            //获取权限
            List<VUserPrivilegeAO> userPrivileges = vUserPrivilegeService.queryPrivilegeByUserName(username);
            user.setPrivileges(userPrivileges);
            //更新登录信息
            user.setLastLoginTime(DateTimeUtil.format(new Date(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND));
            user.setLastLoginIp(IPUtil.getClientIp(request));
            //用户所属机构
            if (StringUtils.isNotEmpty(user.getAgencyCode())) {
                user.setAgencyName(agencyService.getByCode(user.getAgencyCode()).getName());
            }
            userService.saveOrUpdate(user);
            Map<String, Object> map = new HashMap<>();
            map.put("userName", username);
            map.put("userId", user.getId());
            genResult.setAdditionalProperties("token", JWTUtil.sign(map));
        } else {
            genResult = ServiceResultHelper.genResult(false, Constant.ErrorCode.PASSWORD_ERROR,
                    Constant.ErrorCode.PASSWORD_ERROR_MSG, null);
        }
        return genResult;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "退出", notes = "退出")
    @ApiImplicitParams({
    })
    @GetMapping("/logout")
    @LogOperation(action = "退出登录")
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ServiceResultHelper.genResultWithSuccess("退出登录成功");
    }

}
