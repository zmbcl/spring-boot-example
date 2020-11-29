package com.neo.conf;

import com.neo.fastdfs.FastDFSClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import com.github.tobato.fastdfs.FdfsClientConfig;
/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 3:27 下午 2020/7/2
 */
@Configuration
@Import(FdfsClientConfig.class)
// Jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class DfsConfig {
}
