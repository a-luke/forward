package com.luke.similar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/9/7/0007.
 */
public class TitleSimilar {
    /**
     * 源标题，用来和字符串匹配
     */
    private String title;

    /**
     * 关键词，用来做词语匹配
     */
    private List<String> keyWorlds;


    private static final double TITLE_DIVISOR = 1.0;
    private static final double KEY_WORLD_DIVISOR = 0.1;

    public TitleSimilar(String title, List<String> keyWorlds) {
        this.title = title;
        this.keyWorlds = keyWorlds;
    }

    /**
     *
     * @param str 源字符串
     * @param similar 相似度如：0.8
     * @return
     */
    public boolean similar(String str, double similar) {
        if(str == null || str.trim().length() == 0){
            return true;
        }

        List<Integer>  titleIndexs = findTitleInStr(str);

        int len = titleIndexs.size();
        Integer keyWorldIndex = findKeyWorld(str);
        double result = calculate(titleIndexs, keyWorldIndex);
        return result >= similar;
    }


    /**
     * 计算相似度
     * 存在字符占标题的总长度的百分比，如果能找到关键字则加0.1
     * @return
     */
    private double calculate(List<Integer> titleIndexs, Integer keyWorldIndex) {
        double sum = 0;
        double len = titleIndexs.size();
        for (int i = 0; i < len; i++) {
            Integer index = titleIndexs.get(i);
            if (index > -1) {
                sum++;
            }
        }
        double titleRatios = sum / len;
        double keyWorldRatios = 0;
        if(keyWorldIndex != -1){
            keyWorldRatios = 1 * KEY_WORLD_DIVISOR;
        }
        return titleRatios + keyWorldRatios;
    }

    /**
     * 将两个集合中的数字，按位置相减得出一个新的集合
     *
     * @param minuend    被减数
     * @param subtractor 减数
     * @return
     */
    private List<Integer> collectionSubtract(List<Integer> minuend, List<Integer> subtractor) {
        if (minuend.size() != subtractor.size() || minuend.size() == 0) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0, len = minuend.size(); i < len; i++) {
            result.add(minuend.get(i) - subtractor.get(i));
        }
        return result;
    }

    /**
     * 在字符串中找出title的每个字符的位置
     */
    private List<Integer> findTitleInStr(String str) {
        char[] titleChars = title.replaceAll("\\s*|\t|\r|\n", "").toCharArray();
        char[] strChars = str.replaceAll("\\s*|\t|\r|\n", "").toCharArray();

        //用来记录标题字符出现的位置
        List<Integer> indexList = new ArrayList<>();
        for (char titleC : titleChars) {
            //用来判断是否找到标题中的第一个字符
            boolean isExist = false;
            for (int i = 0, len = strChars.length; i < len; i++) {
                char strC = strChars[i];
                if (strC == titleC) {
                    indexList.add(i);
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                indexList.add(-1);
            }
        }
        return indexList;
    }

    private Integer findKeyWorld(String str) {
        for (String keyWorld : keyWorlds) {
            int index = str.indexOf(keyWorld);
            if (index > -1) {
                return index;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String title = "春风十里，不如你";
        List<String> keyWorld = new ArrayList(){{
            add("父子雄兵");
            add("视频");
        }};
        TitleSimilar titleSimilar = new TitleSimilar(title, keyWorld);
        String str = "春F十里，B如你撒发射点发啊撒发射点发射点。mkv";

        boolean result = titleSimilar.similar(str, 0.7);

        System.out.println(result);
    }

}
