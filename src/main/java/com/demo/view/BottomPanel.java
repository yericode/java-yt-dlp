package com.demo.view;

import com.demo.dto.Version;

import javax.swing.*;
import java.awt.*;


public class BottomPanel {
    public JPanel render() {
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        panel.setPreferredSize(new Dimension(600, 36));
        panel.setMinimumSize(new Dimension(0, 36));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        panel.add(renderProgressBar(layout, panel));
        panel.add(renderVersionInfo(layout, panel, new Version("2026.06.17", "2026.07.06")));
        return panel;
    }

    private JProgressBar renderProgressBar(SpringLayout layout, JPanel panel) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(45);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(240, 20));
        // progressBar：永遠以 bottomPanel 整體為基準置中
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER,
                progressBar,
                0,
                SpringLayout.HORIZONTAL_CENTER,
                panel
        );
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER,
                progressBar,
                0,
                SpringLayout.VERTICAL_CENTER,
                panel
        );
        return progressBar;
    }

    private JLabel renderVersionInfo(SpringLayout layout, JPanel panel, Version version) {
        JLabel versionInfo = new JLabel(String.format("[%s]", version));
        // versionLabel：永遠貼右，右邊保留 8px
        layout.putConstraint(
                SpringLayout.EAST,
                versionInfo,
                -8,
                SpringLayout.EAST,
                panel
        );
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER,
                versionInfo,
                0,
                SpringLayout.VERTICAL_CENTER,
                panel
        );
        return versionInfo;
    }
}
