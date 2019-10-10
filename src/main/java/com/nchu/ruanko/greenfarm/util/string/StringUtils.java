package com.nchu.ruanko.greenfarm.util.string;

import java.util.Random;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 字符串处理工具类
 *
 * @author Yuan Yueshun
 */
public final class StringUtils {

    private static final String RANDOM_POOL = "1234567890";
    private static final String SALT = "$greenfarm$";


    /**
     * 进行 MD5 编码
     *
     * @param string 原字符串
     * @return 经 MD5 编码后的字符串
     */
    public static String encodeMd5(String string) {
        String temp1 = SALT.charAt(0) + string + SALT.charAt(3) + SALT.charAt(2) + SALT.charAt(1);
        String temp2 = SALT.charAt(3) + SALT.charAt(2) + temp1 + SALT.charAt(1) + SALT.charAt(0);
        return DigestUtils.md5Hex(temp2);
    }



    /**
     * 进行 Base64 编码
     *
     * @param string 原字符串
     * @return 经 Base64 编码后的字符串
     */
    public static String encodeBase64(String string) {
        return Base64.encodeBase64String(string.getBytes());
    }

    /**
     * 进行 Base64 解码
     *
     * @param string 编码后的字符串
     * @return 解码后的字符串
     */
    public static String decodeBase64(String string) {
        if (string == null || "".equals(string)) {
            return null;
        }
        return new String(Base64.decodeBase64(string));
    }

    /**
     * 产生 32位的 UUID
     *
     * @return 产生的 UUID
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 产生短信验证码
     *
     * @param figure 位数
     * @return 短信验证码
     */
    public static String createVerificationCode(int figure) {
        if (figure <= 0) {
            figure = 4;
        }
        StringBuilder stringBuilder = new StringBuilder(figure);
        for (int i = 0; i < figure; i++) {
            char ch = RANDOM_POOL.charAt(new Random().nextInt(RANDOM_POOL.length()));
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    /**
     * 对“联系方式”进行脱敏（隐藏中间4位）处理
     *
     * @param phone 联系方式
     * @return 脱敏处理后的“联系方式”
     */
    public static String desensitizePhoneNumber(String phone) {
        if (phone == null || "".equals(phone)) {
            return null;
        }
        char[] seq = phone.toCharArray();
        for (int i = 3; i <= 6; i++) {
            seq[i] = '*';
        }
        return String.copyValueOf(seq);
    }

    /**
     * 对“身份证号”进行脱敏处理
     *
     * 33062219******001*
     *
     * @param idCard 身份证号
     * @return 脱敏处理后的“身份证号”
     */
    public static String desensitizeIdCard(String idCard) {
        if (idCard == null || "".equals(idCard)) {
            return null;
        }
        char[] seq = idCard.toCharArray();
        seq[seq.length - 1] = '*';
        for (int i = 8; i <= 13; i++) {
            seq[i] = '*';
        }
        return String.copyValueOf(seq);
    }

    /**
     * 对“姓名”进行脱敏处理
     *
     *
     * @param name 姓名
     * @return 脱敏处理后的“姓名”
     */
    public static String desensitizeName(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        char[] seq = new char[2];
        seq[0] = name.charAt(0);
        seq[1] = '*';
        return String.copyValueOf(seq);
    }

    /**
     * 对“邮箱”进行脱敏处理
     *
     * @param mail 邮箱
     * @return 脱敏处理后的“邮箱”
     */
    public static String desensitizeMail(String mail) {
        if (mail == null || "".equals(mail)) {
            return null;
        }
        return mail.charAt(0) + "*" + mail.substring(mail.lastIndexOf("@"));
    }

}