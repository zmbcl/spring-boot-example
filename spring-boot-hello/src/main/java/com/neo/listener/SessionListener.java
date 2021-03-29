package com.neo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 10:50 上午 2021/1/2
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        System.out.printf("我生成啦 %s\n", System.currentTimeMillis());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.printf("我死啦 %s\n", System.currentTimeMillis());
    }
}
