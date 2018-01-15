package com.luke.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MutilDown {

    public static void main(String[] args) throws Exception {
        File source = new File("E:\\testData\\movie\\qq.mp4");

        String result = "";
        try(RandomAccessFile rf = new RandomAccessFile(source, "rw");) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            long fileLen = source.length();

            for (int i = 0; i <= fileLen; i = (int) (i + (fileLen/3) -1023)) {
                rf.seek(i);
                length = rf.read(buffer, 0, 1024);
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            result = bigInt.toString(16);
        }
        System.out.println(result);
    }

}