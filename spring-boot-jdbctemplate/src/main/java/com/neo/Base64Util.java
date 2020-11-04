package com.neo;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 8:34 上午 2020/11/4
 */
public class Base64Util {

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: base64字符串转byte[]
     * @Params:
     * @Date: 2020/11/4
     * @Time: 8:38 上午
     */
    public static byte[] base64String2ByteFun(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: byte[]转base64
     * @Params:
     * @Date: 2020/11/4
     * @Time: 8:38 上午
     */
    public static String byte2Base64StringFun(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: 将图片文件转化为字节数组字符串，并对其转化为--->Base64编码
     * @Params:
     * @Date: 2020/11/4
     * @Time: 8:36 上午
     */
    public String GetImageStr(String imgFilePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: 对字节数组字符串进行Base64解码并-->生成图片
     * @Params:
     * @Date: 2020/11/4
     * @Time: 8:36 上午
     */
    public static boolean GenerateImage(String imgStr, String imgFilePath) {
        // 图像数据为空
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
