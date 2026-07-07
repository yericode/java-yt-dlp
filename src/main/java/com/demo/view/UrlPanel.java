package com.demo.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Arrays;
import java.util.List;

@Component
public class UrlPanel extends JScrollPane {
    public interface Listener {
        void onUrlListChanged(List<String> urlList);
    }

    private final PlaceholderTextArea textArea = new PlaceholderTextArea("請輸入影片 URL，一行一個...");
    private Listener listener;

    public UrlPanel() {
        initLayout();
        initAction();
    }

    public void setEventListener(UrlPanel.Listener listener) {
        this.listener = listener;
    }

    private void initAction() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("URL insert");
                listener.onUrlListChanged(Arrays.stream(textArea.getText().split("\n")).toList());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("URL remove");
                listener.onUrlListChanged(Arrays.stream(textArea.getText().split("\n")).toList());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println("URL change");
                listener.onUrlListChanged(Arrays.stream(textArea.getText().split("\n")).toList());
            }
        });
    }

    private void initLayout() {
        textArea.setRows(20);
        setViewportView(textArea);
    }
}
