package com.backstage.system.controller;


import com.backstage.system.log.LogOperation;
import com.backstage.system.service.IFileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yf on 2019/8/13.
 * 文件上传、下载、删除 controller
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FlieController {

    private static Logger LOG = LoggerFactory.getLogger(FlieController.class);


    @Resource
    private IFileInfoService fileInfoService;


    /**
     * 文件上传
     *
     * @return
     */
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "query", required = true),
    })
    @PostMapping("/upload")
    @LogOperation(action = "上传文件")
    public Object uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return fileInfoService.uploadFile(file);
    }

    /**
     * 下载
     *
     * @param fileId   文件id
     * @param response
     * @return
     */
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/download")
    @LogOperation(action = "下载文件")
    public void downloadFile(@RequestParam(name = "fileId") String fileId,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileInfoService.downloadFile(fileId, request, response);
    }

    /**
     * 文件删除
     *
     * @return
     */
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/delete")
    @LogOperation(action = "删除文件")
    public Object deleteFile(@RequestParam("fileId") String fileId) {
        return fileInfoService.deleteFile(fileId);
    }

}