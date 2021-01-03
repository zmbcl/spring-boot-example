package com.neo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 4:29 下午 2020/12/28
 */
@Data
@Accessors(chain = true)
public class ResultEntity {
    private String code;
    private String message;
    private File file;

    public ResultEntity(String code, String message, File file) {
        super();
        this.code = code;
        this.message = message;
        this.file = file;
    }
}
