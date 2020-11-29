package com.neo.controller;

import com.neo.model.ElasticSearchModel;
import com.neo.repository.ModelRepository;
import com.neo.response.ResponseResult;
import com.sun.deploy.net.proxy.pac.PACFunctionsImpl;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.ml.OpenJobRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.data.elasticsearch.core.document.Document.parse;


/**
 * @author WCNGS@QQ.COM
 * @ClassName ElasticIndexController
 * @Description ElasticSearch索引的基本管理，提供对外查询、删除和新增功能
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/11/19 11:22
 * @Version 1.0.0
 */
@Slf4j
@RequestMapping("/elastic")
@RestController
public class ElasticIndexController extends BaseController {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ModelRepository modelRepository;

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description:
     * @Params:
     * @Date: 2020/11/29
     * @Time: 2:34 下午
     */
    @GetMapping(value = "/old")
    public ResponseResult oldCreate() {
        boolean b0 = elasticsearchRestTemplate.createIndex("myindex");
        boolean b1 = elasticsearchRestTemplate.putMapping(ElasticSearchModel.class);
        return success();
    }

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: 通过配置参数创建索引
     * @Params:
     * @Date: 2020/11/29
     * @Time: 2:34 下午
     */
    @GetMapping(value = "/createIndexByParamters")
    public ResponseResult createIndexByParamters() {
        Map<String, Object> object = new HashMap<>();
        object.put("index.number_of_shards", 1);
        object.put("index.number_of_replicas", 0);
        boolean exist = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).exists();
        if (!exist) {
            elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).create(Document.from((Map<String, Object>) object));
        }
        return success();
    }
    @GetMapping(value = "/createIndexByJson")
    public ResponseResult createIndexByJson() {
        String settings = "{\n" + "        \"index\": {\n" + "            \"number_of_shards\": \"1\",\n"
                + "            \"number_of_replicas\": \"0\",\n" + "            \"analysis\": {\n"
                + "                \"analyzer\": {\n" + "                    \"emailAnalyzer\": {\n"
                + "                        \"type\": \"custom\",\n"
                + "                        \"tokenizer\": \"uax_url_email\"\n" + "                    }\n"
                + "                }\n" + "            }\n" + "        }\n" + '}';


        boolean index = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).create(parse(settings));
        return success();
    }

    /**
     * @Author: bcl
     * @Company: Hunters
     * @Description: 通过类型实体创建索引&mapping
     * @Params:
     * @Date: 2020/11/29
     * @Time: 2:33 下午
     */
    @PostMapping(value = "/createIndex")
    public ResponseResult createIndex() {
        boolean exists = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).exists();
        if (!exists) {
            boolean isCreate = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).create();
            elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).createMapping();
        }
        return success();
    }


    @GetMapping("/oldPutMappings")
    public ResponseResult oldPutMappings() {
        elasticsearchRestTemplate.putMapping(ElasticSearchModel.class);
        return success();
    }

    @GetMapping("/putMappings")
    public ResponseResult putMappings() {
        String mappings = "{\n" +
                "    \"properties\": {\n" +
                "      \"age\":    { \"type\": \"integer\" },  \n" +
                "      \"email\":  { \"type\": \"keyword\"  }, \n" +
                "      \"name\":   { \"type\": \"text\"  }     \n" +
                "    }\n" +
                "}";

        // when
        boolean index = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).putMapping(parse(mappings));

        return success();
    }


    @GetMapping(value = "/exist/{index}")
    public ResponseResult indexExist(@PathVariable(value = "index") String index) {

        return success();
    }

    @GetMapping(value = "/del/{index}")
    public ResponseResult indexDel(@PathVariable(value = "index") String index) {
        return success();
    }

    @GetMapping("/addDocument")
    public ResponseResult addDocument() {
        ElasticSearchModel model = new ElasticSearchModel()
                .setId(32311)
                .setDocid(9456)
                .setItemid(4114)
                .setItemType("采风")
                .setDocclobnohtml("中华人民共和国银行业监督管理办法处理调理山东老家了")
                .setDoctitle("云南银保监局机关行政处罚信息公开表")
                .setPublishdate(new Date(System.currentTimeMillis()))
                .setOrgcode("345")
                .setIndexno("0002")
                .setDocumentno("0002")
                .setYear("2020")
                .setName("bcl")
                .setDocUuid(UUID.randomUUID().toString().replace("-", ""))
                .setDatafrom("datafrom2")
                .setBuilddate("2020-08-26")
                .setSolicitFlag("solicit")
                .setDocSummary("docSummary");
        modelRepository.save(model);
        return success();
    }

    @GetMapping(value = "queryDemo")
    public ResponseResult queryDemo() {

        String message = "中华";
        String field = "docclobnohtml";
        String sortField = "docid";
        String sortRule = "ASC";
        int page = 1;
        int size = 1;

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSort(SortBuilders.fieldSort(sortField).order(SortOrder.valueOf(sortRule)))
                .withPageable(PageRequest.of(page, size))
                .withQuery(QueryBuilders.matchQuery(field, message))
                .withHighlightFields(new HighlightBuilder.Field(field).preTags("<font color='red'>").postTags("</font>").numOfFragments(0))
                .build();
        SearchHits<ElasticSearchModel> search = elasticsearchRestTemplate.search(searchQuery, ElasticSearchModel.class);
        List<SearchHit<ElasticSearchModel>> searchHits = search.getSearchHits();
        return success(searchHits);
    }
}
