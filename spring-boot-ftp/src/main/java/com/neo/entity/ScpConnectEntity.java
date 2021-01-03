package com.neo.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 4:29 下午 2020/12/28
 */
@Data
@Accessors(chain = true)
public class ScpConnectEntity {
    private String userName;
    private String passWord;
    private String url;
    private String targetPath;

    public ScpConnectEntity(String userName, String passWord, String url) {
        this.userName = userName;
        this.passWord = passWord;
        this.url = url;
    }
}
