package com.online.taxi.controller;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.service.impl.StsService;
import com.online.taxi.utils.OssApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述
 *
 * @date 2018/8/22
 */
@Slf4j
@RestController
@RequestMapping("/sts")
public class StsController {
    @Autowired
    private StsService stsService;

    @Autowired
    private OssApiConfig ossApiConfig;

    @GetMapping({"/authorization", "/authorization/{tokenName}"})
    public ResponseResult getALiYunOSSToken(@PathVariable(value = "tokenName", required = false) String tokenName){
        try {
            // 获取临时授权token
            AssumeRoleResponse assumeRoleResponse = stsService.assumeRole(tokenName);
            log.info("assumeRoleResponse=="+assumeRoleResponse.toString());
            // 构造返回参数
            Map<String,String> map = new HashMap<>(7);
            // 根节点
            map.put("endPoint", ossApiConfig.getEndpoint());
              // 空间名称
            map.put("bucketName", ossApiConfig.getBucketLkmotion());
            // 账号ID
            map.put("accessKeyId", assumeRoleResponse.getCredentials().getAccessKeyId());
            // 密码
            map.put("accessKeySecret", assumeRoleResponse.getCredentials().getAccessKeySecret());
            // token
            map.put("securityToken", assumeRoleResponse.getCredentials().getSecurityToken());
            // 有效时间
            map.put("expiration", assumeRoleResponse.getCredentials().getExpiration());
            //目录
            map.put("path","lkmotion/"+tokenName+"/");

            return ResponseResult.success(map);
        } catch (ClientException e) {
            e.printStackTrace();
            return ResponseResult.fail(1,"获取阿里oss token失败，服务器内部错误！错误码：" + e.getErrCode() + ";错误信息：" + e.getErrMsg());
        }
    }

    /**
     * 删除OSS上的指定目录下的文件
     * @param appName
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult deleteOssAppFile(@RequestBody String appName){
        if(StringUtils.isBlank(appName)){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"文件为空");
        }
        try{
            AssumeRoleResponse assumeRoleResponse = stsService.assumeRole("deleteApp");
            OSSClient ossClient = new OSSClient(ossApiConfig.getEndpoint(), assumeRoleResponse.getCredentials().getAccessKeyId(),
                    assumeRoleResponse.getCredentials().getAccessKeySecret(), assumeRoleResponse.getCredentials().getSecurityToken());
            boolean exists = ossClient.doesBucketExist(ossApiConfig.getBucketLkmotion());
            if (!exists) {
                return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(),"bucket不存在");
            }
            boolean found = ossClient.doesObjectExist(ossApiConfig.getBucketLkmotion(), appName);
            if (found) {
                ossClient.deleteObject(ossApiConfig.getBucketLkmotion(), appName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseResult.success("删除成功！");
    }
}
