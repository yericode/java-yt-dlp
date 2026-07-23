package com.demo.view;

import com.demo.vo.VersionInfo;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class BottomPanel extends JPanel {
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel versionLabel = new JLabel("");
    private VersionInfo versionInfo = new VersionInfo("-", "-");

    public BottomPanel() {
        initLayout();
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        versionLabel.setText(versionInfo.displayText());
    }

    public void showProgressBar(int value) {
        progressBar.setValue(value);
        progressBar.setVisible(true);
        revalidate();
        repaint();
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public void hideProgressBar() {
        progressBar.setVisible(false);
        revalidate();
        repaint();
    }

    private void initLayout() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        setPreferredSize(new Dimension(600, 36));
        setMinimumSize(new Dimension(0, 36));
        setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        add(progressBar(layout, this));
        add(versionLabel(layout, this, versionInfo));
    }

    private JProgressBar progressBar(SpringLayout layout, JPanel panel) {
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
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

    private JLabel versionLabel(SpringLayout layout, JPanel panel, VersionInfo versionInfo) {
        if (versionInfo != null) {
            versionLabel.setText(versionInfo.displayText());
        }
        // versionLabel：永遠貼右，右邊保留 8px
        layout.putConstraint(
                SpringLayout.EAST,
                versionLabel,
                -8,
                SpringLayout.EAST,
                panel
        );
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER,
                versionLabel,
                0,
                SpringLayout.VERTICAL_CENTER,
                panel
        );
        return versionLabel;
    }
}
