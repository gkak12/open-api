package com.open.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiDto {

    private String api;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> params;
}
