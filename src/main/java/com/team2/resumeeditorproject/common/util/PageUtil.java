package com.team2.resumeeditorproject.common.util;

import com.team2.resumeeditorproject.exception.BadRequestException;
import com.team2.resumeeditorproject.exception.NotFoundException;
import org.springframework.data.domain.Page;

public class PageUtil {
    // 페이지 값이 음수거나 마지막페이지를 초과하는 경우 재유청 유도

    public static void checkUnderZero(int pageNo) {
        if (pageNo < 0) {
            throw new BadRequestException("pageNo를 양수로 입력하세요");
        }
    }

    public static void checkExcessLastPageNo(int pageNo, int lastPageNo) {
        if (pageNo > lastPageNo) {
            throw new BadRequestException("pageNo를 lastPage보다 작거나 같게 입력하세요");
        }
    }

    public static <T> void checkListEmpty(Page<T> page) {
        if (page.getTotalElements() == 0) {
            throw new NotFoundException("결과가 존재하지 않습니다");
        }
    }

}