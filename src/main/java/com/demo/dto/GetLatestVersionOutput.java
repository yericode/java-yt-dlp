package com.demo.dto;

public class GetLatestVersionOutput extends BaseOutput {
    private String latestVersion;

    public GetLatestVersionOutput(boolean isSuccess, String rtnMsg, String latestVersion) {
        super(isSuccess, rtnMsg);
        this.latestVersion = latestVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }
}
