package com.demo;

import com.demo.view.MainPanel;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class DesktopApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(DesktopApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(MainPanel mainPanel) {
        return args -> SwingUtilities.invokeLater(() -> {
            mainPanel.setVisible(true);
        });
    }
}
