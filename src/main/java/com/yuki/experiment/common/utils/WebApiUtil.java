package com.yuki.experiment.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author nzh
 */
public class WebApiUtil {
    private final RestTemplate restTemplate=new RestTemplate();

    @Test
    public void test() throws IOException {
        String keywords="伟大的渺小";
        JSONWriter writer=new JSONWriter(new FileWriter("src/main/resources/test.json"));
        JSONObject forObject = restTemplate.getForObject("http://music.eleuu.com/search?keywords= 周杰伦", JSONObject.class);
        JSONArray jsonArray = forObject.getJSONObject("result").getJSONArray("songs");

        for(int i=0;i<jsonArray.size();i++){
            Integer id = jsonArray.getJSONObject(i).getInteger("id");
            String url="https://api.xsot.cn/netease/?id=" + id;
            String a=restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = JSONObject.parseObject(a);
            if(jsonObject.getInteger("code")!=404) {
                writer.writeObject(jsonObject);
            }
        }
    }

}
