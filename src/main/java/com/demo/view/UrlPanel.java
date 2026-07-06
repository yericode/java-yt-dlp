package com.demo.view;

import javax.swing.*;

public class UrlPanel {
    public JScrollPane render() {
        PlaceholderTextArea textArea = new PlaceholderTextArea("請輸入影片 URL，一行一個...");
        textArea.setRows(20);
        return new JScrollPane(textArea);
    }
}
