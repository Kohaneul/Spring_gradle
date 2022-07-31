package haneul.haneulspring.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class SingletonService {
    private static final SingletonService instance = new SingletonService();
    //자기자신을 내부에 static으로 가지고 있음 => 클래스레벨에 존재하므로 딱 하나만 생성가능

    //다른 객체에서 생성을 막기 위해서 private 생성자를 만든다
    private SingletonService() {
    }


    //SingletonService 객체가 생성될때 new SingletonService 객체 생성하면서 instance에 참조를 넣어놓는다

    public static SingletonService getInstance(){
        return instance;//SingletonService를 꺼낼 수 있는 유일한 방법 : getInstance 메소드를 호출
    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }



}
