package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {
    @Test
    void dynamicA() throws NoSuchMethodException {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        //클래스 로더 : 런타임에 클래스를 동적으로 JVM에 로드 하는 역할
        //Proxy.newProxyInstance => 자바에서 지원하는 프록시기술
        proxy.call();   //handler에 있는 로직 수행
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }


    @Test
    void dynamicB() throws NoSuchMethodException{
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }
}
