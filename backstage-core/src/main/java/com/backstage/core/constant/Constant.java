package com.backstage.core.constant;


/**
 * 常量配置
 *
 * @author yangfeng
 */
public final class Constant {

    public static int SUCCESS = 200;
    public static int FAILED = 404;
    public static Integer INVALID_FLG = 0; // 数据无效
    public static Integer VALID_FLG = 1;// 数据有效/解禁
    public static Integer DISABLE = 2;//禁用
    public static Integer IS_LEAF = 1;//叶子结点
    public static String USER_CS_PWD = "123456";//用户初始密码
    public static String SUPER_ADMIN_ROLE = "超级管理员";
    public static int NO_AUTHENTICATION = 401;//没有认证
    public static String SYSTEM_SUPER_ADMIN = "super_admin";//系统超级管理员


    public static class ErrorCode {

        /**
         * 无效参数
         */
        public static Integer INVALID_PARAM_CODE = -101;
        public static String INVALID_PARAM_MSG = "无效参数";

        /**
         * 没有权限
         */
        public static Integer PERMISSION_DENIED_CODE = -102;
        public static String PERMISSION_DENIED_MSG = "没有权限";

        /**
         * 通用错误
         */
        public static Integer COMMON_ERROR_CODE = -103;
        public static String COMMON_ERROR_MSG = "服务器繁忙，请稍后再试";

        /**
         * 登录失效
         */
        public static Integer INVALID_LOGIN_CODE = -104;
        public static String INVALID_LOGIN_MSG = "用户未登录或登录信息已失效";

        /**
         * 数据库操作失败
         */
        public static Integer DATABASE_OPERATION_ERROR_CODE = -105;
        public static String DATABASE_OPERATION_ERROR_MSG = "数据库操作失败";

        /**
         * token失效
         */
        public static Integer INVALID_TOKEN_CODE = -106;
        public static String INVALID_TOKEN_MSG = "token不可用";


        /**
         * 用户名不存在
         */
        public static Integer USER_NOT_EXIST_ERROR = -107;
        public static String USER_NOT_EXIST_ERROR_MSG = "用户不存在";

        /**
         * 用户名或密码错误
         */
        public static Integer PASSWORD_ERROR = -108;
        public static String PASSWORD_ERROR_MSG = "密码错误";

        /**
         * 账号被禁用
         */
        public static Integer USER_DISABLE_ERROR = -109;
        public static String USER_DISABLE_ERROR_MSG = "账号已被禁用，请联系管理员";

        /**
         * 文件上传失败
         */
        public static Integer FILE_UPLOAD_ERROR = -110;
        public static String FILE_UPLOAD_ERROR_MSG = "文件上传失败";

        /**
         * 文件不存在
         */
        public static Integer FILE_NOT_EXIST_ERROR = -111;
        public static String FILE_NOT_EXIST_ERROR_MSG = "文件不存在";

        /**
         * 服务器异常
         */
        public static Integer SERVER_ERROR_CODE = -200;
        public static String SERVER_ERROR_MSG = "服务器异常";

    }
}

