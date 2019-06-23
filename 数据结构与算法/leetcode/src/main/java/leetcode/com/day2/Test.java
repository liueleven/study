package leetcode.com.day2;

import java.util.*;

public class Test {


    public static void main(String[] args) {
        getNotRepeatStringLength();
    }

    /**
     * 给定一个字符串，找出不含有重复字符的最长子串的长度。
     * 例如：
     * abcabcbb  = 3 abc
     * bbbbb  = 1 b
     * pwwkew = 3 wke
     * */
    public static void getNotRepeatStringLength() {
        String str = "pwwkew";
        String[] strings = str.split("");
        String strLength = "";
        Map<Integer,String> record = new HashMap<Integer, String>();
        for(String s : strings) {
            int i = strLength.indexOf(s);
            if(i != -1){
                strLength = strLength.substring(i+1, strLength.length());
            }
            strLength = strLength + s;
            record.put(strLength.length(),strLength);
        }
        Set<Map.Entry<Integer,String>> entries = record.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
        int max = 1;
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            if(max < next.getKey()) {
                max  = next.getKey();
            }
        }
        System.out.println("最大长度为：" + max +" 值为："+record.get(max));
    }
}
