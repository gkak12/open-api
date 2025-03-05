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
@OpenApiAnnotation(value = "kakao")
public class KakaoOpenApiServiceImpl implements OpenApiService {

    @Value("${api.kakao.token}")
    private String token;

    @Override
    public String getOpenApi(OpenApiDto openApiDto) throws Exception {
        log.info("KakaoOpenApiServiceImpl getOpenApi");

        openApiDto.getHeaders().put("Authorization", "KakaoAK ".concat(token));

        return RestUtil.getHttpsRestApi(openApiDto);
    }
}
