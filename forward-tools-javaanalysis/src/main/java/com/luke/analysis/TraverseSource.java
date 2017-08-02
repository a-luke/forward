package com.luke.analysis;

import com.luke.utils.FileHandle;

import java.util.List;

/**
 * Created by yangf on 2017/8/2/0002.
 * 用来获取数据源中的每个字符
 */
public class TraverseSource {
    private List<String> source;
    private Integer lineIndex = null;
    private Integer charIndex = -1;
    private String lineStr = "";

    public TraverseSource(String path) {
        try {
            source = FileHandle.readToStr(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Character nextChar() {
        if (isArrayIndexOut()) {
            return null;
        }
        if (charIndex == -1 || charIndex >= lineStr.length() - 1) {
            this.lineStr = nextLine();
            this.charIndex = -1;
        }
        return this.lineStr.charAt(++charIndex);
    }

    public Character nextChar(Integer index) {
        if (isArrayIndexOut()) {
            return null;
        }
        Integer count = index + (this.charIndex == -1 ? 0 : this.charIndex);
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

    public boolean isArrayIndexOut() {
        return lineIndex != null && (lineIndex < 0 || lineIndex >= source.size() - 1);
    }

}
