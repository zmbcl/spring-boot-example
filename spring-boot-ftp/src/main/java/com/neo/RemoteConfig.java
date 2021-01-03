package com.neo;

import com.neo.entity.ScpConnectEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 5:05 下午 2020/12/28
 */
@Configuration
public class RemoteConfig {
    @Value("${remoteServer.host:192.168.196.132}")
    private String host;

    @Value("${remoteServer.password:root}")
    private String passWord;

    @Value("${remoteServer.username:root}")
    private String userName;

    @Bean
    public ScpConnectEntity getScpConnectEntity() {
        return new ScpConnectEntity(host, userName, passWord);
    }
}
