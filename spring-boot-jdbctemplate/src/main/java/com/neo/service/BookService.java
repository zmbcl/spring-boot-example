package com.neo.service;

import com.neo.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:30 下午 2020/11/3
 */
@Service
public class BookService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertImage() throws IOException {
        System.out.println("createUser");
        InputStream inputStream = new ClassPathResource("timg-2.jpeg").getInputStream();
        byte[] bytes = inputStream2byte(new ClassPathResource("timg-2.jpeg").getInputStream());
        String base64StringFun = Base64Util.byte2Base64StringFun(bytes);



        LobHandler lobHandler = new DefaultLobHandler();
        jdbcTemplate.execute("INSERT INTO imagedb (image_name, content, description) VALUES (?, ?, ?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement preparedStatement, LobCreator lobCreator) throws SQLException, DataAccessException {
                preparedStatement.setString(1,"image");
                try {
                    lobCreator.setBlobAsBinaryStream(preparedStatement, 2, inputStream, inputStream.available());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                lobCreator.setClobAsString(preparedStatement, 3, "description");
            }
        });

    }

    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
