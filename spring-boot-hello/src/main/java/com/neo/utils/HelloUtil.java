package com.neo.utils;

import com.neo.DemoConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 4:07 下午 2021/1/19
 */
@Component
public class HelloUtil {
    private static DemoConfig demoConfig;
    @Resource
    public void setDemoConfig(DemoConfig demoConfig) {
        HelloUtil.demoConfig = demoConfig;
    }

//    static {
//        new HelloUtil();
//        System.out.println(demoConfig.getDemo());;
//    }

    public static void show() {
        System.out.println(demoConfig.getDemo());
    }
}
