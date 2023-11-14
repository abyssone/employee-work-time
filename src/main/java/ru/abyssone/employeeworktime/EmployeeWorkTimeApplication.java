package ru.abyssone.employeeworktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// mapper.* содержит реализации мапперов, генерируемых MapStruct
@SpringBootApplication
public class EmployeeWorkTimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeWorkTimeApplication.class, args);
    }
}
