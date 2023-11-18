package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ScheduleDescription {
    private UUID id;
    private String title;
    private ScheduleType type;
}
