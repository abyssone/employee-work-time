package ru.abyssone.employeeworktime.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.abyssone.employeeworktime.dto.ScheduleInfo;
import ru.abyssone.employeeworktime.service.ScheduleService;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("schedule/create")
    public String getScheduleCreating() {
        return "schedule/schedule-creating";
    }

    @PostMapping("schedule/create")
    public String createSchedule(@ModelAttribute ScheduleInfo scheduleInfo) {
        scheduleService.save(scheduleInfo);
        return "schedule/schedule-creating";
    }
}
