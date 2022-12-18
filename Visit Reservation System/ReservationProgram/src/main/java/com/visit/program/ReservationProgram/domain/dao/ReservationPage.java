package com.visit.program.ReservationProgram.domain.dao;

import lombok.Data;

import java.util.List;

@Data
public class ReservationPage {
    private int pagePerCount = 7; //한 페이지당 보여지는 게시글 수
    private int count = 5; //페이징 버튼 갯수
    private int currentPageButton;    //현재 페이지 번호
    private int startNo;  //시작 게시글 번호
    private int endNo;  //끝나는 게시글 번호
    private int startPageButton; //(게시글이 속한 페이지의 )시작 페이지 번호
    private int endPageButton;    //(게시글이 속한 페이지의 )끝나는 페이지


    public ReservationPage(int totalCount, int currentNo) {   //총 게시글 갯수, 내가 조회하고 싶은 글번호

        System.out.println("총 게시글 수 " + totalCount);
        System.out.println("조회하고 싶은 글번호 = " + currentNo);

        int i = currentNo/pagePerCount;
        if((currentNo % pagePerCount)>0){
            i++;
        }
        setCurrentPageButton(i);
        System.out.println("현재 페이지 = " + getCurrentPageButton());

        if(i>pagePerCount){
            setEndNo(i*pagePerCount);
            setStartNo(getEndNo()-pagePerCount+1);
        }
        else{
            setStartNo(1);
            setEndNo(7);
        }
        int i1 = currentNo / (pagePerCount*count);
        setStartPageButton(((i1*count)+1));
        setEndPageButton(getStartPageButton()+count-1);

        System.out.println("getStartPageButton() = " + getStartPageButton());
        System.out.println("getEndPageBUtton()="+getEndPageButton());

    }



    }




