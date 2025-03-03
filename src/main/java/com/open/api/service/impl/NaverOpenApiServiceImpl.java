package com.open.api.service.impl;

import com.open.api.domain.dto.OpenApiDto;
import com.open.api.service.OpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NaverOpenApiServiceImpl implements OpenApiService {

    @Override
    public String getOpenApi(OpenApiDto openApiDto) {
        return "";
    }
}
