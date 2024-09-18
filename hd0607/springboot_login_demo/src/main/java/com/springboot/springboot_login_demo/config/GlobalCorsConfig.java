//package com.springboot.springboot_login_demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class GlobalCorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")    //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
//                        .allowedOrigins("*")    //开放哪些ip、端口、域名的访问权限 SpringBoot2.4.0以后allowedOrigins被allowedOriginPatterns代替
//                        .allowCredentials(true)  //是否允许发送Cookie信息
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")     //开放哪些Http方法，允许跨域访问
//                        .allowedHeaders("*")     //允许HTTP请求中的携带哪些Header信息
//                        .exposedHeaders("*");   //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//            }
//        };
//    }
//}


package com.springboot.springboot_login_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 如果您确定要允许所有来源进行跨域请求，并且这些来源都是可信的，可以保持 "*"
                // 否则，您应该替换为具体的域名或使用 allowedOriginPatterns 来设置更安全的跨域规则
                registry.addMapping("/**")    // 添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
                        .allowedOriginPatterns("*")  // 使用 allowedOriginPatterns 代替 allowedOrigins
                        .allowCredentials(true)  // 是否允许发送Cookie信息
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 开放哪些Http方法，允许跨域访问
                        .allowedHeaders("*")     // 允许HTTP请求中的携带哪些Header信息
                        .exposedHeaders("*");   // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
            }
        };
    }
}

