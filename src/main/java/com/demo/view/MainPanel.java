package com.demo.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainPanel extends JFrame {

    public MainPanel() throws HeadlessException {
        setTitle("YT 下載器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(renderMainPanel());
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel renderMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane urlPanel = new UrlPanel().render();
        JPanel contentPanel = new ContentPanel().render();
        JPanel bottomPanel = new BottomPanel().render();

        // 中間主要區域：textarea 會吃掉主要可伸縮空間
        mainPanel.add(urlPanel, BorderLayout.CENTER);

        // 下方區 域：上面是設定表單，下面是進度 / 版本資訊
        JPanel southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.add(contentPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        return mainPanel;
    }
}
