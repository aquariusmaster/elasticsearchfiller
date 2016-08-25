package com.aquariusmaster.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by harkonnen on 15.07.16.
 */
public interface ElasticsearchCrudRepository extends ElasticsearchRepository<User, Long>, PagingAndSortingRepository<User, Long> {
}