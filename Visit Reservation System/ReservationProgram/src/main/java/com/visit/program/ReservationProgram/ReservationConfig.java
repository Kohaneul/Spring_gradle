package com.visit.program.ReservationProgram;

import com.visit.program.ReservationProgram.domain.interceptor.ReservationInfoInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@Configuration
public class ReservationConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReservationInfoInterceptor()).addPathPatterns("/**").order(1).
                excludePathPatterns("/reservation/info/all","/reservation/info/all_","/reservation/info/all/rapigen_security","/reservation/info/{id}","/",
                        "/reservation/info/click/{id}");
    }


}
