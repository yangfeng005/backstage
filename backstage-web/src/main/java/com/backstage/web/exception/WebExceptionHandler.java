package com.backstage.web.exception;

import com.backstage.core.constant.Constant;
import com.backstage.core.result.ServiceResultHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * 全局异常处理
 *
 * @author yangfeng
 * @date 2019-09-11
 */
@RestControllerAdvice
public class WebExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(WebExceptionHandler.class);


    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return ServiceResultHelper.genResult(false, Constant.ErrorCode.SERVER_ERROR_CODE,
                StringUtils.isEmpty(e.getMessage()) ? Constant.ErrorCode.SERVER_ERROR_MSG : e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Object handleUnauthorizedException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return ServiceResultHelper.genResult(false, Constant.ErrorCode.PERMISSION_DENIED_CODE,
                Constant.ErrorCode.PERMISSION_DENIED_MSG, null);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public Object handleUnauthenticatedException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return ServiceResultHelper.genResult(false, Constant.ErrorCode.INVALID_LOGIN_CODE,
                Constant.ErrorCode.INVALID_LOGIN_MSG, null);
    }

    @ExceptionHandler(SQLException.class)
    public Object handleSQLException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        return ServiceResultHelper.genResult(false, Constant.ErrorCode.DATABASE_OPERATION_ERROR_CODE,
                Constant.ErrorCode.DATABASE_OPERATION_ERROR_MSG, null);
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        LOG.error(e.getMessage(), e);
        LOG.error(request.getRequestURI());
        return ServiceResultHelper.genResult(false,
                Constant.ErrorCode.SERVER_ERROR_CODE, Constant.ErrorCode.SERVER_ERROR_MSG, null);
    }

}
