package ru.abyssone.employeeworktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.entity.WorkTimeReport;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeReportRepository;
import ru.abyssone.employeeworktime.service.timemodel.WorkTimeModelService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@SpringBootApplication
public class EmployeeWorkTimeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EmployeeWorkTimeApplication.class, args);

        ContractRepository cr = context.getBean(ContractRepository.class);
        EmployeeRepository er = context.getBean(EmployeeRepository.class);
        WorkTimeModelRepository wtmr = context.getBean(WorkTimeModelRepository.class);
        WorkTimeReportRepository wtrr = context.getBean(WorkTimeReportRepository.class);

        WorkTimeModelService wtms = context.getBean(WorkTimeModelService.class);

        // work time report
//        FixedWorkWeek fixedWorkWeek = new FixedWorkWeek();
//
//        Map<DayOfWeek, TimePeriod> workHours = new LinkedHashMap<>();
//        TimePeriod workDay = new TimePeriod();
//        workDay.setStartTime(LocalTime.parse("08:00:00"));
//        workDay.setEndTime(LocalTime.parse("17:00:00"));
//
//        workHours.put(DayOfWeek.MONDAY, workDay);
//        workHours.put(DayOfWeek.TUESDAY, workDay);
//        workHours.put(DayOfWeek.WEDNESDAY, workDay);
//        workHours.put(DayOfWeek.THURSDAY, workDay);
//        workHours.put(DayOfWeek.FRIDAY, workDay);
//        workHours.put(DayOfWeek.SATURDAY, null);
//        workHours.put(DayOfWeek.SUNDAY, null);
//
//        fixedWorkWeek.setWorkHours(workHours);
//
//        Contract contract = new Contract();
//        contract.setWorkTimeModel(fixedWorkWeek);
//
//        cr.save(contract);

        Optional<Contract> byId = cr.findById(UUID.fromString("2fec1458-37e0-472a-ab56-10db33c7f0da"));
        Contract contract = byId.get();
        TimePeriod workHoursForDate = wtms.getWorkHoursForDate(contract.getWorkTimeModel(), LocalDate.parse("2023-11-10"));
        System.out.println();


//        // shift work schedule
//        ShiftWorkSchedule wtm1 = new ShiftWorkSchedule();
//        wtm1.setWorkHours(8);
//        wtm1.setStartWorkSchedule(LocalDate.parse("2020-01-01"));
//        wtm1.setWorkDaysNumber(3);
//        wtm1.setDaysOffNumber(2);
//
//        // fixed work week
//        FixedWorkWeek wtm2 = new FixedWorkWeek(new Integer[] {5, 5, 5, 4, 4, 0, 0});
//
//        // contract
//        Contract c1 = new Contract();
//        c1.setPosition("developer");
//        c1.setWorkTimeModel(wtm1);
//
//        Contract c2 = new Contract();
//        c2.setPosition("proj manager");
//        c2.setWorkTimeModel(wtm2);
//
//
//        // employee
//        Employee e1 = new Employee();
//        e1.setName("name");
//        e1.addContract(c1);
//
//        Employee e2 = new Employee();
//        e2.setName("name2");
//        e2.addContract(c2);
//
//        er.save(e1);
//        er.save(e2);

        //==============================================================================================================
//        Optional<Employee> employee = er.findById(UUID.fromString("462f59cd-9451-49ea-83a4-750683a2f320"));
//        Set<Contract> contracts = employee.get().getContracts();
//        contracts.stream().forEach(contract -> {
//            System.out.println(contract.getWorkTimeModel().getClass());
//        });
//
//        System.out.println();

        //f0900acb-ad08-4c43-9fff-0cae30501714
        //Contract contractOpt = cr.getByIdFetchWorkTimeReports(UUID.fromString("f0900acb-ad08-4c43-9fff-0cae30501714"));


//        contractOpt.addWorkTimeReport(wtr);
//
//        cr.save(contractOpt);

        System.out.println();

    }

}
