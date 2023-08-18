package com.elastic.esexample.repository;

import com.elastic.esexample.model.ElasticModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRepository
//        extends ElasticsearchRepository<ElasticModel, String>
        {
    List<ElasticModel> findByName(String name);
}
