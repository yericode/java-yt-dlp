package com.demo.view;

import com.demo.dto.GetCurrentVersionOutput;
import com.demo.dto.GetLatestVersionOutput;
import com.demo.service.HttpService;
import com.demo.service.YtDlpClient;
import com.demo.vo.CheckboxOptions;
import com.demo.vo.State;
import com.demo.vo.VersionInfo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainPanel extends JFrame {
    private State state;
    private final UrlPanel urlPanel;
    private final ContentPanel contentPanel;
    private final BottomPanel bottomPanel;
    private final HttpService httpService;
    private final YtDlpClient ytDlpClient;

    public MainPanel(HttpService httpService, YtDlpClient ytDlpClient) throws HeadlessException {
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
        this.urlPanel = new UrlPanel();
        this.contentPanel = new ContentPanel();
        this.bottomPanel = new BottomPanel(new VersionInfo("-", "-"));
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
                return new VersionInfo(latestOutput.getLatestVersion(), currentOutput.getCurrentVersion());
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
                    e.printStackTrace();
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
        System.out.println(state);
    }

    private void handleOutputDirChanged(String dir) {
        setState(state.urlList(), dir, state.options());
        System.out.println(state);
    }

    private void handleCheckBoxOptionsChanged(CheckboxOptions options) {
        setState(state.urlList(), state.outputDir(), options);
        System.out.println(state);
    }

    private void handleBrowseClicked() {
        System.out.println("browse");
    }

    private void handleDownloadClicked() {
        System.out.println("download");
    }

    private void handleUpdateClicked() {
        System.out.println("update");
    }

    private void setState(List<String> urlList, String outputDir, CheckboxOptions options) {
        this.state = new State(urlList, outputDir, options);
        System.out.println(state);
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
