package com.luke.analysis.traverse.impl;

import com.luke.analysis.traverse.TraverseSource;

import java.util.List;

public class TraverseFileLineStr implements TraverseSource {
    private List<String> source;
    private Integer lineIndex = null;
    private Integer charIndex = -1;
    private String lineStr = "";

    public TraverseFileLineStr(List<String> source) {
        this.source = source;
    }

    @Override
    public Character next() {
        if (isArrayIndexOut()) {
            return null;
        }
        if (charIndex == -1 || charIndex >= lineStr.length() - 1) {
            this.lineStr = nextLine();
            this.charIndex = -1;
        }
        return this.lineStr.charAt(++charIndex);
    }

    @Override
    public Character next(Integer index) {
        if (isArrayIndexOut()) {
            return null;
        }
        Integer count = index + (this.charIndex == -1 ? (index == 0 ? 0 : -1) : this.charIndex);
        String line = nextLine(0);
        Integer lineStep = 0;
        while (count < 0 || count >= line.length()) {
            if (count < 0) {
                line = nextLine(--lineStep);
                count = count + line.length();
            } else if (count >= line.length()) {
                count = count - line.length();
                line = nextLine(++lineStep);
            }
        }
        return line.charAt(count);
    }

    private String nextLine() {
        return source.get(lineIndex == null ? lineIndex = 0 : ++lineIndex).trim() + "\n";
    }

    private String nextLine(Integer index) {
        return source.get((this.lineIndex == null ? 0 : this.lineIndex) + index).trim() + "\n";
    }

    @Override
    public boolean isArrayIndexOut() {
        return lineIndex != null && (lineIndex < 0 || lineIndex >= source.size() - 1);
    }

    @Override
    public Integer getIndex() {
        return null;
    }
}
