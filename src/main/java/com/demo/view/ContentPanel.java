package com.demo.view;

import javax.swing.*;
import java.awt.*;

public class ContentPanel {
    public JPanel renderContentPanel() {
        JLabel outputDirLabel = new JLabel("輸出路徑：");
        JTextField outputDirField = new JTextField(50);
        JButton browseButton = new JButton("選擇");
        JLabel formatLabel = new JLabel("輸出選項：");
        JCheckBox thumbnailCheckBox = new JCheckBox("保留縮圖");
        JCheckBox metadataCheckBox = new JCheckBox("保留 metadata");
        JCheckBox mp4CheckBox = new JCheckBox("輸出 MP4");
        JCheckBox listCheckBox = new JCheckBox("下載整個清單");
        JButton downloadButton = new JButton("下載");
        JButton updateButton = new JButton("更新");
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.add(downloadButton);
        buttonPanel.add(updateButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(false);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(outputDirLabel)
                                        .addComponent(formatLabel)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addComponent(
                                                                outputDirField,
                                                                0,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE
                                                        )
                                                        .addComponent(browseButton)
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

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(outputDirLabel)
                                        .addComponent(
                                                outputDirField,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(browseButton)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(formatLabel)
                                        .addComponent(checkboxPanel)
                        )
                        .addComponent(buttonPanel)
        );
        return contentPanel;
    }
}
