package com.luke.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by yangf on 2018/1/15/0015.
 */
public class FileUtils {

    /**
     * 采样生成的MD5值
     *
     * @param source
     * @return
     */
    public static String md5(File source) throws Exception {
        String result = "";
        try (RandomAccessFile rf = new RandomAccessFile(source, "rw")) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            long fileLen = source.length();
            int step = (int) ((fileLen / 5) - 1040);

            for (int i = 0; i <= fileLen - 1040; i += step) {
                rf.seek(i);
                length = rf.read(buffer, 0, 1024);
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            result = bigInt.toString(16);
        }
        return result;
    }
}
