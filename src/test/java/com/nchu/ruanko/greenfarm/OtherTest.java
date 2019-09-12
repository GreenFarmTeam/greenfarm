package com.nchu.ruanko.greenfarm;

import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.junit.Test;

public class OtherTest {

    @Test
    public void demo1() throws ClientException {
//        String mail = "2161624113@vip.qq.com";
//        String mail = "y2161624113@163.com";
//        System.out.println(mail.substring(mail.lastIndexOf("@") + 1, mail.lastIndexOf(".")).replace(".", ""));
//        System.out.println(mail.substring(0, mail.lastIndexOf("@")));
        String data = ShortMessageUtils.sendVerificationCodeMessage("SMS_174020806", "18548327651", StringUtils.createVerificationCode(5));
        System.out.println(data);
    }

}
