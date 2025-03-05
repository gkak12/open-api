package com.open.api.service.impl;

import com.open.api.annotation.OpenApiAnnotation;
import com.open.api.domain.dto.OpenApiDto;
import com.open.api.service.OpenApiService;
import com.open.api.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@OpenApiAnnotation(value = "naver")
public class NaverOpenApiServiceImpl implements OpenApiService {

    @Value("${api.naver.id}")
    private String clientId;

    @Value("${api.naver.secret}")
    private String clientSecret;

    @Override
    public String getOpenApi(OpenApiDto openApiDto) throws Exception {
        log.info("NaverOpenApiServiceImpl getOpenApi");

        openApiDto.getHeaders().put("X-Naver-Client-Id", clientId);
        openApiDto.getHeaders().put("X-Naver-Client-Secret", clientSecret);

        return RestUtil.getHttpsRestApi(openApiDto);
    }
}
