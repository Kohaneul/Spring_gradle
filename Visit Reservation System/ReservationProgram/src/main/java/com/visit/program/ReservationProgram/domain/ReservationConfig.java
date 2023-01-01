package com.visit.program.ReservationProgram.domain;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ReservationConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReservationInfoInterceptor()).addPathPatterns("/reservation/info/{id}/**").order(1).excludePathPatterns("/reservation/info/save");
    }
}
