package com.example.pidev.entity.activities;

import java.util.List;

public class Match {
    private String message;
    private int offset;
    private int length;
    private List<Replacement> replacements;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Replacement> getReplacements() {
        return replacements;
    }

    public void setReplacements(List<Replacement> replacements) {
        this.replacements = replacements;
    }

    @Override
    public String toString() {
        return "Match{" +
                "message='" + message + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", replacements=" + replacements +
                '}';
    }
}