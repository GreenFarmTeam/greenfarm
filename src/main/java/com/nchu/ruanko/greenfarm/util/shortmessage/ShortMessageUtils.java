package com.nchu.ruanko.greenfarm.util.shortmessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信工具类
 *
 * @author Yuan Yueshun
 */
public final class ShortMessageUtils {

    private static final String SIGN_NAME = "绿色农场";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String VERSION = "2017-05-25";
    private static final String ACTION = "SendSms";
    private static final String REGION_ID = "default";
    private static final String ACCESS_KEY_ID = "LTAI4FwoLysPvAPPcVXRDRm9";
    private static final String ACCESS_KEY_SECRET = "f8N0GRr2kQtHQAGPCC7WY3OYOgTN91";

    private static IAcsClient client;

    static {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }

    /**
     *
     * @param template 短信模板编号
     * @param phone 手机号
     * @param code 验证码
     * @return JSON 格式的响应结果
     * @throws ClientException 异常
     */
    public static String sendVerificationCodeMessage(String template, String phone, String code) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);
        request.setAction(ACTION);
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("PhoneNumbers", phone);
        JSONObject json = new JSONObject();
        json.put("code", code);
        request.putQueryParameter("TemplateParam", json.toString());
        CommonResponse response = client.getCommonResponse(request);
        return response.getData();
    }

    /**
     *
     * @param template 短信模板编号
     * @param phone 手机号
     * @param json 封装成 JSON 的替换文本
     * @return JSON 格式的响应结果
     * @throws ClientException 异常
     */
    public static String sendMessage(String template, String phone, JSONObject json) throws ClientException {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(DOMAIN);
        request.setVersion(VERSION);
        request.setAction(ACTION);
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateParam", json.toString());
        CommonResponse response = client.getCommonResponse(request);
        return response.getData();
    }

    /**
     * 获取 API 错误码
     *
     * @param responseData 短息 API 调用后返回的 JSON
     * @return API 错误码
     */
    public static String getErrorCode(String responseData) {
        return JSON.parseObject(responseData).getString("Code");
    }

    /**
     * 获取 API 错误描述
     *
     * @param responseData 短息 API 调用后返回的 JSON
     * @return API 错误描述
     */
    public static String getErrorDescription(String responseData) {
        String code = getErrorCode(responseData);
        String description = "";
        for (ShortMessageErrorsEnum elem : ShortMessageErrorsEnum.values()) {
            if (elem.getCode().equals(code)) {
                description = elem.getDescription();
                break;
            } else {
                description = ShortMessageErrorsEnum.UNKNOWN_ERROR.getDescription();
            }
        }
        return description;
    }



}
