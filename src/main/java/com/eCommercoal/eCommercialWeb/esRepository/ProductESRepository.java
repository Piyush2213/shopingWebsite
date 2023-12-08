package com.eCommercoal.eCommercialWeb.esRepository;

import com.eCommercoal.eCommercialWeb.entity.ProductES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductESRepository extends ElasticsearchRepository <ProductES, Integer> {
}
