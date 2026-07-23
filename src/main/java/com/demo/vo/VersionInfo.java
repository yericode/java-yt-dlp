package com.demo.vo;

public record VersionInfo(
        String currentVersion,
        String latestVersion
) {
    public String displayText() {
        return "[" + currentVersion + " / " + latestVersion + "]";
    }
}
