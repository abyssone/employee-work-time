package ru.abyssone.employeeworktime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.abyssone.employeeworktime.service.timemodel.FixedWorkWeekHandler;
import ru.abyssone.employeeworktime.service.timemodel.ShiftWorkScheduleHandler;
import ru.abyssone.employeeworktime.service.timemodel.WorkTimeModelService;

@Configuration
public class AppConfig {

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
