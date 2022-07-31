package haneul.haneulspring;

import lombok.*;

@Getter
@Setter
@ToString
public class HelloLombok {
    //lombok : 자동으로 getter, setter, toString, 생성자 생성해주는 라이브러리
    private String name;
    private int age;

    public static void main(String[] args) {
       HelloLombok helloLombok = new HelloLombok();
       helloLombok.setName("afe");
       String name = helloLombok.getName();
       System.out.println(name);
       System.out.println(helloLombok);
    }
}
