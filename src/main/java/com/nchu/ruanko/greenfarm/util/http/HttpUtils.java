package com.nchu.ruanko.greenfarm.util.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Timer;
import java.util.TimerTask;

/**
 * HTTP 工具类
 *
 * 一些与 HTTP 有关的方法
 *
 * @author Yuan Yueshun
 */
public final class HttpUtils {

    /**
     * 获取 URL 的前缀，便于拼接出完整的 URL 供跨域访问
     *
     * @param request HTTP 请求
     * @return URL 前缀
     */
    public static String getUrlPrefix(HttpServletRequest request) {
        return request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * 设置特定的“属性”在 Session 中的时长
     *
     * @param session 当前会话
     * @param attrName 属性名
     * @param time 有效时间，单位“分钟”
     */
    public static void sessionAttributeInvalid(final HttpSession session, final String attrName, final Integer time) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (session.getAttribute(attrName) != null) {
                    session.removeAttribute(attrName);
                }
                timer.cancel();
            }
        }, time * 60 * 1000);
    }

}
