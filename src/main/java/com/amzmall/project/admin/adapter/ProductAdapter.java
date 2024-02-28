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

    private static final String QUERY_TEMPLATE = "query {\n" +
            "  products(page: %d, size:%d) {\n" +
            "    productId\n" +
            "    name\n" +
            "    productStatus\n" +
            "    price\n" +
            "    createdAt\n" +
            "  }\n" +
            "}";

    public List<ProductDTO> findAll(Pageable pageable) {
        String query = String.format(QUERY_TEMPLATE, pageable.getPageNumber(), pageable.getPageSize());

        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(
                productGraphqlApiUrl,
                HttpMethod.POST,
                new HttpEntity<>(query, httpHeaders),
                String.class
        );

        List<ProductDTO> products = extractProductsFromResponse(exchange.getBody());
        return products;
    }

    private List<ProductDTO> extractProductsFromResponse(String responseBody) {
        // Implement logic to extract products from the GraphQL response
        // You can use a library like Jackson or Gson to parse the JSON response
        // and map it to a list of ProductDTO objects
        // For simplicity, let's assume a method called parseResponse is available
        return parseResponse(responseBody);
    }

    private List<ProductDTO> parseResponse(String responseBody) {
        // Implement the logic to parse the GraphQL response and map it to ProductDTO
        // This could be done using a JSON parsing library
        // For simplicity, we'll assume a placeholder implementation
        return List.of();
    }
}

//    public <CustomGraphQLClient> List<ProductDTO> findAll(Pageable pageable) {
//        CustomGraphQLClient client = GraphQLClient.createCustom(productGraphqlApiUrl, (url, headers, body) -> {
//            HttpHeaders httpHeaders = new HttpHeaders();
//            headers.forEach(httpHeaders::addAll);
//            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, httpHeaders), String.class);
//            return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
//        });
//
//        GraphQLResponse graphQLResponse = client.executeQuery(query, emptyMap(), "");
//        ProductGraphqlAdapter productGraphqlAdapter = graphQLResponse.dataAsObject(ProductGraphqlAdapter.class);
//        List<ProductDTO> products = productGraphqlAdapter.productGraphqlDTOS.stream()
//                .map(p -> {
//                    return ProductDTO.builder().productId(p.getProductId()).price(p.getPrice()).createdAt(p.getCreatedAt()).build();
//                }).collect(Collectors.toList());
//
//        return products;
