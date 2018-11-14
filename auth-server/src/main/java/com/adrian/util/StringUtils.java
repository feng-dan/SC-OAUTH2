package com.adrian.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author asus
 * @ClassName StringUtils
 * @description TODO
 * @Date 2018/10/26 16:11
 * @Version 1.0v
 **/
public class StringUtils {

    public static String getPinYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {  //判断字符是否是中文
                    //toHanyuPinyinStringArray 如果传入的不是汉字，就不能转换成拼音，那么直接返回null
                    //由于中文有很多是多音字，所以这些字会有多个String，在这里我们默认的选择第一个作为pinyin
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else {
                    output += Character.toString(input[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            //    		Log.v(TAG, "BadHanyuPinyinOutputFormatCombination");
        }
        return output;
    }


    /**
     * 取出拼音中第一个字母,一般第一个字母的使用时比较常见的
     *
     * @param chines
     * @return
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaulFormat = new HanyuPinyinOutputFormat();
        defaulFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaulFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaulFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaulFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination ex) {
                    ex.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    public static String swapCase(String str) {
        if (org.apache.commons.lang.StringUtils.isEmpty(str)) {
            return str;
        }
        char[] buffer = str.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            char ch = buffer[i];

            if (Character.isUpperCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                buffer[i] = Character.toUpperCase(ch);
            }
        }
        return new String(buffer);
    }


}
