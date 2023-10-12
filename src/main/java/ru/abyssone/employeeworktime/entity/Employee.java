package ru.abyssone.employeeworktime.entity;

import java.util.List;
import java.util.Optional;

public class Employee {
    private String name;
    private List<Contract> contracts;
    private Optional<Account> account;
}
