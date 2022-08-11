package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){
        //세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        //HttpServletResponse 는 인터페이스-> 스프링에서 MockHttpServletResponse 객체를 제공해서 가짜로 테스트할 수 있도록 만들어줌
        Member member = new Member();
        sessionManager.createSession(member,response);

        //요청에 응답쿠키 저장 확인
        MockHttpServletRequest request = new MockHttpServletRequest();

        //클라이언트->서버 전송
        request.setCookies(response.getCookies());

        //세션 조회
        Object result = sessionManager.getSession(request);

        //검증
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object result2 = sessionManager.getSession(request);

        //검증
        assertThat(result2).isNull();
    }
}