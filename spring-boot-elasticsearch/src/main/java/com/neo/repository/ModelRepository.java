package com.neo.repository;

import com.neo.model.ElasticSearchModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:37 上午 2020/11/25
 */
@Repository
public interface ModelRepository extends ElasticsearchRepository<ElasticSearchModel, String> {
}
