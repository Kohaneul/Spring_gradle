package hello.login.web.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());//내가 설정했던 필터 클래스를 넣어준다.
        filterRegistrationBean.setOrder(1);//순서 정해줌
        filterRegistrationBean.addUrlPatterns("/*");//어떤 URL 패턴을 할것인가 : 모든 URL에 다 적용된다.

        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");//모든 url이 해당되는것처럼 보이지만, LoginCheckFilter 객체를 넣어줬으므로 여기서 걸러짐
        return filterRegistrationBean;
    }

}
