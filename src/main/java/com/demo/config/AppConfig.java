package com.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class AppConfig {

    @Value("${path.yt-dlp}")
    private String ytDlpPath;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(3, ChronoUnit.SECONDS))
                .build();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
