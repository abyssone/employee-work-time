package ru.abyssone.employeeworktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import ru.abyssone.employeeworktime.entity.timemodel.ShiftWorkSchedule;
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;

import java.sql.Array;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class EmployeeWorkTimeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EmployeeWorkTimeApplication.class, args);

        ContractRepository cr = context.getBean(ContractRepository.class);
        EmployeeRepository er = context.getBean(EmployeeRepository.class);
        WorkTimeModelRepository wtmr = context.getBean(WorkTimeModelRepository.class);

//        ShiftWorkSchedule wtm1 = new ShiftWorkSchedule();
//        wtm1.setWorkHours(8);
//        wtm1.setStartWorkSchedule(LocalDate.parse("2020-01-01"));
//        wtm1.setWorkDaysNumber(3);
//        wtm1.setDaysOffNumber(2);
//
//        FixedWorkWeek wtm2 = new FixedWorkWeek(new Integer[] {5, 5, 5, 4, 4, 0, 0});
//
//        Contract c1 = new Contract();
//        c1.setPosition("developer");
//        c1.setWorkTimeModel(wtm1);
//
//        Contract c2 = new Contract();
//        c2.setPosition("proj manager");
//        c2.setWorkTimeModel(wtm2);
//
//
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

        Optional<Employee> employee = er.findById(UUID.fromString("b1d97d84-23bc-4230-bf62-1f64414d7b0a"));
        Set<Contract> contracts = employee.get().getContracts();
        contracts.stream().forEach(contract -> {
            System.out.println(contract.getWorkTimeModel().getClass());
        });

        System.out.println();


    }

}
