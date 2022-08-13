package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
      log.info("supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        //파라미터 호출하기 전에 로그인 Annotation이 있는가
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());//@Login Member라면 Member 타입
        return hasLoginAnnotation && hasMemberType; //둘 다 실행하면 resolveArgument가 실행됨
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");
        HttpServletRequest nativeRequest = (HttpServletRequest)webRequest.getNativeRequest();
        HttpSession session = nativeRequest.getSession(false);
        if(session==null){
            return null;    //HomeController에서 homeLoginV4ArgumentResolver 파라미터인 Member가 null로 반환된다.
        }

        return  session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
