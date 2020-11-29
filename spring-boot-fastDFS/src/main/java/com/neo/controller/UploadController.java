package com.neo.controller;

import com.neo.fastdfs.FastDFSClient;
import com.neo.fastdfs.FastDFSFile;
import org.omg.CORBA.portable.ValueOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class UploadController {
    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(
            @RequestParam("file") MultipartFile[] files,
            RedirectAttributes redirectAttributes) {
        int count = files.length;
        MultipartFile file;
        for (int i = 0; i < count; i++) {
            file = files[i];
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                return "redirect:uploadStatus";
            }
            try {
                // Get the file and save it somewhere
                String path = saveFile(file);
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
                redirectAttributes.addFlashAttribute("path",
                        "file path url '" + path + "'");
            } catch (Exception e) {
                logger.error("upload file failed", e);
            }
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        InputStream ips = FastDFSClient.downFile("group1", "M00/00/00/wKjEhF748zmAWl8jAAB5ejF80eA191.jpg");
        response.setContentType("multipart/form-data");
        //为文件重新设置名字，采用数据库内存储的文件名称
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new String("q.jpg".getBytes("UTF-8"), "ISO8859-1") + "\"");
        OutputStream out = response.getOutputStream();
        //读取文件流
        int len = 0;
        byte[] buffer = new byte[1024 * 10];
        while ((len = ips.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();

    }

    @GetMapping("/download1")
    public ResponseEntity<byte[]> download1(HttpServletResponse response) throws IOException {
        byte[] data = FastDFSClient.downFile1("group1", "M00/00/00/wKjEhF748zmAWl8jAAB5ejF80eA191.jpg");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Connection", "close");
        headers.add("Accept-Ranges", "bytes");
        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode("a.png", "utf-8"));
        ResponseEntity<byte[]> entity = new ResponseEntity<>(data, headers, HttpStatus.OK);
        return entity;

    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }
}