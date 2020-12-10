package com.neo.controller;

import com.mysql.jdbc.StringUtils;
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
import org.springframework.beans.factory.annotation.Value;
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
    @Value(value = "${elastic.shards: 1}")
    private int shards;
    @Value("${elastic.replicas: 0}")
    private int replicas;
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
    @GetMapping(value = "/deprecatedCreateIndexAndMapping")
    @Deprecated
    public ResponseResult oldCreate() {
        boolean result01 = elasticsearchRestTemplate.createIndex("myindex");
        boolean result02 = elasticsearchRestTemplate.putMapping(ElasticSearchModel.class);
        return success(result01 + "&" + result02);
    }


    @GetMapping(value = "/createIndexByParamters")
    public ResponseResult createIndexByParamters() {
        Map<String, Object> object = new HashMap<>();
        object.put("index.number_of_shards", shards);
        object.put("index.number_of_replicas", replicas);
        boolean exist = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).exists();
        if (!exist) {
            boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).create(Document.from((Map<String, Object>) object));
            if (result) {
                return success(result);
            }
        }
        return err();
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
            boolean result01 = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).create();
            if (result01) {
                Document document = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).createMapping();
                return success(document);
            }
            else {
                return err();
            }
        }
        else {
            return success("索引已存在");
        }
    }

    @GetMapping("/createMapping")
    public ResponseResult createMapping() {
        Document document = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).createMapping();
        if (document != null) {
            return success(document);
        }
        return err();
    }


    @GetMapping("/deprecatedPutMappings")
    @Deprecated
    public ResponseResult deprecatedPutMappings() {
        boolean result = elasticsearchRestTemplate.putMapping(ElasticSearchModel.class);
        if (result) {
            return success();
        }
        return err();
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
        boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).putMapping(parse(mappings));
        if (result) {
            return success();
        }
        return err();
    }

    @GetMapping("/createIndexAndMapping")
    public ResponseResult createIndexAndMapping() {
        Map<String, Object> object = new HashMap<>();
        object.put("index.number_of_shards", shards);
        object.put("index.number_of_replicas", replicas);
        boolean exist = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).exists();
        if (!exist) {
            boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("model")).create(Document.from((Map<String, Object>) object));
            if (result) {
                Document document = elasticsearchRestTemplate.indexOps(ElasticSearchModel.class).createMapping();
                if (document != null) {
                    return success(document);
                }
                return err("创建mapping失败");
            }
            else {
                return err("创建索引失败");
            }
        }
        return err();
    }


    @GetMapping(value = "/exist/{index}")
    public ResponseResult indexExist(@PathVariable(value = "index") String index) {
        boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("index")).exists();
        return success();
    }

    @GetMapping(value = "/del/{index}")
    public ResponseResult indexDel(@PathVariable(value = "index") String index) {
        boolean result = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(index)).delete();
        if (result) {
            return success();
        }
        return err();
    }

    @GetMapping("/addDocument")
    public ResponseResult addDocument() {
        ElasticSearchModel model = new ElasticSearchModel()
                .setId(32311)
                .setDocid(9456)
                .setItemid(4114)
                .setItemname("采风")
                .setDocclobnohtml("中华人民共和国银行业监督管理办法处理调理山东老家了")
                .setDoctitle("云南银保监局机关行政处罚信息公开表")
                .setPublishdate(new Date(System.currentTimeMillis()))
                .setOrgcode("345")
                .setIndexno("0002")
                .setDocumentno("0002")
                .setYear("2020")
                .setName("bcl")
                .setDocuuid(UUID.randomUUID().toString().replace("-", ""))
                .setDatafrom("datafrom2")
                .setBuilddate("2020-08-26")
                .setSolicitflag("solicit")
                .setDocsummary("docSummary");
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
