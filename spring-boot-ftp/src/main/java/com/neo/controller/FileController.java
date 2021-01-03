package com.neo.controller;

import com.neo.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 4:43 下午 2020/12/28
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileUploadUtil fileUploadUtil;
    @GetMapping("/upload")
    public String uploadFile() throws Exception {
        File file = new File("/Users/bcl/work/ideaprojects/spring-boot-examples/spring-boot-ftp/src/main/resources/application.yml");
        String remoteFileName = "app.yml";
        fileUploadUtil.uploadFile(file, "/data/file/", remoteFileName);
        return "success";
    }
}
