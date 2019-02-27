package com.invillia.acme.environments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;

import static org.mockito.Mockito.when;

@Component
public class EnvMockClock {

    private static final int HOUR = 0;
    private static final int MINUTE = 01;

    @Autowired
    public Clock clock;

    public void setDate(int year, Month month, int dayOfMonth) {
        LocalDateTime localDateTime = getDateTest(year, month, dayOfMonth);
        Clock fixed = Clock.fixed(localDateTime.toInstant(ZoneOffset.of("-03:00")), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixed.instant());
        when(clock.getZone()).thenReturn(fixed.getZone());
    }

    private LocalDateTime getDateTest(int year, Month month, int dayOfMonth) {
        return LocalDateTime.of(year, month, dayOfMonth, HOUR, MINUTE);
    }
}
