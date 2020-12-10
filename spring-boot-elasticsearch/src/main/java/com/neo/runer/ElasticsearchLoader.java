package com.neo.runer;


import com.mysql.jdbc.log.LogFactory;
import com.neo.model.ElasticSearchModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:14 下午 2020/12/10
 */
@Component
public class ElasticsearchLoader implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(ElasticsearchLoader.class);
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Value(value = "${elastic.shards: 1}")
    private int shards;
    @Value("${elastic.replicas: 0}")
    private int replicas;

    @Override
    public void run(ApplicationArguments args) {

        try {
            Map<String, Object> object = new HashMap<>();
            object.put("index.number_of_shards", shards);
            object.put("index.number_of_replicas", replicas);
            boolean exist = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).exists();
            if (!exist) {
                boolean result01 = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).create(Document.from((Map<String, Object>) object));
                if (result01) {
                    Document document = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).createMapping();
                    boolean result02 = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).putMapping(document);
                    if (document != null && result02) {
                        logger.info("创建索引&映射成功");
                    }
                    logger.info("创建索引成功");
                }
                else {
                    logger.info("创建索引失败");
                }
            }
            logger.info("索引已存在");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
