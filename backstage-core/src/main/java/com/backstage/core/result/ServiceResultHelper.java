package com.backstage.core.result;

import com.backstage.core.constant.Constant;

public class ServiceResultHelper {
    public ServiceResultHelper() {
    }

    public static <T> ServiceResult<T> genResult(boolean succeed, int retCode, String msg, T obj) {
        ServiceResult ret = new ServiceResult();
        ret.setData(obj);
        ret.setMsg(msg);
        ret.setCode(retCode);
        ret.setSucceed(succeed);
        return ret;
    }

    public static <T> boolean isSuccess(ServiceResult<T> result) {
        return result != null && result.isSucceed() && result.getCode() == 0;
    }



    public static <T> ServiceResult<T> genResultWithSuccess(T obj) {
        return genResult(true, Constant.SUCCESS, "成功", obj);
    }

    public static <T> ServiceResult<T> genResultWithSuccess() {
        return genResult(true, Constant.SUCCESS, "成功", null);
    }


    public static <T> ServiceResult<T> genResultWithFaild() {
        return genResult(false, Constant.FAILED, "失败", null);
    }

    public static <T> ServiceResult<T> genResultWithFaild(String msg,Integer code) {
        return genResult(false, code, msg, null);
    }
    public static <T> ServiceResult<T> genResultWithWarn(String msg,Integer code) {
        return genResult(true, code, msg, null);
    }
    public static <T> ServiceResult<T> genResultWithDataNull() {
        return genResult(false, Constant.SUCCESS, "数据为空", null);
    }
}

