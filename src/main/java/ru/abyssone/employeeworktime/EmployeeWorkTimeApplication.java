package ru.abyssone.employeeworktime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;

@SpringBootApplication
public class EmployeeWorkTimeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EmployeeWorkTimeApplication.class, args);

        ContractRepository cr = context.getBean(ContractRepository.class);
        EmployeeRepository er = context.getBean(EmployeeRepository.class);

        Contract c = new Contract();
        c.setPosition("developer");

        Employee e = new Employee();
        e.setName("name");
        e.addContract(c);

        er.save(e);
        System.out.println();


    }

}
