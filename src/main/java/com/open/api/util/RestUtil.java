package com.open.api.util;

import com.open.api.domain.dto.OpenApiDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Executors;

@Slf4j
public class RestUtil {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)) // 타임아웃 설정
            .executor(Executors.newFixedThreadPool(10)) // 10개의 스레드 풀 사용
            .version(HttpClient.Version.HTTP_2) // HTTP/2 사용
            .build();

    public static String getHttpsRestApi(OpenApiDto openApiDto) throws Exception {
        String api = openApiDto.getApi();
        log.info("apiUrl: {}", api);

        if (StringUtils.isEmpty(api)) {
            throw new Exception("WebUtil getHttpsRestApi: apiUrl is empty or null.");
        }

        StringBuilder sb = new StringBuilder(api).append("?");
        Map<String, String> params = openApiDto.getParams();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            log.debug("{}: {}", entry.getKey(), entry.getValue());
        }

        String urlAddr = sb.deleteCharAt(sb.length() - 1).toString();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(urlAddr))
                .timeout(Duration.ofSeconds(10))
                .GET();

        Map<String, String> headers = openApiDto.getHeaders();
        headers.put("charset", "UTF-8");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json");

        headers.forEach(requestBuilder::header);
        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        int statusCode = response.statusCode();
        if (statusCode >= 400) {
            throw new Exception(statusCode + " error.");
        }

        return response.body();
    }
}
