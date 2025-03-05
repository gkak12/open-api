package com.open.api.util;

import com.open.api.domain.dto.OpenApiDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class RestUtil {

    public static String getHttpsRestApi(OpenApiDto openApiDto) throws Exception {
        String api = openApiDto.getApi();
        log.info("apiUrl: {}", api);

        if (StringUtils.isEmpty(api)) {
            throw new Exception("WebUtil getHttpsRestApi: apiUrl is empty or null.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(api).append("?");

        Map<String, String> params = openApiDto.getParams();

        for (String paramKey : params.keySet()) {    // parameter 추가
            String paramValue = params.get(paramKey);
            sb.append(paramKey).append("=").append(paramValue).append("&");

            log.debug("{}: {}", paramKey, paramValue);
        }

        String urlAddr = sb.deleteCharAt(sb.length() - 1).toString();

        URL url = new URL(urlAddr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        try {
            conn.setRequestMethod("GET");    // method 설정

            Map<String, String> headers = openApiDto.getHeaders();    // header 추가

            headers.put("charset", "UTF-8");
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("Accept", "application/json");

            for (String haaderKey : headers.keySet()) {
                String headerValue = headers.get(haaderKey);
                conn.addRequestProperty(haaderKey, headerValue);

                log.debug("{}: {}", haaderKey, headerValue);
            }

            conn.connect();    // rest api 접속
            int statusCode = conn.getResponseCode();

            if (statusCode == 403) {
                throw new Exception("403 error.");
            } else if (statusCode == 404) {
                throw new Exception("404 error.");
            } else if (statusCode == 405) {
                throw new Exception("405 error.");
            } else if (statusCode == 500) {
                throw new Exception("500 error.");
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder resSb = new StringBuilder();
                String tmpRes;

                while ((tmpRes = br.readLine()) != null) {
                    resSb.append(tmpRes).append("\n");
                }

                return resSb.toString();
            }
        } finally {
            conn.disconnect();
        }
    }
}
