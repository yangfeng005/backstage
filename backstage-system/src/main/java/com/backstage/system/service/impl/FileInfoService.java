package com.backstage.system.service.impl;

import com.backstage.common.utils.lang.DateTimeUtil;
import com.backstage.core.mapper.BaseGeneratedMapper;
import com.backstage.core.result.ServiceResult;
import com.backstage.core.result.ServiceResultHelper;
import com.backstage.core.service.AbstractBaseAOService;
import com.backstage.system.auth.AuthUtil;
import com.backstage.system.dao.gen.FileInfoGeneratedMapper;
import com.backstage.system.entity.customized.FileInfoAO;
import com.backstage.system.entity.customized.UserAO;
import com.backstage.system.entity.gen.FileInfoCriteria;
import com.backstage.system.service.IFileInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author yangfeng
 * <p>
 * 文件服务
 * @create 2019-08-13 10:47
 **/
@Service
public class FileInfoService extends AbstractBaseAOService<FileInfoAO, FileInfoCriteria> implements IFileInfoService {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FileInfoGeneratedMapper fileInfoGeneratedMapper;

    @Override
    protected BaseGeneratedMapper<FileInfoAO, FileInfoCriteria> getGeneratedMapper() {
        return fileInfoGeneratedMapper;
    }

    @Value("${file.upload.path}")
    private String uploadParentPath;


    /**
     * 上传文件
     *
     * @return
     */
    @Override
    public ServiceResult<FileInfoAO> uploadFile(MultipartFile file) throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String uploadFilePath = uploadParentPath + File.separator+ DateTimeUtil.format(new Date(),DateTimeUtil.YEAR_MONTH)+File.separator;
        String filepath = File.separator+DateTimeUtil.format(new Date(),DateTimeUtil.YEAR_MONTH)+File.separator;
        File dest = new File(uploadFilePath);
        // 检测是否存在目录
        if (!dest.exists()) {
            dest.mkdirs();
        }
        //文件重命名，防止覆盖
        int fileMax = getFileMax(uploadFilePath, fileName);
        StringBuilder destFilePath = new StringBuilder();
        if (fileMax > 0) {
            destFilePath.append(uploadFilePath).append(fileName.substring(0, fileName.lastIndexOf("."))).
                    append("(" + fileMax + ")").append(fileName.substring(fileName.lastIndexOf(".")));
        } else {
            destFilePath.append(uploadFilePath).append(fileName);
        }

        File destFile = new File(destFilePath.toString());
        file.transferTo(destFile);
        filepath += destFile.getName();
        FileInfoAO fileInfoAO = new FileInfoAO();
        fileInfoAO.setCreateTime(DateTimeUtil.formatDateTime(new Date()));
        fileInfoAO.setFileName(fileName);
        fileInfoAO.setFileSize(file.getSize()+"");
        //存储相对路径
        LOG.info("filepath "+filepath);
        fileInfoAO.setFilePath(filepath);
        fileInfoAO.setFileType(StringUtils.substringAfter(fileName, "."));// 获取文件的扩展名
        UserAO user = AuthUtil.getCurrentUser();
        fileInfoAO.setPulisher(user != null ? user.getUserName() : null);
        insert(fileInfoAO);
        return ServiceResultHelper.genResultWithSuccess(fileInfoAO);
    }


    /**
     * 获取文件夹中相同文件名的文件个数
     *
     * @param filePath
     * @param fileName
     * @return
     */
    private int getFileMax(String filePath, String fileName) {
        File file = new File(filePath);
        File[] files;
        int number = 0;
        if (file.isDirectory()) {
            files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile()
                        && (f.getName().substring(0, f.getName().lastIndexOf("."))
                        .contains(fileName.substring(0, fileName.lastIndexOf(".")))
                        && f.getName().substring(f.getName().lastIndexOf("."))
                        .equals(fileName.substring(fileName.lastIndexOf("."))))) {
                    number = number + 1;
                }
            }
        }
        return number;
    }

    /**
     * 下载文件
     *
     * @param fileId   文件id
     * @param request
     * @param response
     */
    @Override
    public void downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServiceResult<FileInfoAO> result = selectByPrimaryKey(fileId);
        if (result == null || !result.isSucceed() || result.getData() == null) {
            return;
        }
        String fileName = result.getData().getFileName().replace(",", "");
        //处理中文文件名乱码
        if (request.getHeader("User-Agent").toUpperCase().contains("MSIE") ||
                request.getHeader("User-Agent").toUpperCase().contains("TRIDENT")
                || request.getHeader("User-Agent").toUpperCase().contains("EDGE")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        response.reset();
        // 通知浏览器进行文件下载
        response.setContentType(result.getData().getFileType());
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(uploadParentPath
                + result.getData().getFilePath()));
        // 设置文件大小
        response.addHeader("Content-Length", String.valueOf(br.available()));
        byte[] buf = new byte[1024];
        int len;
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
    }


    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @Override
    public ServiceResult<Boolean> deleteFile(String fileId) {
        if (StringUtils.isEmpty(fileId)) {
            return ServiceResultHelper.genResultWithFaild("删除失败,文件ID为空", -1);
        }
        ServiceResult<FileInfoAO> result = selectByPrimaryKey(fileId);
        if (result != null && result.isSucceed() && result.getData() != null) {
            if (StringUtils.isEmpty(result.getData().getFilePath())) {
                return ServiceResultHelper.genResultWithFaild("删除失败,文件路径为空", -1);
            }
            File file = new File(uploadParentPath + result.getData().getFilePath());
            if (file.exists()) {
                file.delete();
                deleteById(fileId);
                return ServiceResultHelper.genResultWithSuccess(true);
            }
        }
        return ServiceResultHelper.genResultWithFaild("删除失败", -1);
    }
}
