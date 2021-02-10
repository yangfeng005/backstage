package com.backstage.system.service;


import com.backstage.core.result.ServiceResult;
import com.backstage.core.service.IBaseAOService;
import com.backstage.system.entity.customized.FileInfoAO;
import com.backstage.system.entity.gen.FileInfoCriteria;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangfeng
 * @create 2019-08-14 12:57
 **/
public interface IFileInfoService extends IBaseAOService<FileInfoAO, FileInfoCriteria> {

    /**
     * 上传文件
     *
     * @return
     */
    ServiceResult<FileInfoAO> uploadFile(MultipartFile file) throws Exception;


    /**
     * 下载文件
     *
     * @param fileId
     * @param request
     * @param response
     */
    void downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    ServiceResult<Boolean> deleteFile(String fileId);
}
