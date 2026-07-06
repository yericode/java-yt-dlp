package com.demo.dto;

public record Version(String old, String current) {
    @Override
    public String toString() {
        return old + "/" + current;
    }
}
