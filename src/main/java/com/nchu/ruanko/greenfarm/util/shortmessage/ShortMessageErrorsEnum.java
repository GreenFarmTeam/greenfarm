package com.nchu.ruanko.greenfarm.util.shortmessage;

public enum ShortMessageErrorsEnum {

    OK("OK", "发送成功"),
    MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL", "您输入的号码无效！"),
    BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL", "向同一手机号发送短信过于频繁，请稍后重试！"),
    AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH", "系统短信业务费用不足！"),
    UNKNOWN_ERROR("", "发生未知错误！");

    private String code;
    private String description;

    ShortMessageErrorsEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }

}