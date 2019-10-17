package com.nchu.ruanko.greenfarm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.service.OrderService;
import com.nchu.ruanko.greenfarm.util.http.AliyunHttpUtils;
import com.nchu.ruanko.greenfarm.util.http.CertificationUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTest {
    @Autowired
    private OrderService orderService;


    @Test
    public void test(){
        System.out.println(orderService.loadAllOrdersOfBusiness("1d0602ec9316495182f6506a2dbec573",1,5,5));
    }




}
