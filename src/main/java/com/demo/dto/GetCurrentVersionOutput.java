package com.demo.dto;

public class GetCurrentVersionOutput extends BaseOutput {
    private String currentVersion;

    public GetCurrentVersionOutput(boolean isSuccess, String rtnMsg, String currentVersion) {
        super(isSuccess, rtnMsg);
        this.currentVersion = currentVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
