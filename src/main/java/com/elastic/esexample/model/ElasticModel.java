package com.elastic.esexample.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "my_index")
@Getter
public class ElasticModel {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

}
