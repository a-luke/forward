package com.luke.traverse.impl;

import com.luke.traverse.TraverseSource;

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
        if (charIndex >= lineStr.length() - 1) {
            this.lineStr = nextLine();
            if(lineStr == null){
                return null;
            }
            this.charIndex = -1;
        }
        return this.lineStr.charAt(++charIndex);
    }

    @Override
    public Character next(Integer index) {
        if (lineIndex < 0 || lineIndex >= source.size()) {
            return null;
        }

        Integer count = index + (this.charIndex == -1 ? 0 : this.charIndex);
        String line = nextLine(0);
        Integer lineStep = 0;

        while (count < 0 || count >= line.length()) {
            if (count < 0) {
                line = nextLine(--lineStep);
                if (line == null) {
                    return null;
                }
                count = count + line.length();
            } else if (count >= line.length()) {
                count = count - line.length();
                line = nextLine(++lineStep);
                if (line == null) {
                    return null;
                }
            }
        }
        return line.charAt(count);
    }

    private String nextLine() {
        int ind = lineIndex == null ? lineIndex = 0 : ++lineIndex;
        if(ind >= source.size()){
            return null;
        }
        return source.get(ind).trim() + "\n";
    }

    private String nextLine(Integer index) {
        int ind = (this.lineIndex == null ? 0 : this.lineIndex) + index;
        if (ind < 0 || ind >= source.size()) {
            return null;
        }
        return source.get(ind).trim() + "\n";
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
