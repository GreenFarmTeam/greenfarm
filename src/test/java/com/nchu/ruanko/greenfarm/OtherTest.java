package com.nchu.ruanko.greenfarm;

import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.controller.common.AddressCascadeController;
import com.nchu.ruanko.greenfarm.util.http.CertificationUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTest {
    @Autowired
    AddressCascadeController addressCascadeController;

    @Test
    public void demo1() throws Exception {

        System.out.println(StringUtils.encodeBase64("1337116052@qq.com"));



    }


}
