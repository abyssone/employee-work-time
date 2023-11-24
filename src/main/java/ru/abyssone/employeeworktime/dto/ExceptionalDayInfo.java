package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ExceptionalDayInfo {

    private Long id;
    private String date;
    private String startTime;
    private String endTime;
    private String info;
    private List<UUID> contracts = new ArrayList<>();
}
