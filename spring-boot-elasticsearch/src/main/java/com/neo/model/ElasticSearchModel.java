package com.neo.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Auther: bcl
 * @Description:
 * @Date: Create in 9:04 上午 2020/11/24
 */
@Data
@Accessors(chain = true)
@Document(indexName = "model", createIndex = false, shards = 1, replicas = 0)
public class ElasticSearchModel {
    @Id
    @Field(type = FieldType.Integer)
    private int id;
    @Field(type = FieldType.Integer)
    private int docid;
    @Field(type = FieldType.Integer)
    private int itemid;
    @Field(type = FieldType.Keyword)
    private String itemname;
    @Field(type = FieldType.Keyword)
    private String itemType;
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String docclobnohtml;
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String doctitle;
    @Field(type = FieldType.Date,format = DateFormat.date)
    private Date publishdate;
    @Field(type = FieldType.Keyword)
    private String orgcode;
    @Field(type = FieldType.Keyword)
    private String indexno;
    @Field(type = FieldType.Keyword)
    private String documentno;
    @Field(type = FieldType.Keyword)
    private String year;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String docUuid;
    @Field(type = FieldType.Keyword)
    private String datafrom;
    @Field(type = FieldType.Keyword)
    private String generaltype;
    @Field(type = FieldType.Keyword)
    private String builddate;
    @Field(type = FieldType.Keyword)
    private String docTitle;
    @Field(type = FieldType.Keyword)
    private String solicitFlag;
    @Field(type = FieldType.Keyword)
    private String docSummary;
}
