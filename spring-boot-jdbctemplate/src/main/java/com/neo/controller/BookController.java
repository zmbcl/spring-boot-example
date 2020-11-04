package com.neo.controller;

import com.neo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:31 下午 2020/11/3
 */
@RestController
public class BookController {
    @Autowired
    private BookService userService;


    @GetMapping("/insert")
    public String insertImage() {
        try {
            userService.insertImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
