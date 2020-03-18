package com.online.taxi.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.response.StsToken;
import com.online.taxi.utils.OssApiConfig;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Date;

/**
 * 上传文件
 **/
@Service
@RequiredArgsConstructor
public class UploadService {

    @Autowired
    private OssApiConfig ossApiConfig;

    /**
     * @param targetFile   需要上传OSS文件服务器上生成的目标文件路径
     * @param fileNamePath 当前生成文件路径
     */
    public ResponseResult uploadFileToOss(String targetFile, File fileNamePath,StsToken stsToken) {
        targetFile = ossApiConfig.getOssFileName()+"/"+targetFile;
        JSONObject jsonObject = new JSONObject();
        ResponseResult responseResult = new ResponseResult();
        OSSClient ossClient = new OSSClient(ossApiConfig.getEndpoint(), stsToken.getAccessKeyId(),
                stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        boolean exists = ossClient.doesBucketExist(ossApiConfig.getBucketLkmotion());
        if (!exists) {
            jsonObject.put("status","1");
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"更新失败");
        } else {
            jsonObject.put("status","0");
            if (fileNamePath.exists()) {
                boolean found = ossClient.doesObjectExist(ossApiConfig.getBucketLkmotion(), targetFile);
                if (found) {
                    ossClient.deleteObject(ossApiConfig.getBucketLkmotion(), targetFile);
                }
                ossClient.putObject(ossApiConfig.getBucketLkmotion(), targetFile, fileNamePath);
                String endpoint = ossApiConfig.getEndpoint().replaceFirst("https://","");
                String url = "http://"+ossApiConfig.getBucketLkmotion()+"."+endpoint+targetFile;
                ossClient.shutdown();
                jsonObject.put("fileUrl",url);
                responseResult.setData(jsonObject.toString());
            }
        }
        return responseResult;
    }
}
