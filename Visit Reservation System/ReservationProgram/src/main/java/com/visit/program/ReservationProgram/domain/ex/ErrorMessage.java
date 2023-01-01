package com.visit.program.ReservationProgram.domain.ex;

public abstract class ErrorMessage {
    public final static String NO_REVISE_MSG ="한번 변경한 내용은 수정이 불가능합니다. 관리자에게 문의하세요";
    public final static String NOTHING_EMPLOYEE_EX ="해당 검색조건에 일치하는 직원이 없습니다.";
    public final static String REVISE_COUNT_EXCESS="수정 가능 횟수를 초과하였습니다.(2회 초과시 수정불가)";
}