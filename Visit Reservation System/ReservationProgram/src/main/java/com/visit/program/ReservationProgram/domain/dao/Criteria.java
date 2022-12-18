package com.visit.program.ReservationProgram.domain.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class Criteria {
    private int page;
    private int perPageNum = 7;

    public Criteria(int page){
        this.page = page;
    }

    public int getPageStart() {
        return (this.page-1)*perPageNum;
    }


    public int getPage() {
        return page;
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public void setPerPageNum(int pageCount) {
        int cnt = this.perPageNum;
        if(pageCount != cnt) {
            this.perPageNum = cnt;
            log.info("cnt={}",cnt);
        } else {
            this.perPageNum = pageCount;
            log.info("perPageNum={}",pageCount);

        }
    }


}
