package com.janwes.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mall.config
 * @date 2021/6/29 14:43
 * @description swagger接口文档配置
 */
@Configuration
@EnableSwagger2
//@EnableSwaggerBootstrapUI
@Profile({"dev,test"}) // 生产环境下不开启swagger2文档
public class Swagger2Configuration {

    @Bean
    public Docket buildDocket() {
        HashSet<String> responseData = new HashSet<>();
        responseData.add("application/json");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                // 设置返回数据类型
                .produces(responseData)
                // 分组名称
                .groupName("1.0")
                .select()
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.janwes"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("婴幼儿商品跨境商城-平台服务API文档")
                // 描述
                .description("平台服务API")
                // 联系信息
                .contact(new Contact("3T technology", "https://swagger.io", ""))
                // 版本
                .version("1.0")
                .build();
    }
}
