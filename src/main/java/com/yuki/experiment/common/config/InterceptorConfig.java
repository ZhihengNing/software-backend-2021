package com.yuki.experiment.common.config;

import com.yuki.experiment.common.interceptor.SampleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    private final SampleInterceptor sampleInterceptor;

    @Autowired
    public InterceptorConfig(SampleInterceptor sampleInterceptor){
        this.sampleInterceptor=sampleInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sampleInterceptor)
                //.addPathPatterns("/**")
                .excludePathPatterns("/**/sys/login/**","/**/doc.html");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
