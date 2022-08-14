package hello.login.web.login;

import hello.login.web.argumentResolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

@Configuration  //빈등록을
public class WebConfig implements WebMvcConfigurer {

    @Override   //LoginMemberArgumentResolver Override 해줘야한다.
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).order(1)
                .addPathPatterns("/**")   //하위는 전부다
                .excludePathPatterns("/css/**","/*.ico","/error"); //이건 생략

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**") //이 경로에서는 적용하지만
                .excludePathPatterns("/","/members/add","/login","/logout","/css/**","/*.ico","/error");    //이 경로는 제외
    }

   // @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());//내가 설정했던 필터 클래스를 넣어준다.
        filterRegistrationBean.setOrder(1);//순서 정해줌
        filterRegistrationBean.addUrlPatterns("/*");//어떤 URL 패턴을 할것인가 : 모든 URL에 다 적용된다.

        return filterRegistrationBean;
    }


   // @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");//모든 url이 해당되는것처럼 보이지만, LoginCheckFilter 객체를 넣어줬으므로 여기서 걸러짐
        return filterRegistrationBean;
    }

}
