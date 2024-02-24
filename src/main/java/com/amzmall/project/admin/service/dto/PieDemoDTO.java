package com.amzmall.project.admin.service.dto;

import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class PieDemoDTO {
    private final List<String> categories;
    private final List<Integer> sales;
}