package ru.abyssone.employeeworktime.repository;

import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import ru.abyssone.employeeworktime.entity.embedded.TimePeriod;
import ru.abyssone.employeeworktime.entity.timemodel.FixedWorkWeek;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
/*
    Отключение оборачивания тестирующих методов в транзакции для наблюдения
    изменений в бд при пошаговой отладке.
 */
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
        locations = "classpath:integration-tests.properties")
class ContractRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ExceptionalDayRepository exceptionalDayRepository;

    @Test
    public void createEmployee() {
        Employee e1 = new Employee();
        e1.setName("name");

        Employee e2 = new Employee();
        e2.setName("name2");

        employeeRepository.save(e1);
        employeeRepository.save(e2);

        List<Employee> employees = employeeRepository.findAll();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(e1));
        assertTrue(employees.contains(e2));
    }

    @Test
    public void createFixedWorkWeek() {
        FixedWorkWeek fixedWorkWeek = new FixedWorkWeek();

        Map<DayOfWeek, TimePeriod> workHours = new LinkedHashMap<>();
        TimePeriod workDay = new TimePeriod();
        workDay.setStartTime(LocalTime.parse("08:00:00"));
        workDay.setEndTime(LocalTime.parse("17:00:00"));

        workHours.put(DayOfWeek.MONDAY, workDay);
        workHours.put(DayOfWeek.TUESDAY, workDay);
        workHours.put(DayOfWeek.WEDNESDAY, workDay);
        workHours.put(DayOfWeek.THURSDAY, workDay);
        workHours.put(DayOfWeek.FRIDAY, workDay);
        workHours.put(DayOfWeek.SATURDAY, null);
        workHours.put(DayOfWeek.SUNDAY, null);

        fixedWorkWeek.setWorkHours(workHours);

        Contract contract = new Contract();
        contract.setWorkTimeModel(fixedWorkWeek);

        contractRepository.save(contract);
    }

    @Test
    @DisplayName("добавление рабочего дня")
    public void createExctptionalDay() {
        Contract contract = new Contract();

        TimePeriod timePeriod = new TimePeriod();
        timePeriod.setStartTime(LocalTime.parse("08:00:00"));
        timePeriod.setEndTime(LocalTime.parse("17:00:00"));

        ExceptionalDay exceptionalDay = new ExceptionalDay();
        exceptionalDay.setDate(LocalDate.parse("2000-01-01"));
        exceptionalDay.setInfo("рабочий день");
        exceptionalDay.setWorkTime(timePeriod);
        exceptionalDay.addContract(contract);

        exceptionalDayRepository.save(exceptionalDay);

        List<Contract> contractList = contractRepository.findAllFetchExceptionalDays();

        /*
            В exceptional day был добавлен только один contract
         */
        assertEquals(1, contractList.size());

        Contract contractFromDb = contractList.get(0);

        /*
            в мапе contract.exDays по ключу exDay.date должет быть exDay
         */
        assertNotNull(contractFromDb.getExceptionalDays().get(exceptionalDay.getDate()));

        ExceptionalDay exDayFromDb = contractFromDb.getExceptionalDays().get(exceptionalDay.getDate());
        assertEquals(exceptionalDay.getInfo(), exDayFromDb.getInfo());
        Assertions.assertEquals(exceptionalDay.getWorkTime(), exDayFromDb.getWorkTime());
    }
}