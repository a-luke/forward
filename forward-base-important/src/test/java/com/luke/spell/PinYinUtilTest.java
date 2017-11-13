package com.luke.spell;

import org.junit.Test;

/**
 * Created by yangf on 2017/11/13/0013.
 */
public class PinYinUtilTest {

    @Test
    public void getPingYin() throws Exception {
        String chinese = "获取汉字串拼音首字母，英文字符不变";
        String spell =  PinYinUtil.getPingYin(chinese);
        System.out.println(spell);
    }

    @Test
    public void getFirstSpell() throws Exception {
        String chinese = "获取汉字串拼音首字母英文字符不变";
        String spell =  PinYinUtil.getFirstSpell(chinese);
        System.out.println(spell);
    }

    @Test
    public void getFullSpell() throws Exception {
        String chinese = "获取汉字串拼音首字母英文字符不变";
        String spell =  PinYinUtil.getFullSpell(chinese);
        System.out.println(spell);
    }

}