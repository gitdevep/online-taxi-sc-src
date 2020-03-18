package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.service.impl.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *  上传证件照片
 * @date 2018-08-10 13:28
 **/
@RestController
@RequestMapping("/file")
@Slf4j
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @ResponseBody
    @PostMapping("/upload")
    public ResponseResult upload(MultipartFile file,String strFileType) throws Exception {

        String fileName = file.getOriginalFilename();
        return uploadService.uploadFiles(file.getBytes(), strFileType, fileName);
    }

}
