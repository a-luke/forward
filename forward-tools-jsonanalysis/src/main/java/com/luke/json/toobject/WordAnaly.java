package com.luke.json.toobject;

import com.luke.json.model.SymbolType;
import com.luke.json.model.Word;
import com.luke.json.model.WordType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/3/31.
 */
public class WordAnaly {

    private int index = -1;

    private String str;

    public WordAnaly(String str) {
        this.str = str;
    }

    public List getWord() {
        List<Word> list = new ArrayList();
        char c = '0';
        while (index < str.length() - 1) {
            if (!isSymbol(c = getNext())) {
                if ('"' == getNext(-1)) {
                    list.add(new Word(WordType.STR, strWord()));
                } else if (':' == getNext(-1) || ',' == getNext(-1) || '[' == getNext(-1)) {
                    String numStr = numberWord();
                    if (numStr.length() > 0) {
                        list.add(new Word(WordType.NUM, numStr));
                    }
                } else if (!isESC(c)) {
                    throw new RuntimeException("'" + str.substring(index) + "' 位置格式不正确！");
                }
            } else {
                list.add(new Word(WordType.getType(c), String.valueOf(c)));
            }
        }
        return list;
    }

    private String strWord() {
        index--;
        StringBuilder sb = new StringBuilder();
        while ((getNext(1)) != '"' && getNext(-1) != '\\') {
            char c = getNext();
            sb.append(c);
        }
        return sb.toString();
    }

    private String numberWord() {
        index--;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        boolean flg = false;
        while (!isSymbol(getNext(1)) && getNext(-1) != '\\') {
            char c = getNext();
            if (isESC(c)) {
                continue;
            }
            if ((c < '0' || c > '9') && c != '.') {
                flg = true;
            } else if (c == '.') {
                if (sb.length() == 0)
                    flg = true;
                count++;
            }
            sb.append(c);
        }
        if (count > 1 || flg) {
            throw new RuntimeException("value is error : " + sb.toString());
        }
        return sb.toString();

    }


    private static boolean isSymbol(char c) {
        for (SymbolType type : SymbolType.values()) {
            if (type.equal(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isESC(char c) {
        return c == ' ';
    }

    private char getNext(int i) {
        return str.charAt(index + i);
    }

    private char getNext() {
        return str.charAt(++index);
    }
}
