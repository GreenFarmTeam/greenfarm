package com.nchu.ruanko.greenfarm.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    /**
     * 应用ID，APPID，收款账号既是您的APPID对应支付宝账号
     */
    public static String app_id = "2016101300674380";

    /**
     * 商户私钥，您的 PKCS8 格式 RSA2 私钥
     */
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCCq8N3Lpm1dLRA5mcOX5/qgng+w8VZFjY1NEF40AEA7+/gHAVs8RCiIIg2gafsQs3qjth8s82/4Z3c3vIKwKDFY9JsO8d6LPkCiax2Ro0GGkDaQyefZHyGajS/jlho+yhWJf/jaL2OalOa97d+GPWmAm5IYw9QL3uqUzidW96X+lX+3rfwrRxUie9hA53jpU3xrrbNTy5hBcFoROg7I1RLryHRVY8eg9iB2ev2W3e8xT1qeQLsJWlxAdTecHwO6bn4buDYuwlnhbbZZrbs+vTbspgDyYgycjjU9bEtxLA0Mv2d/UzkGOGZA8koB1TT858BKjQ8z7C0hJ1uiZT+0CErAgMBAAECggEAGhVjTTUWJayIFrRnOe1IpK0qh4zYpfHTHEe0EgOjT6aVgWzWgGqWYWaRCiBTfsvPAtVEzY+usHC+7EnskszkysIkTMqaBP9ZLXKB5JIssF0pOOhSznRmjwZARUSKzVnlMV0geSp9PCJpUfPEwv0EfADcNsLfWahAzT0TfgXKpxdeJ/u2LL3vQcuCeLHvuQvGC1YkDKBTxIIJ2m3S6pQ5yAExk3d/KlhVh0I5I3jgXKXW+rDSeyikPRFMkW3MItUNosq3eAlYpEUEpy8RTMnvHMy3QgS95HYJI6b6fOrSg3VWKq6GekTuh2XKSow3Kn16Yd513pw5CCNG7hWzYPoX0QKBgQDH9YiflO0TZfP279Wi/24IacWWdJYW2v0I+3v6qQTQJtflQPqHzCB00VugvuyE2BqsOtoK3VKNlprlE1syFbtI9jgJ8wS7+QgvZ0jlaArSv2k9wTUW6mKF1+tNgnbiOW8jh6QTK2EXnuklI4GQFyImhrap4UlpgcNiA7UOiNnQTQKBgQCnSwMUta/A7H8b25ZQJ0TKu+vMu7vw9OA+sojfURbMi1LNemDZBmR83Otm7pkGbql6g89mCQxnMG+X4CvXP5EjPrbamcTZMzQMqq0NmTny72YeIVASEzAI5uLlh72/7HGNdIof/hThzi9tziZ5RCTbKEjiq6M/Pwm7wzDclvczVwKBgHOb10EEtslHYfU8lxUlf3WnydCCWaCfPjrqQ7UZ8alzO9HVbsQC1iUUQli+6jqoQ0uevyAW9Dref3HAbjKX8+9sQ9wkwHD5u6zEl5XjQJa2il9lDI/SNeJSgz4OFzQWptYw/0ECDKG+g+ewDqHDbkA+FPBFS8+PceqCYDUmFGDlAoGAeAvH5jCKMfKDM8ZbEqFbJ++5wWxl7P6Le/I5baaOqbDAdlp+7L09lqwg7/f/LzJxw0yaoymTEOkc6useCpO+3n++TxdQvluT2hMPo1Z00vMfYDefcZj7OygTYYoKi5Jr2YnrJBH7gh2mwzDPQu23VNyPkLiBqHzfyzDB+0kJknkCgYA2DkeDh5II2tmok0VZjrpcTOvkQXSVzLWFDe5O79CIi0xb/NRIKJQSfxHiKJaygMuDgjFBSRbjgCumGHAl+e3wNL8JL1cj20M4msqOEWf3kklZkKQTIlqfF31q2TCT+fXDU+BXqZ/TSoCMt752965cXfj62ZsRZ7ZLrY2rJ6wXqw==";

    /**
     * 支付宝公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应 APPID 下的支付宝公钥。
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApshnObVYtPSJ+ubUbv4sMLkiBVd4FjH2zno/5yxibWwQ3pDNphJFpUfE2oFl6/wscgIznj4S9saDEWcO+zyqX7OfZAjNsxuHnOxAoFy36IWZgYWf+hJ5fPectsNRJK0Sf7/cMRIpA5R51BWPCVdLV/p05e09jATXFLrlGXRu4ftVW+YK0qHELIOWz43HcgbOmAwEJXroul1eR3aiO04g3ONadYXy1RhoSxV//o7F3wfUsucj3UV08CSk4Cj1UmGrNVzkMqMPXQ7tvTuFMvlzXcPtQ52ccE84KXT1DQRlzB/WD+lDiRrIdaDkm+wZvTNdtAdXQxvccdbTUg6lWxJKVwIDAQAB";

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String notify_url = "";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String return_url = "http://localhost:8080/demo1/pay/tip/success";

    /**
     * 签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";

    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 日志存储位置
     */
    private static final String LOG_PATH = "E:\\greenfarm\\alipaylog";


    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
