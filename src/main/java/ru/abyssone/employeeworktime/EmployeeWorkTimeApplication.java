package ru.abyssone.employeeworktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.service.ContractService;

import java.util.Map;

@SpringBootApplication
public class EmployeeWorkTimeApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(EmployeeWorkTimeApplication.class, args);
        ContractService cs = ac.getBean(ContractService.class);

        Map<Long,Contract> contracts = cs.findAll();

        System.out.println(contracts);
    }

}
