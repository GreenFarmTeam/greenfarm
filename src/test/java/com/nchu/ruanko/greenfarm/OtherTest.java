package com.nchu.ruanko.greenfarm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.util.http.AliyunHttpUtils;
import com.nchu.ruanko.greenfarm.util.http.CertificationUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OtherTest {

    @Test
    public void demo1() throws Exception {
//        String mail = "2161624113@vip.qq.com";
//        String mail = "y2161624113@163.com";
//        System.out.println(mail.substring(mail.lastIndexOf("@") + 1, mail.lastIndexOf(".")).replace(".", ""));
//        System.out.println(mail.substring(0, mail.lastIndexOf("@")));
//        String data = ShortMessageUtils.sendVerificationCodeMessage("SMS_174020806", "18548327651", StringUtils.createVerificationCode(5));
//        System.out.println(data);
//        System.out.println(StringUtils.desensitizeIdCard("371322199802030210"));
//        boolean flag = CertificationUtils.certification("袁悦", "371322199802030210");
//        System.out.println(flag);
//        String str = "2161624113@qq.com";
//        System.out.println(str.substring(str.lastIndexOf("@")));
//        for (int i = 0; i <= 100; i++) {
//            int result = (int)((31 + 1) * Math.random() + 0);
//            System.out.println(result);
//        }
//        System.out.println(StringUtils.encodeMd5("16205134"));
//        System.out.println(StringUtils.encodeBase64("110@qq.com"));
         System.out.println(StringUtils.createUUID());
        // System.out.println(StringUtils.encodeMd5("16201535"));
//        System.out.println(StringUtils.encodeBase64("15797897170"));
//        String s = "/file/upload/06d8232ebb9546069dcd497fca838a4c.jpg".substring("/file/upload/06d8232ebb9546069dcd497fca838a4c.jpg".lastIndexOf("/") + 1);
//        System.out.println(s);

    }

    @Test
    public void demo2() throws Exception {
        String host = "https://jisuxzqhf.market.alicloudapi.com";
        String path = "/area/city";
        String method = "GET";
        String appcode = "b8334e2c6bfe4673afdfba5a9308b909";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("parentid", "1");

        HttpResponse response = AliyunHttpUtils.doGet(host, path, method, headers, querys);
        String r = EntityUtils.toString(response.getEntity());
        System.out.println(JSON.parseObject(r).get("status"));
        System.out.println(JSON.parseObject(r).get("result"));
//        System.out.println(JSON.parseObject(r).get("result"));
//        JSONArray jsonArray = JSON.parseArray(JSON.parseObject(r).getString("result"));
//
//        for (Object obj : jsonArray) {
//            JSONObject json = JSON.parseObject(obj.toString());
//            System.out.println(json.getString("id") + "："  + json.getString("name"));
//        }


    }


}
