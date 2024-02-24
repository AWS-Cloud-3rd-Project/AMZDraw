package com.amzmall.project.admin.adapter;

import com.amzmall.project.admin.service.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;


@Component
@RequiredArgsConstructor
public class ProductAdapter {

    private final RestTemplate restTemplate;

    @Value("${apis.product-graphql-api.url}")
    private String productGraphqlApiUrl;

    private String query = "query {\n" +
            "  products(page: 0, size:20) {\n" +
            "    productId\n" +
            "    name\n" +
            "    productStatus\n" +
            "    price\n" +
            "    createdAt\n" +
            "  }\n" +
            "}";

    public <CustomGraphQLClient> List<ProductDTO> findAll(Pageable pageable) {
        CustomGraphQLClient client = GraphQLClient.createCustom(productGraphqlApiUrl, (url, headers, body) -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            headers.forEach(httpHeaders::addAll);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, httpHeaders), String.class);
            return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
        });

        GraphQLResponse graphQLResponse = client.executeQuery(query, emptyMap(), "");
        ProductGraphqlAdapter productGraphqlAdapter = graphQLResponse.dataAsObject(ProductGraphqlAdapter.class);
        List<ProductDTO> products = productGraphqlAdapter.productGraphqlDTOS.stream()
                .map(p -> {
                    return ProductDTO.builder().productId(p.getProductId()).price(p.getPrice()).createdAt(p.getCreatedAt()).build();
                }).collect(Collectors.toList());

        return products;
    }
}0