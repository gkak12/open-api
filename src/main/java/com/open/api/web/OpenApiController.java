package com.open.api.web;

import com.open.api.annotation.OpenApiAnnotationFactory;
import com.open.api.domain.dto.OpenApiDto;
import com.open.api.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/open-api")
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiAnnotationFactory openApiAnnotationFactory;

    @GetMapping
    public void getOpenAPI(@ParameterObject OpenApiDto openApiDto) {
        log.info("OpenApiController getOpenAPI: {}", openApiDto.getApi());

        OpenApiService openApiService = openApiAnnotationFactory.getOpenApiService(openApiDto.getApi());
        try {
            openApiService.getOpenApi(openApiDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
