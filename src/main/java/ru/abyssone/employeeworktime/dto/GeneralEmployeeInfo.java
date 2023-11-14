package ru.abyssone.employeeworktime.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GeneralEmployeeInfo {
    private UUID id;
    private String name;
    private List<GeneralContractInfo> contracts = new ArrayList<>();
}
