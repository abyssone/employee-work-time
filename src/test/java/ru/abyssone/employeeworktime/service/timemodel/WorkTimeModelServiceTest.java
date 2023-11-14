package ru.abyssone.employeeworktime.service.timemodel;

import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
 * Инициализирует контекст для бинов из @TestConfiguration
 */
@ExtendWith(SpringExtension.class)
class WorkTimeModelServiceTest {
    @TestConfiguration
    static class WorkTimeModelServiceTestConfiguration {
        @Bean
        public FixedWorkWeekHandler fixedWorkWeekHandler() {
            return new FixedWorkWeekHandler();
        }

        @Bean
        public ShiftWorkScheduleHandler shiftWorkScheduleHandler() {
            return new ShiftWorkScheduleHandler();
        }

        @Bean
        public WorkTimeModelService workTimeModelService(FixedWorkWeekHandler fwwh,
                                                         ShiftWorkScheduleHandler swsh) {
            return new WorkTimeModelService(fwwh, swsh);
        }
    }

    @Autowired
    public WorkTimeModelService workTimeModelService;

    /*
     * Получение рабочего времени по дате при графике фиксированной рабочей недели
     */
    @Test
    public void fixedWorkWeekTest() {
        FixedWorkWeek workWeek = new FixedWorkWeek();

        TimePeriod workingDay = new TimePeriod();
        workingDay.setStartTime(LocalTime.parse("08:00:00"));
        workingDay.setEndTime(LocalTime.parse("17:00:00"));

        Map<DayOfWeek, TimePeriod> schedule = new LinkedHashMap<>() {{
           put(DayOfWeek.MONDAY, workingDay);
           put(DayOfWeek.TUESDAY, workingDay);
           put(DayOfWeek.WEDNESDAY, workingDay);
           put(DayOfWeek.THURSDAY, workingDay);
           put(DayOfWeek.FRIDAY, workingDay);
           put(DayOfWeek.SATURDAY, null);
           put(DayOfWeek.SUNDAY, null);
        }};

        workWeek.setWorkHours(schedule);

        // Понедельник
        assertEquals(workingDay, workTimeModelService.getWorkHoursForDate(workWeek,
                LocalDate.parse("2023-11-06")));
        // Пятница
        assertEquals(workingDay, workTimeModelService.getWorkHoursForDate(workWeek,
                LocalDate.parse("2023-11-10")));
        // Суббота
        assertEquals(null, workTimeModelService.getWorkHoursForDate(workWeek,
                LocalDate.parse("2023-11-11")));
    }

    /*
     * Получение рабочего времени по дате при сменном графике
     */
    @Test
    public void shiftWorkSchedule_WorkTimeForDateTest() {
        TimePeriod workTime = new TimePeriod();
        workTime.setStartTime(LocalTime.parse("12:00:00"));
        workTime.setEndTime(LocalTime.parse("18:00:00"));

        ShiftWorkSchedule workSchedule = new ShiftWorkSchedule();
        workSchedule.setStartWorkSchedule(LocalDate.parse("2023-11-09"));
        workSchedule.setWorkDaysNumber(2);
        workSchedule.setDaysOffNumber(3);
        workSchedule.setWorkHours(workTime);

        /* [ ] - выходной. [v] - рабочий день
         *
         * 9.11 | 10.11 | 11.11 | 12.11 | 13.11 | 14.11 | 15.11 | 16.11 | 17.11 | 18.11 | 19.11 | 20.11 | 21.11
         *  [v] |  [v]  |  [ ]  |  [ ]  |  [ ]  |  [v]  |  [v]  |  [ ]  |  [ ]  |  [ ]  |  [v]  |  [v]  |  [ ]
         */

        assertEquals(workTime, workTimeModelService.getWorkHoursForDate(workSchedule,
                LocalDate.parse("2023-11-10")));
        assertEquals(null, workTimeModelService.getWorkHoursForDate(workSchedule,
                LocalDate.parse("2023-11-11")));
        assertEquals(null, workTimeModelService.getWorkHoursForDate(workSchedule,
                LocalDate.parse("2023-11-12")));
        assertEquals(workTime, workTimeModelService.getWorkHoursForDate(workSchedule,
                LocalDate.parse("2023-11-19")));
        assertEquals(workTime, workTimeModelService.getWorkHoursForDate(workSchedule,
                LocalDate.parse("2023-11-20")));
    }

    /*
     * Получение рабочего времени по диапазону дат при графике фиксированной рабочей недели
     */
    @Test
    public void fixedWorkWeek_WorkTimeForDatePeriodTest() {
        FixedWorkWeek workSchedule = new FixedWorkWeek();

        TimePeriod workingDay = new TimePeriod();
        workingDay.setStartTime(LocalTime.parse("08:00:00"));
        workingDay.setEndTime(LocalTime.parse("17:00:00"));

        Map<DayOfWeek, TimePeriod> schedule = new LinkedHashMap<>() {{
            put(DayOfWeek.MONDAY, workingDay);
            put(DayOfWeek.TUESDAY, workingDay);
            put(DayOfWeek.WEDNESDAY, workingDay);
            put(DayOfWeek.THURSDAY, workingDay);
            put(DayOfWeek.FRIDAY, workingDay);
            put(DayOfWeek.SATURDAY, null);
            put(DayOfWeek.SUNDAY, null);
        }};

        workSchedule.setWorkHours(schedule);
        LocalDate startDate = LocalDate.parse("2023-11-06");
        LocalDate endDate = LocalDate.parse("2023-12-06");

        Map<LocalDate, TimePeriod> workHoursForMonth = workTimeModelService
                                                            .getWorkHoursForDatePeriod(workSchedule,
                                                                                        startDate,
                                                                                        endDate);

        assertEquals(30, workHoursForMonth.size());
        // Понедельник (метод должен возвращать Map, включающую начальную дату диапазона)
        assertEquals(workingDay, workHoursForMonth.get(startDate));
        // Пятница
        assertEquals(workingDay, workHoursForMonth.get(LocalDate.parse("2023-11-10")));
        // Суббота
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-11-11")));
        // Воскресенье
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-11-12")));
        // Вторник
        assertEquals(workingDay, workHoursForMonth.get(LocalDate.parse("2023-12-05")));
        // Среда (метод должен возвращать Map, не включающую конечную дату диапазона)
        assertFalse(workHoursForMonth.containsKey(LocalDate.parse("2023-12-06")));
    }

    /*
     * Получение рабочего времени по дате при сменном графике
     */
    @Test
    public void shiftWorkSchedule_WorkTimeForDatePeriodTest() {
        TimePeriod workTime = new TimePeriod();
        workTime.setStartTime(LocalTime.parse("12:00:00"));
        workTime.setEndTime(LocalTime.parse("18:00:00"));

        LocalDate startDate = LocalDate.parse("2023-11-09");
        LocalDate endDate = LocalDate.parse("2023-12-09");

        ShiftWorkSchedule workSchedule = new ShiftWorkSchedule();
        workSchedule.setStartWorkSchedule(startDate);
        workSchedule.setWorkDaysNumber(2);
        workSchedule.setDaysOffNumber(3);
        workSchedule.setWorkHours(workTime);

        Map<LocalDate, TimePeriod> workHoursForMonth = workTimeModelService
                                                            .getWorkHoursForDatePeriod(workSchedule,
                                                                    startDate,
                                                                    endDate);

        /* [ ] - выходной. [v] - рабочий день
         *
         * 9.11 | 10.11 | 11.11 | 12.11 | 13.11 | 14.11 | 15.11 | 16.11
         *  [v] |  [v]  |  [ ]  |  [ ]  |  [ ]  |  [v]  |  [v]  |  [ ]
         *
         * 2.11 |  3.11 |  4.11 |  5.11 |  6.11 |  7.11 |  8.11 |  9.11
         *  [ ] |  [ ]  |  [v]  |  [v]  |  [ ]  |  [ ]  |  [ ]  |  [v]
         */
        assertEquals(30, workHoursForMonth.size());

        assertEquals(workTime, workHoursForMonth.get(startDate));
        assertEquals(workTime, workHoursForMonth.get(LocalDate.parse("2023-11-10")));
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-11-11")));
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-11-13")));
        assertEquals(workTime, workHoursForMonth.get(LocalDate.parse("2023-12-05")));
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-12-06")));
        assertEquals(null, workHoursForMonth.get(LocalDate.parse("2023-12-08")));

        assertFalse(workHoursForMonth.containsKey(LocalDate.parse("2023-12-09")));
    }
}