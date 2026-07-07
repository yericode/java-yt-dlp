package com.demo.view;

import com.demo.dto.GetCurrentVersionOutput;
import com.demo.dto.GetLatestVersionOutput;
import com.demo.service.HttpService;
import com.demo.service.YtDlpClient;
import com.demo.vo.CheckboxOptions;
import com.demo.vo.State;
import com.demo.vo.VersionInfo;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainPanel extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

    private State state;
    private final UrlPanel urlPanel;
    private final ContentPanel contentPanel;
    private final BottomPanel bottomPanel;
    private final HttpService httpService;
    private final YtDlpClient ytDlpClient;

    public MainPanel(
            HttpService httpService,
            YtDlpClient ytDlpClient,
            UrlPanel urlPanel,
            ContentPanel contentPanel,
            BottomPanel bottomPanel
    ) throws HeadlessException {
        state = new State(
                new ArrayList<>(),
                Strings.EMPTY,
                new CheckboxOptions(
                        false,
                        false,
                        false,
                        false
                )
        );
        this.urlPanel = urlPanel;
        this.contentPanel = contentPanel;
        this.bottomPanel = bottomPanel;
        this.httpService = httpService;
        this.ytDlpClient = ytDlpClient;

        setTitle("YT 下載器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel());
        pack();
        setLocationRelativeTo(null);
        this.init();
    }

    private void bindEventListener() {
        contentPanel.setEventListener(new ContentPanel.Listener() {
            @Override
            public void onOutputDirChanged(String outputDir) {
                handleOutputDirChanged(outputDir);
            }

            @Override
            public void onOptionChanged(CheckboxOptions options) {
                handleCheckBoxOptionsChanged(options);
            }

            @Override
            public void onBrowseClicked() {
                handleBrowseClicked();
            }

            @Override
            public void onDownloadClicked() {
                handleDownloadClicked();
            }

            @Override
            public void onUpdateClicked() {
                handleUpdateClicked();
            }
        });
        urlPanel.setEventListener(this::handleUrlListChanged);
    }

    private void init() {
        this.bottomPanel.setVersionInfo(new VersionInfo("-", "-"));
        this.bindEventListener();
        this.loadVersionInfoAsync();
    }

    private void loadVersionInfoAsync() {
        bottomPanel.showProgressBar(0);

        SwingWorker<VersionInfo, Void> worker = new SwingWorker<>() {
            @Override
            protected VersionInfo doInBackground() {
                GetLatestVersionOutput latestOutput = httpService.getLatestVersion();
                GetCurrentVersionOutput currentOutput = ytDlpClient.getCurrentVersion();
                return new VersionInfo(currentOutput.getCurrentVersion(), latestOutput.getLatestVersion());
            }

            @Override
            protected void done() {
                try {
                    VersionInfo versionInfo = get();
                    if (versionInfo != null) {
                        bottomPanel.setVersionInfo(versionInfo);
                    }
                    bottomPanel.setProgress(100);
                    hideProgressBarAfter(100);
                } catch (Exception e) {
                    log.error("非同步取得版本資訊發生異常: ", e);
                    bottomPanel.hideProgressBar();
                }
            }
        };
        worker.execute();
    }

    private void hideProgressBarAfter(int delayMillis) {
        Timer timer = new Timer(delayMillis, event -> bottomPanel.hideProgressBar());
        timer.setRepeats(false);
        timer.start();
    }

    private void handleUrlListChanged(List<String> urlList) {
        setState(urlList, state.outputDir(), state.options());
        log.info(String.valueOf(state));
    }

    private void handleOutputDirChanged(String dir) {
        setState(state.urlList(), dir, state.options());
        log.info(String.valueOf(state));
    }

    private void handleCheckBoxOptionsChanged(CheckboxOptions options) {
        setState(state.urlList(), state.outputDir(), options);
        log.info(String.valueOf(state));
    }

    private void handleBrowseClicked() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle("選擇輸出資料夾");

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = chooser.getSelectedFile();

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "沒有選擇任何資料夾",
                    "選擇錯誤",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!selectedFile.isDirectory()) {
            JOptionPane.showMessageDialog(
                    this,
                    "請選擇資料夾，不要選擇檔案",
                    "選擇錯誤",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        contentPanel.setOutputDir(selectedFile.getAbsolutePath());
    }

    private void handleDownloadClicked() {
        log.info("download");
    }

    private void handleUpdateClicked() {
        log.info("update");
    }

    private void setState(List<String> urlList, String outputDir, CheckboxOptions options) {
        this.state = new State(urlList, outputDir, options);
        log.info(String.valueOf(state));
    }

    private JPanel mainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

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
