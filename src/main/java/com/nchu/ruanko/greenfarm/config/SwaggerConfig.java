package com.nchu.ruanko.greenfarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 2 配置
 *
 * 访问 http://localhost:8080/swagger-ui.html 进入 URL 文档
 *
 * @author Yuan Yueshun
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nchu.ruanko.greenfarm.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("绿色农场")
                .description("绿色农场 URL 文档")
                .version("1.0")
                .contact(contact())
                .license("Apache 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    private Contact contact() {
        return new Contact("GreenFarm项目组", "https://github.com/GreenFarmTeam/greenfarm/", "2161624113@qq.com");
    }

}