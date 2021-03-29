package com.neo.utils.demo;

import com.neo.utils.HelloUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 4:10 下午 2021/1/19
 */
@Component
public class Hello {
    @PostConstruct
    public void init() {
        HelloUtil.show();
    }
}
