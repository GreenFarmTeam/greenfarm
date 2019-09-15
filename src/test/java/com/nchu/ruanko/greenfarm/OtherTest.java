package com.nchu.ruanko.greenfarm;

import com.aliyuncs.exceptions.ClientException;
import com.nchu.ruanko.greenfarm.util.http.CertificationUtils;
import com.nchu.ruanko.greenfarm.util.shortmessage.ShortMessageUtils;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.junit.Test;

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
        for (int i = 0; i <= 100; i++) {
            int result = (int)((31 + 1) * Math.random() + 0);
            System.out.println(result);
        }

    }


}
