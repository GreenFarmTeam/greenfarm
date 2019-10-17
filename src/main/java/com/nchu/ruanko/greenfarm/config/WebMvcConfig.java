package com.nchu.ruanko.greenfarm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 有关 MVC 的相关配置
 *
 * @author Yuan Yueshun
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 拦截器配置
     *
     * 注册拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO
    }

    /**
     * 资源路径配置
     *
     * 配置静态资源路径以及文件上传后的虚拟路径
     *
     * @param registry 注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 为方便将来拦截器“放行”，引用静态资源加“/gfstatic/”前缀
        registry.addResourceHandler("/gfstatic/**").addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/");

        // 上传的文件路径映射
        // TODO 根据实际电脑上去更改
        registry.addResourceHandler("/file/upload/**").addResourceLocations("file:C:\\Users\\Lenovo\\Desktop\\校内实训\\绿色农场项目\\src\\main\\resources\\static\\uploadFile\\");
    }

    /**
     * 路径映射
     *
     * 只需要简单进行页面跳转在此注册（没有必要经过 Controller 就可以直接访问到的）
     *
     * @param registry 注册器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // TODO
    }

}