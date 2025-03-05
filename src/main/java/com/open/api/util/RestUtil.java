package com.open.api.util;

import com.open.api.domain.dto.OpenApiDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class RestUtil {

    public static String getHttpsRestApi(OpenApiDto openApiDto) throws MalformedURLException, IOException, Exception {
        String api = openApiDto.getApi();
        log.info("apiUrl: {}", api);

        if(StringUtils.isEmpty(api)) {
            throw new Exception("WebUtil getHttpsRestApi: apiUrl is empty or null.");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(api).append("?");

        Map<String, String> params = openApiDto.getParams();
        Iterator<String> paramItr = params.keySet().iterator();

        while(paramItr.hasNext()) {	// parameter 추가
            String paramKey = paramItr.next();
            String paramValue = params.get(paramKey).toString();

            log.debug("{0}: {1}", paramKey, paramValue);

            sb.append(paramKey).append("=").append(paramValue).append("&");
        }

        String urlAddr = sb.deleteCharAt(sb.length()-1).toString();

        URL url = new URL(urlAddr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");	//method 설정

        Map<String, String> headers = openApiDto.getHeaders();	//header 추가

        headers.put("charset", "UTF-8");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("Accept", "application/json");

        Iterator<String> headerItr = headers.keySet().iterator();

        while(headerItr.hasNext()) {
            String haaderKey = headerItr.next();
            String headerValue = headers.get(haaderKey).toString();

            conn.addRequestProperty(haaderKey, headerValue);
        }

        conn.connect();	//rest api 접속
        int statusCode = conn.getResponseCode();

        if(statusCode == 403){
            throw new Exception("403 error.");
        } else if(statusCode == 404) {
            throw new Exception("404 error.");
        } else if(statusCode == 405) {
            throw new Exception("405 error.");
        } else if(statusCode == 500) {
            throw new Exception("500 error.");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuffer resSb = new StringBuffer();
        String tmpRes = null;

        while((tmpRes = br.readLine()) != null) {
            resSb.append(tmpRes+"\n");
        }

        conn.disconnect();
        br.close();

        return resSb.toString();
    }
}
