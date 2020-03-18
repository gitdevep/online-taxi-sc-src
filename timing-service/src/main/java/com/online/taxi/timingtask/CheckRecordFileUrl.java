package com.online.taxi.timingtask;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.online.taxi.constatnt.BusinessInterfaceStatus;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.entity.SecretVoiceRecords;
import com.online.taxi.mapper.SecretVoiceRecordsMapper;
import com.online.taxi.task.OtherInterfaceTask;
import com.online.taxi.response.StsToken;
import com.online.taxi.service.impl.UploadService;
import com.online.taxi.utils.AliPhoneConfig;
import com.online.taxi.utils.OssApiConfig;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 通话记录定时任务
 */
@Component
@Slf4j
public class CheckRecordFileUrl {
    private static final String IS_OK = "OK";

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SecretVoiceRecordsMapper secretVoiceRecordsMapper;

    @Autowired
    private OssApiConfig ossConfig;

    @Autowired
    private AliPhoneConfig aliPhoneConfig;

    @Autowired
    private OtherInterfaceTask otherInterfaceTask;

    @Scheduled(cron = "0 */2 *  * * ?")
    public void checkRecordFileUrlStart() {
        List<SecretVoiceRecords> secretVoiceRecords;
        secretVoiceRecords = secretVoiceRecordsMapper.selectRecordsUrlIsNull();
        if(secretVoiceRecords.size()>0) {
            int size = secretVoiceRecords.size();
            for(int i=0;i<size;i++) {
                SecretVoiceRecords newSecretVoiceRecords = secretVoiceRecords.get(i);
                String callId = newSecretVoiceRecords.getCallId();
                Date callTime = newSecretVoiceRecords.getCallTime();
                try {
                    //获取录音下载地址
                    String DownloadUrl = getRecordFileUrl(callId,callTime);
                    if(StringUtils.isNotBlank(DownloadUrl)) {
                        String[] a = StringUtils.substringsBetween(DownloadUrl, "com/", "?");
                        String fileName = a[0];
                        String uploadFailName = File.separator+aliPhoneConfig.getFilePath()+File.separator+fileName;
                        File file = new File(uploadFailName);
                        //判断文件是否存在，文件不存在，则下载，如果文件存在，则上传至OSS
                        if(judeFileExists(file)) {
                            try {
                                //下载录音文件
                                downLoadFromUrl(DownloadUrl,fileName,File.separator+aliPhoneConfig.getFilePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        String targetFile = ossConfig.getOssFileName()+File.separator+fileName;
                        String tokenName = "phone";
                        //获取token
                        StsToken stsToken = otherInterfaceTask.getALiToken(tokenName);
                        if(null != stsToken){
                            //上传OSS
                            ResponseResult responseResult = uploadService.uploadFileToOss(targetFile, file,stsToken);
                            log.info("responseResult:"+responseResult);
                            if (responseResult.getCode() == BusinessInterfaceStatus.SUCCESS.getCode()) {
                                //判断上传状态，如果为0，并且fileUrl不为空，则修改上传文件状态
                                JSONObject jsStr = JSONObject.fromObject(responseResult);
                                JSONObject dataJson = JSONObject.fromObject(jsStr.get("data"));
                                Integer status = Integer.valueOf(dataJson.get("status").toString());
                                String ossUrl = dataJson.get("fileUrl").toString();
                                log.info("ossUrl===="+ossUrl);
                                if(status == 0 && StringUtils.isNotBlank(ossUrl)) {
                                    newSecretVoiceRecords.setFlag(1);
                                    newSecretVoiceRecords.setCallId(callId);
                                    newSecretVoiceRecords.setOssDownloadUrl(ossUrl);
                                    secretVoiceRecordsMapper.updateByPrimaryKeySelective(newSecretVoiceRecords);
                                }
                            }
                        }
                        //删除文件，防止临时文件夹由于上传OSS失败而导致文件内容增大
                        deleteFile(uploadFailName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String getRecordFileUrl(String callId, Date callTime) throws Exception{
    	// 此代码没问题，缺jar包注释掉
    	//        String DownloadUrl = null;
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//        //初始化ascClient需要的几个参数
//        final String product = "Dyplsapi";
//        final String domain = "dyplsapi.aliyuncs.com";
//        //初始化ascClient,暂时不支持多region
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliPhoneConfig.getKey(), aliPhoneConfig.getSecret());
//        try {
//            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//        //组装请求对象-具体描述见控制台-文档部分内容
//        QueryRecordFileDownloadUrlRequest request = new QueryRecordFileDownloadUrlRequest();
//        //对应的号池Key
//        request.setPoolKey(aliPhoneConfig.getPoolKey());
//        //话单回执中返回的标识每一通唯一通话行为的callId
//        request.setCallId(callId);
//        //话单回执中返回的callTime字段
//        String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(callTime);
//        request.setCallTime(dateStr);
//
//        //hint 此处可能会抛出异常，注意catch
//        QueryRecordFileDownloadUrlResponse response;
//        try {
//            response = acsClient.getAcsResponse(request);
//            if(response.getCode() != null && response.getCode().equals(IS_OK)) {
//                //请求成功
//                DownloadUrl = response.getDownloadUrl();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return DownloadUrl;
    	
    	return "downloadUrl";
    }

    /**
     * 下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @return
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    /**
     * 判断文件是否存在
     * @param file
     * @return
     */
    public static boolean judeFileExists(File file) {
        if (file.exists()) {
            return false;
        } else {//不存在
            return true;
        }
    }

    /**
     * 文件上传成功并修改状态后删除文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.error("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                log.error("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            log.error("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
