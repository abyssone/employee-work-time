package ru.abyssone.employeeworktime.entity.timemodel;

import ru.abyssone.employeeworktime.exception.IncorrectArgsNumber;

import java.time.*;
import java.util.*;

public class FixedWorkWeek extends WorkTimeModel{
    private final Map<DayOfWeek, Integer> workHours;

    {
        workHours = new LinkedHashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            workHours.put(dayOfWeek, 0);
        }
    }

    public FixedWorkWeek() {}

    public FixedWorkWeek(Integer[] workHours) {
        if (workHours.length != DayOfWeek.values().length)
            throw new IncorrectArgsNumber(DayOfWeek.values().length, workHours.length);

        int i = 0;
        Iterator<DayOfWeek> iterator = this.workHours.keySet().iterator();

        while(iterator.hasNext()){
            this.workHours.put(iterator.next(), workHours[i]);
            i++;
        }
    }

    @Override
    public Integer getWorkHours(LocalDate date) {
        return this.workHours.get(date.getDayOfWeek());
    }

    @Override
    public Map<LocalDate, Integer> getWorkHours(LocalDate start, LocalDate end) {
        LocalDate currentDate = start;
        Map<LocalDate, Integer> result = new HashMap<>();
        long duration = Duration.between(
                LocalDateTime.of(start, LocalTime.MIDNIGHT),
                LocalDateTime.of(end, LocalTime.MIDNIGHT)).toDays();

        for (long i = 0; i < duration; i++) {
            result.put(currentDate, this.workHours.get(currentDate.getDayOfWeek()));
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    //todo: удалить
    public Map<DayOfWeek, Integer> getWorkHoursCopy() {
        return new LinkedHashMap<>(this.workHours);
    }
}
