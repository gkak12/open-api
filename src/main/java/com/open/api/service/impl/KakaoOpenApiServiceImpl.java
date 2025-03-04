package com.open.api.service.impl;

import com.open.api.annotation.OpenApiAnnotation;
import com.open.api.domain.dto.OpenApiDto;
import com.open.api.service.OpenApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@OpenApiAnnotation(value = "kakao")
public class KakaoOpenApiServiceImpl implements OpenApiService {

    @Override
    public String getOpenApi(OpenApiDto openApiDto) {
        log.info("KakaoOpenApiServiceImpl getOpenApi");

        return "";
    }
}
