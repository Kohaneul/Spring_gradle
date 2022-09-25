package jpabook.jpashop.service;

public class DuplicateMemberName extends RuntimeException{
    private String message;

    public DuplicateMemberName(String message) {
        super(message);
    }
}
