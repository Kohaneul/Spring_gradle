package com.visit.program.ReservationProgram.domain.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PageMaker {
    private int totalPage;
    private int boardNo;    // 현재 페이지
    private int startBoardNo;    //내 현재 페이지의 첫번호
    private int endBoardNo; //내 현재 페이지의 끝번호
    private int boardPerNo = 7; //한 페이지에 있는 게시글
    private int pagePerNo = 5; //페이지당 번호
    private int pageStartNo;    //페이지의 첫번호
    private int pageEndNo;      //페이지의 끝번호

    public PageMaker() {
        this.startBoardNo = ((int)(Math.ceil(boardNo/boardPerNo))-1)*boardPerNo+1;

    }
}
