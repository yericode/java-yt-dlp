package com.demo.service;

import com.demo.dto.GetCurrentVersionOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class YtDlpClient {
    private static final Logger log = LoggerFactory.getLogger(YtDlpClient.class);

    @Value("${path.yt-dlp}")
    private String ytDlpPath;

    public GetCurrentVersionOutput getCurrentVersion() {
        boolean isSuccess = true;
        String rtnMsg = "";
        String currentVersion = "-";
        try {
            List<String> command = List.of(ytDlpPath, "--version");
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                currentVersion = output.toString();
            } else {
                isSuccess = false;
                rtnMsg = "yt-dlp 執行失敗，Exit Code: " + exitCode;
                log.error(rtnMsg);
            }
        } catch (IOException e) {
            isSuccess = false;
            rtnMsg = "yt-dlp.exe 路徑異常";
            log.error(rtnMsg, e);
        } catch (InterruptedException e) {
            isSuccess = false;
            rtnMsg = "yt-dlp.exe 命令執行中斷";
            log.error(rtnMsg, e);
            Thread.currentThread().interrupt(); // 恢復 interrupt flag
        } catch (Exception e) {
            isSuccess = false;
            rtnMsg = "未預期錯誤";
            log.error(rtnMsg, e);
        }
        return new GetCurrentVersionOutput(isSuccess, rtnMsg, currentVersion);
    }
}
