package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>();
    //동시에 여러 요청이 접근하기 때문에 ConcurrentHashMap으로 사용

    //세션 생성
    public void createSession(Object value, HttpServletResponse response){

        //세션 id를 생성하고, 값을 세션에 저장한다.
        String sessionId= UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);
        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    //세션 조회
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookies(request,SESSION_COOKIE_NAME);
        if(sessionCookie == null){
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());

    }

    /**
     * 세션 만료
     *
     * */
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookies(request, SESSION_COOKIE_NAME);
        if(sessionCookie!=null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }
    public Cookie findCookies(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return null;
        }
//        for (Cookie cookie : cookies) {
//            if(cookie.getName().equals(cookieName)){
//                return cookie;
//            }
//        }
    return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName)).findAny().orElse(null);
        //findFirst : 순서 중에 제일 먼저 나온애 , findAny : 순서 상관없이 찾으면 반환
    }


}
