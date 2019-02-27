package com.invillia.acme.utils;


import com.invillia.acme.domain.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertEquals;

class DateUtilsTest {

    @Test
    void getParse() {
        LocalDate dataLocal = DateUtils.getParse("01/12/2016");

        assertEquals("Ano", 2016, dataLocal.getYear());
        assertEquals("Mes", 12, dataLocal.getMonthValue());
        assertEquals("Dia", 1, dataLocal.getDayOfMonth());
    }

    @Test
    void getDate() throws Exception {
        LocalDate now = DateUtils.getParse("01/12/2016");
        Date date = DateUtils.toDate(now);

        assertEquals("toDate", "Thu Dec 01 00:00:00 BRST 2016", date.toString());
    }

    @Test
    void getDateString() throws Exception {
        Date date = DateUtils.getDate("01/12/2016");
        assertEquals("toDate", "Thu Dec 01 00:00:00 BRST 2016", date.toString());
    }
}