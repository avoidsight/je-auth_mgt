package com.jiezhan.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zp
 * @Date: 2019-11-26 19:54
 * @Description:
 */
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {
    @Bean
    public Docket swaggerPlugin() {
        System.out.println("hello swagger.");
        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("Token").defaultValue("j_32f8ccc7_a9e1_49e8_aae5_a797aecb6f98").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //添加head参数end
        return new Docket(DocumentationType.SWAGGER_2)
                //配置路径
//                .host("47.100.96.107:8324")
//                .host("0.0.0.0:20010")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jiezhan.auth.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("公共模块API")
                .description("行业展会相关接口")
                .termsOfServiceUrl("swagger")
                .version("2.0")
                .build();
    }
}
