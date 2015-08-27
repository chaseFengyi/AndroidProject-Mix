package com.mix.expressage;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class Pinyin {
    
    /**
    * ºº×Ö×ªÆ´ÒôµÄ·½·¨
    * @param name ºº×Ö
    * @return Æ´Òô
    */
    public static String HanyuToPinyin(String name){
    	StringBuffer pinyinName = new StringBuffer(); 
        char[] nameChar = name.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = 
                        new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray
                                           (nameChar[i], defaultFormat)[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
            	pinyinName.append(nameChar[i]);
            }
        }
        return pinyinName.toString();
    }
}
