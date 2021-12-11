package com.yuki.experiment.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SimilarityUtil {

    public static double similarScoreCos(String text1, String text2) {
        if (text1 == null || text2 == null) {
            //只要有一个文本为null，规定相似度分值为0，表示完全不相等
            return 0.0;
        } else if ("".equals(text1) && "".equals(text2)) {
            return 1.0;
        }

        Set<Integer> ASCII = new TreeSet<>();
        Map<Integer, Integer> text1Map = new HashMap<>();
        Map<Integer, Integer> text2Map = new HashMap<>();
        for (int i = 0; i < text1.length(); i++) {
            Integer temp1 = (int) text1.charAt(i);
            text1Map.merge(temp1, 1, Integer::sum);
            ASCII.add(temp1);
        }
        for (int j = 0; j < text2.length(); j++) {
            Integer temp2 = (int) text2.charAt(j);
            text2Map.merge(temp2, 1, Integer::sum);
            ASCII.add(temp2);
        }
        double xy = 0.0;
        double x = 0.0;
        double y = 0.0;
        //计算
        for (Integer it : ASCII) {
            int t1 = text1Map.get(it) == null ? 0 : text1Map.get(it);
            int t2 = text2Map.get(it) == null ? 0 : text2Map.get(it);
            xy += t1 * t2;
            x += Math.pow(t1, 2);
            y += Math.pow(t2, 2);
        }
        if (x == 0.0 || y == 0.0) return 0.0;
        return xy / Math.sqrt(x * y);
    }
}
