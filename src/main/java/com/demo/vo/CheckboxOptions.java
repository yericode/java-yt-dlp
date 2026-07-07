package com.demo.vo;

public record CheckboxOptions(
        boolean keepThumbnail,
        boolean keepMetadata,
        boolean outputMp4,
        boolean downloadList
) {
}
