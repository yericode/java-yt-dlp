package com.demo.service;

import com.demo.dto.GetLatestVersionOutput;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HttpService {

    @Value("${api.getVersionInfo}")
    private String getVersionInfoUrl;

    private final HttpClient httpClient;
    private final Gson gson;

    public HttpService(HttpClient httpClient, Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public GetLatestVersionOutput getLatestVersion() {
        boolean isSuccess = true;
        String rtnMsg = "";
        String latestVersion = "-";
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getVersionInfoUrl)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject body = gson.fromJson(response.body(), JsonObject.class);
                latestVersion = body.get("tag_name").getAsString();
            } else {
                isSuccess = false;
                rtnMsg = "Call API getVersionInfo 狀態異常: " + response.statusCode();
                System.err.println(rtnMsg);
            }
        } catch (IOException e) {
            isSuccess = false;
            rtnMsg = "Call API 傳輸資料發生異常";
            System.err.println(rtnMsg + e);
        } catch (InterruptedException e) {
            isSuccess = false;
            rtnMsg = "Call API 請求中斷";
            System.err.println(rtnMsg + e);
            Thread.currentThread().interrupt(); // 恢復 interrupt flag
        } catch (Exception e) {
            isSuccess = false;
            rtnMsg = "未預期錯誤";
            System.err.println(rtnMsg + e);
        }
        return new GetLatestVersionOutput(isSuccess, rtnMsg, latestVersion);
    }
}
