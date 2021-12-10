package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.yuki.experiment.common.exception.MyException;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class JsonUtil {

    public static JSONObject readJson(String path) {
        if (path.endsWith(".json")) {
            try {
                JSONReader reader = new JSONReader(new FileReader(path));
                return reader.readObject(JSONObject.class);
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean writeJson(String path,JSONObject object) {
        try {
            JSONWriter writer = new JSONWriter(new FileWriter(path));
            writer.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void readFile() throws IOException {
        long num=0;
        Set<String>set=new HashSet<>();
        BufferedReader reader=new BufferedReader(new FileReader("src/main/resources/Movies_and_TV_5.json"));
        BufferedWriter writer=new BufferedWriter(new FileWriter("src/main/resources/a.txt"));
        String temp;
        while((temp=reader.readLine())!=null){
            String asin = JSONObject.parseObject(temp).getString("asin");
            num++;
            set.add(asin);
        }
        System.out.println("total:"+num);
        System.out.println("size:"+set.size());
        for(String item:set){
            writer.write(item);
            writer.newLine();
        }
        writer.close();
        reader.close();
    }

    @Test
    public void test(){
        String temp="https://www.amazon.com/dp/B00006IUJ1";
        System.out.println(temp.substring(temp.indexOf("dp/")+3));
    }

}
