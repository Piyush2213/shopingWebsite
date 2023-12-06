package com.eCommercoal.eCommercialWeb.repository;

import com.eCommercoal.eCommercialWeb.entity.ProductES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductESRepository extends ElasticsearchRepository <ProductES, Integer> {
}
