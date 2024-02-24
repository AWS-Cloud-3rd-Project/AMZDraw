package com.amzmall.project.admin.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
class ProductGraphqlAdapter {
    @JsonProperty("products")
    List<ProductGraphqlDTO> productGraphqlDTOS;
}