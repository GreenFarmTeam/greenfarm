package com.nchu.ruanko.greenfarm.util.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.Map;

public final class CertificationUtils {

    private static final String HOST = "https://naidcard.market.alicloudapi.com";
    private static final String PATH = "/nidCard";
    private static final String METHOD = "GET";
    private static final String APP_CODE = "b8334e2c6bfe4673afdfba5a9308b909";
    private static final String SUCCESS_FLAG = "01";

    private static Map<String, String> headers;

    static {
        headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + APP_CODE);
    }

    public static boolean certification(String name, String idcard) throws Exception {
        Map<String, String> querys = new HashMap<>();
        querys.put("idCard", idcard);
        querys.put("name", name);
        HttpResponse response = AliyunHttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
        String flag = JSON.parseObject(EntityUtils.toString(response.getEntity())).getString("status");
        System.out.println(flag);
        return SUCCESS_FLAG.equals(flag);
    }

}
