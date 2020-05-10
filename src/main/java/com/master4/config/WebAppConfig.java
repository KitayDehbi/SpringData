package com.master4.config;

import com.master4.Interceptors.LoginInterceptor;
import com.master4.Interceptors.ArticleInterceptor;
import com.master4.Interceptors.Taginterceptor;
import com.master4.Interceptors.UserInterceptor;
import com.master4.converter.UserConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.master4" })
@EnableAspectJAutoProxy
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolvers =new InternalResourceViewResolver();
        resolvers.setViewClass(JstlView.class);
        resolvers.setPrefix("/WEB-INF/views/");
        resolvers.setSuffix(".jsp");
        return resolvers;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserConverter());
    }


   /* @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(new String[]{
                       "/article","/article/","/user","/user/","/tag","/tag/"
                }).excludePathPatterns("/");
        registry.addInterceptor(new ArticleInterceptor()).addPathPatterns(new String[]{
                "/article/add/","/article/add","article/add/{id}/" ,"/article/add/{id}",
                "/article/delete/{page}/{id}","/article/delete/{page}/{id}/"
        });
        registry.addInterceptor(new UserInterceptor()).addPathPatterns(new String[]{
                "/user/add/","/user/add","user/add/{id}/" ,"/user/add/{id}",
                "/user/delete/{page}/{id}","/user/delete/{page}/{id}/"
        });
        registry.addInterceptor(new Taginterceptor()).addPathPatterns(new String[]{
                "/tag/add/","/tag/add","tag/add/{id}/" ,"/tag/add/{id}",
                "/tag/delete/{page}/{id}","/tag/delete/{page}/{id}/"
        });
    }*/
}
