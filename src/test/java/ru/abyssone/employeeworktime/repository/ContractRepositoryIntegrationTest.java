package ru.abyssone.employeeworktime.repository;

import org.junit.jupiter.api.Order;
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
import ru.abyssone.employeeworktime.entity.timemodel.WorkTimeModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Order(1)
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
    @Order(2)
    @DisplayName("Добавление графика фиксированной недели")
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

        String title = "testSchedule";
        fixedWorkWeek.setTitle(title);

        Contract contract = new Contract();
        UUID contractId = contract.getId();

        contract.setWorkTimeModel(fixedWorkWeek);

        contractRepository.save(contract);

        WorkTimeModel workTimeModelFromDB = contractRepository.findByIdWorkTimeModel(contractId);

        assertNotNull(workTimeModelFromDB);
        assertEquals(FixedWorkWeek.class, workTimeModelFromDB.getClass());
        assertEquals(title, workTimeModelFromDB.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("Добавление рабочего дня вне графика")
    public void createExceptionalDay() {
        Contract contract = new Contract();
        UUID contractId = contract.getId();

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

        assertEquals(2, contractList.size());
        List<Contract> contracts = contractList.stream().filter(c -> c.getId().equals(contractId)).toList();

        assertEquals(1, contracts.size());

        /*
            в мапе contract.exDays по ключу exDay.date должет быть exDay
         */
        assertNotNull(contracts.get(0).getExceptionalDays().get(exceptionalDay.getDate()));

        ExceptionalDay exDayFromDb = contracts.get(0).getExceptionalDays().get(exceptionalDay.getDate());
        assertEquals(exceptionalDay.getInfo(), exDayFromDb.getInfo());
        Assertions.assertEquals(exceptionalDay.getWorkTime(), exDayFromDb.getWorkTime());
    }
}