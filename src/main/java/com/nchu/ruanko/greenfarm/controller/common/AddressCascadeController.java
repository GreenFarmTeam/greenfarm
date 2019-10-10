package com.nchu.ruanko.greenfarm.controller.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nchu.ruanko.greenfarm.util.http.AliyunHttpUtils;
import io.swagger.annotations.Api;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * “省市县地址联动”功能控制器
 */
@Api(tags = "common.AddressCascadeController", description = "“省市县地址联动”功能控制器")
@Controller
public class AddressCascadeController {

    private static final String HOST = "https://jisuxzqhf.market.alicloudapi.com";
    private static final String PATH = "/area/city";
    private static final String METHOD = "GET";
    private static final String APP_CODE = "b8334e2c6bfe4673afdfba5a9308b909";

    /**非授权信息**/
    private static final String NONE_SUB_STATUS = "203";
    /**返回0代表成功找到结果**/
    private static final String OK_STATUS = "0";

    @GetMapping(value = "/address/level")
    @ResponseBody
    public String getSubLevelAddress(@RequestParam(name = "pid") String parentLevelId) {
        JSONObject json = new JSONObject();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + APP_CODE);
        Map<String, String> querys = new HashMap<>();
        querys.put("parentid", parentLevelId);
        try {
            HttpResponse response = AliyunHttpUtils.doGet(HOST, PATH, METHOD, headers, querys);

            /*System.out.println("response.getEntity():"+response.getEntity());*/

            /**获得子地区的结果集信息**/
            String tmp = EntityUtils.toString(response.getEntity());
           /* System.out.println("tmp:"+tmp);*/
            /**转化成JSON对象方便取出其中的数据**/
            JSONObject apiReturnJson = JSON.parseObject(tmp);
            String status = apiReturnJson.getString("status");
            if (OK_STATUS.equals(status)) {
                json.put("status", 0);
                json.put("sub", apiReturnJson.get("result"));
            } else {
                if (NONE_SUB_STATUS.equals(status)) {
                    json.put("status", 1);
                } else {
                    json.put("status", 2);
                }
            }
        } catch (Exception e) {
            json.put("status", 2);
            e.printStackTrace();
        }
        return json.toString();
    }

}