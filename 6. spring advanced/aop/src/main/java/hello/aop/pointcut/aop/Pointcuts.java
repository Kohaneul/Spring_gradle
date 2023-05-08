package hello.aop.pointcut.aop;

import org.aspectj.lang.annotation.Pointcut;

//Pointcut 모둘화
public class Pointcuts {
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){}

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
