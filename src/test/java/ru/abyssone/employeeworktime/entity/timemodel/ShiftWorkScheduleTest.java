package ru.abyssone.employeeworktime.entity.timemodel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.abyssone.employeeworktime.exception.IllegalDateValue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShiftWorkScheduleTest {

    ShiftWorkSchedule ws;
    LocalDate start;
    Integer workDaysNumb;
    Integer daysOffNumb;
    Integer workHours;

    @BeforeAll
    void init() {
        start = LocalDate.parse("2000-01-01");
        workDaysNumb = 3;
        daysOffNumb = 2;
        workHours = 8;

        ws = new ShiftWorkSchedule(start, workDaysNumb, daysOffNumb, workHours);
    }

    @Test
    void checkIllegalDate() {
        assertThrows(IllegalDateValue.class, () -> ws.getWorkHours(LocalDate.parse("1999-01-01")));
    }

    /**
     * Start date - 01.01.2000;
     * Work days: (1 - 3, 6 - 8).01.2000;
     * Days off: (4, 5, 9, 10).01.2000;
     */
    @Test
    void checkGettingOfWorkHours_whenWorkDay() {
        assertAll(
            () -> assertEquals(workHours, ws.getWorkHours(LocalDate.parse("2000-01-01"))),
            () -> assertEquals(workHours, ws.getWorkHours(LocalDate.parse("2000-01-03"))),
            () -> assertEquals(workHours, ws.getWorkHours(LocalDate.parse("2000-01-06"))),
            () -> assertEquals(workHours, ws.getWorkHours(LocalDate.parse("2000-01-08"))));
    }

    @Test
    void checkGettingOfWorkHours_whenDayOff() {
        assertAll(
            () -> assertEquals(0, ws.getWorkHours(LocalDate.parse("2000-01-04"))),
            () -> assertEquals(0, ws.getWorkHours(LocalDate.parse("2000-01-05"))),
            () -> assertEquals(0, ws.getWorkHours(LocalDate.parse("2000-01-09"))),
            () -> assertEquals(0, ws.getWorkHours(LocalDate.parse("2000-01-10"))));
    }

    @Test
    void checkGettingOfWorkHours_whenDateRange() {
        Map<LocalDate, Integer> expected = new HashMap<>() {{
            put(LocalDate.parse("2000-01-03"), 8);
            put(LocalDate.parse("2000-01-04"), 0);
            put(LocalDate.parse("2000-01-05"), 0);
            put(LocalDate.parse("2000-01-06"), 8);
        }};
        Map<LocalDate, Integer> received = ws.getWorkHours(
                LocalDate.parse("2000-01-03"),
                LocalDate.parse("2000-01-07"));

        for (Map.Entry<LocalDate, Integer> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), received.get(entry.getKey()));
        }
    }

}