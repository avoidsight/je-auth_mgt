package com.jiezhan.auth.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zp
 * @Date: 2019-11-25 17:46
 * @Description:
 */
@Configuration
@MapperScan("com.jiezhan.auth.mapper")
public class MybatisConfig {

    /**
     * 分页插件，自动识别数据库类型
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
