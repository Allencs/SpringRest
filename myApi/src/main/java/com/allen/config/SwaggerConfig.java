package com.allen.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: allen
 * @Date: 2022/4/14 10:56
 * @Description: Swagger配置类
 **/

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /*引入Knife4j提供的扩展类*/
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public SwaggerConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket docket(){
        String groupName="default";
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                .apiInfo(apiInfo()).enable(true)
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("com.allen.controller"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("myApi接口文档")
                .description("SpringBoot简单接口服务")
                .contact(new Contact("Allen", "作者URL - ⛱", "作者Email - ⛱"))
                .version("1.0")
                .build();
    }
}
