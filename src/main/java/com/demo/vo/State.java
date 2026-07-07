package com.demo.vo;

import java.util.List;

public record State(
        List<String> urlList,
        String outputDir,
        CheckboxOptions options
) {
}
