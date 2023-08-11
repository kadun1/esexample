package com.elastic.esexample.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "surffy_eum_web_info")
@Getter
public class WebInfo {

    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private Integer ts;

    @Field(type = FieldType.Integer)
    private Integer count;

    @Field(type = FieldType.Float)
    private Float pt;

    @Field(type = FieldType.Text)
    private String subDomain;

    @Field(type = FieldType.Text)
    private String visitorKey;

    @Field(type = FieldType.Text)
    private String mainDomain;

    @Field(type = FieldType.Text)
    private String os;

    @Field(type = FieldType.Text)
    private String browser;

    @Field(type = FieldType.Text)
    private String category;
}
