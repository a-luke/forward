package com.luke.read;

import com.luke.util.FileHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadAllParser {

    private static final String PARSE_PATH = "D:\\workspaces\\VideoCrawlProject-project\\src\\com\\firstbrave\\crawler\\parser";

    public Map<String, String> read() {
        List<String> paths = new ArrayList<>();
        FileHandle.traversePath(paths, PARSE_PATH);
        Map<String, String> result = new HashMap();
        for (String path : paths) {
            File file = new File(path);
            String filePath = file.getAbsolutePath();
            String pack = filePath.substring(filePath.indexOf("com" + File.separator)).replace(File.separator, ".").replace(".java", "") + ";";
            result.put(file.getName().replace(".java",""), pack);
        }
        return result;
    }

    public static void main(String[] args) {
        new ReadAllParser().read();
    }
}
