package ru.abyssone.employeeworktime;

import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;

import java.time.LocalDate;
import java.util.Map;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
public class EmployeeWorkTimeApplication {

    public static void main(String[] args) {
//        SpringApplication.run(EmployeeWorkTimeApplication.class, args);

        FixedWorkWeek week = new FixedWorkWeek(new Integer[]{6, 6, 7, 7, 8, 0, 0});

        Map<LocalDate, Integer> workHours = week.getWorkHours(LocalDate.now(), LocalDate.parse("2023-10-30"));
        System.out.println(workHours);
        System.out.println();
    }

}
