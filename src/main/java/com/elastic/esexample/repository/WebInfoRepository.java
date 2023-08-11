package com.elastic.esexample.repository;

import com.elastic.esexample.model.WebInfo;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WebInfoRepository extends ElasticsearchRepository<WebInfo, String> {

    @Query("{\"range\" : {\"ts\" : {\"from\" : \"?0\", \"to\" : \"?1\"}}}")
    List<WebInfo> findByTs(Long start, Long end);
}
