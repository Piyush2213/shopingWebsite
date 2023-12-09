package com.eCommercoal.eCommercialWeb.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.eCommercoal.eCommercialWeb.entity.ProductES;

import com.eCommercoal.eCommercialWeb.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<ProductES> fuzzySearch(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierQuery(approximateProductName);
        SearchResponse<ProductES> response = elasticsearchClient
                .search(s->s.index("product_index").query(supplier.get()), ProductES.class);
        return response;

    }
}
