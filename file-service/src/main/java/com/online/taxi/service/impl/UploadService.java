package com.online.taxi.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
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

    public ResponseResult uploadFiles(byte[] input, String folder, String fileName) {
        String fileUrl;
        ByteArrayInputStream byteArrayInput = null;
        try {
            // 文件为空错误
            if (null == input) {
                return ResponseResult.fail(1);
            }
            byte[] decodeInput = input;
            int length = decodeInput.length;
            if (length == 0) {
                // 上传文件为空文件错误
                return ResponseResult.fail(1);
            }
            // Client采用原生的HttpClient请求，自身设置了超时机制，不用shutdown
            // 默认连接超时50秒
            // 默认Socket超时50秒
            // 默认最大连接数1024
            OSSClient client = new OSSClient(ossApiConfig.getEndpoint(), ossApiConfig.getAccessId(), ossApiConfig.getAccessKey());

            String key = (folder == null ? "" : (folder + "/")) + fileName;
            URL url;
            // 设置URL过期时间为10年 3600l* 1000*24*365*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            // 生成URL
            url = client.generatePresignedUrl(ossApiConfig.getBucketLkmotion(), key, expiration);
            // 判断文件是否存在，文件存在直接返回文件URL
            if (client.doesObjectExist(ossApiConfig.getBucketLkmotion(), key)) {
                fileUrl =url.toString();
            } else {
                ObjectMetadata metadata = new ObjectMetadata();
                // 采用非chunk方式传输数据包
                metadata.setContentLength(length);
                /* 文件类型设定 */
                byteArrayInput = new ByteArrayInputStream(decodeInput);
                client.putObject(ossApiConfig.getBucketLkmotion(), key, byteArrayInput, metadata);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", 0);
                jsonObject.put("key", key);
                jsonObject.put("url", url.toString());
                fileUrl = url.toString();
            }
            client.shutdown();
            return ResponseResult.success(fileUrl);
        } finally {
            if (null != byteArrayInput) {
                try {
                    byteArrayInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
