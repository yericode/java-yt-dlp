package com.demo.view;

import com.demo.vo.CheckboxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ContentPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(ContentPanel.class);

    public interface Listener {
        void onOutputDirChanged(String outputDir);
        void onOptionChanged(CheckboxOptions options);
        void onBrowseClicked();
        void onDownloadClicked();
        void onUpdateClicked();
    }

    // label
    private final JLabel outputDirLabel = new JLabel("輸出路徑：");
    private final JLabel formatLabel = new JLabel("輸出選項：");
    // output dir
    private final JTextField outputDirField = new JTextField(50);
    private final JButton browseButton = new JButton("選擇");
    // checkbox
    private final JCheckBox thumbnailCheckBox = new JCheckBox("保留縮圖");
    private final JCheckBox metadataCheckBox = new JCheckBox("保留 metadata");
    private final JCheckBox mp4CheckBox = new JCheckBox("輸出 MP4");
    private final JCheckBox listCheckBox = new JCheckBox("下載整個清單");
    // button
    private final JButton downloadButton = new JButton("下載");
    private final JButton updateButton = new JButton("更新");
    // listener
    private Listener listener;

    public ContentPanel(
            @Value("${path.default-output-dir}") String defaultOutputDir
    ) {
        initLayout(defaultOutputDir);
        initAction();
    }

    public void setEventListener(Listener listener) {
        this.listener = listener;
    }

    private void initAction() {
        outputDirField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("insert");
                listener.onOutputDirChanged(outputDirField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("remove");
                listener.onOutputDirChanged(outputDirField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("change");
                listener.onOutputDirChanged(outputDirField.getText());
            }
        });
        thumbnailCheckBox.addItemListener(e -> listener.onOptionChanged(checkboxOptions()));
        metadataCheckBox.addItemListener(e -> listener.onOptionChanged(checkboxOptions()));
        mp4CheckBox.addItemListener(e -> listener.onOptionChanged(checkboxOptions()));
        listCheckBox.addItemListener(e -> listener.onOptionChanged(checkboxOptions()));

        browseButton.addActionListener(e -> listener.onBrowseClicked());
        downloadButton.addActionListener(e -> listener.onDownloadClicked());
        updateButton.addActionListener(e -> listener.onUpdateClicked());
    }

    public void setOutputDir(String dir) {
        outputDirField.setText(dir);
    }

    private CheckboxOptions checkboxOptions() {
        return new CheckboxOptions(
                thumbnailCheckBox.isSelected(),
                metadataCheckBox.isSelected(),
                mp4CheckBox.isSelected(),
                listCheckBox.isSelected()
        );
    }

    private void initLayout(String defaultOutputDir) {
        GroupLayout rootLayout = new GroupLayout(this);
        this.setLayout(rootLayout);
        this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel outputDirPanel = outputDirPanel();
        JPanel checkboxPanel = checkboxPanel();
        JPanel buttonPanel = buttonPanel();

        rootLayout.setAutoCreateGaps(true);
        rootLayout.setAutoCreateContainerGaps(false);
        rootLayout.setHorizontalGroup(
                rootLayout.createSequentialGroup()
                        .addGroup(
                                rootLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(outputDirLabel)
                                        .addComponent(formatLabel)
                        )
                        .addGroup(
                                rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(
                                                outputDirPanel,
                                                0,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE
                                        )
                                        .addComponent(
                                                checkboxPanel,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                                buttonPanel,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                        )
        );

        rootLayout.setVerticalGroup(
                rootLayout.createSequentialGroup()
                        .addGroup(
                                rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(outputDirLabel)
                                        .addComponent(
                                                outputDirPanel,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                        )
                        .addGroup(
                                rootLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(formatLabel)
                                        .addComponent(checkboxPanel)
                        )
                        .addComponent(buttonPanel)
        );

        if (defaultOutputDir != null && !defaultOutputDir.isBlank()) {
            Path outputDir = Path.of(defaultOutputDir).toAbsolutePath().normalize();

            try {
                Files.createDirectories(outputDir);
                outputDirField.setText(outputDir.toString());
            } catch (IOException e) {
                log.error("建立預設輸出路徑發生異常", e);
            }
        }
    }

    private JPanel outputDirPanel() {
        JPanel outputDirPanel = new JPanel();
        GroupLayout outputDirLayout = new GroupLayout(outputDirPanel);

        outputDirPanel.setLayout(outputDirLayout);
        outputDirLayout.setAutoCreateGaps(true);
        outputDirLayout.setAutoCreateContainerGaps(false);
        outputDirLayout.setHorizontalGroup(
                outputDirLayout.createSequentialGroup()
                        .addComponent(
                                outputDirField,
                                0,
                                GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE
                        )
                        .addComponent(browseButton)
        );

        outputDirLayout.setVerticalGroup(
                outputDirLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(
                                outputDirField,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(browseButton)
        );
        return outputDirPanel;
    }

    private JPanel checkboxPanel() {
        JPanel checkboxPanel = new JPanel();
        GroupLayout checkboxLayout = new GroupLayout(checkboxPanel);

        checkboxPanel.setLayout(checkboxLayout);
        checkboxLayout.setAutoCreateGaps(true);
        checkboxLayout.setAutoCreateContainerGaps(false);
        checkboxLayout.setHorizontalGroup(
                checkboxLayout.createSequentialGroup()
                        .addGroup(
                                checkboxLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(thumbnailCheckBox)
                                        .addComponent(mp4CheckBox)
                        )
                        .addGroup(
                                checkboxLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(metadataCheckBox)
                                        .addComponent(listCheckBox)
                        )
        );

        checkboxLayout.setVerticalGroup(
                checkboxLayout.createSequentialGroup()
                        .addGroup(
                                checkboxLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(thumbnailCheckBox)
                                        .addComponent(metadataCheckBox)
                        )
                        .addGroup(
                                checkboxLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(mp4CheckBox)
                                        .addComponent(listCheckBox)
                        )
        );
        return checkboxPanel;
    }

    private JPanel buttonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.add(downloadButton);
        buttonPanel.add(updateButton);
        return buttonPanel;
    }
}
