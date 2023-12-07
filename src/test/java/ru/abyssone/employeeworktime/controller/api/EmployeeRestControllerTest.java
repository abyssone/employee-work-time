package ru.abyssone.employeeworktime.controller.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.abyssone.employeeworktime.dto.employee.GeneralEmployeeInfo;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeRestController.class)
class EmployeeRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService service;

    @Test
    @DisplayName("Запрос всех. Без поиска и сортировки")
    void findAllWithoutSearchAndSort_shouldReturnAll() throws Exception {
        List<GeneralEmployeeInfo> employeeInfo = getEmployeeInfo();
        Mockito.when(service.getFilteredAndSortedEmployeesInfo(null, null))
                .thenReturn(employeeInfo);


        ResultActions result = mvc.perform(get("/api/employee/all-info"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employeeInfo.size())))
                .andExpect(jsonPath("$[0].name", is(employeeInfo.get(0).getName())))
                .andExpect(jsonPath("$[0].id", is(employeeInfo.get(0).getId().toString())));
    }

    @Test
    @DisplayName("Запрос всех. С пустым поиском и сортировкой")
    void findAllWithEmptySearchAndSort_shouldReturnAll() throws Exception {
        List<GeneralEmployeeInfo> employeeInfo = getEmployeeInfo();
        Mockito.when(service.getFilteredAndSortedEmployeesInfo("", ""))
                .thenReturn(employeeInfo);


        ResultActions result = mvc.perform(get("/api/employee/all-info?search=&sort="));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(employeeInfo.size())))
                .andExpect(jsonPath("$[2].name", is(employeeInfo.get(2).getName())))
                .andExpect(jsonPath("$[1].id", is(employeeInfo.get(1).getId().toString())));
    }

    @Test
    @DisplayName("Поиск по имени")
    void findAllWithSearch_shouldReturnRightEmployees() throws Exception {
        List<GeneralEmployeeInfo> employeeInfo = getEmployeeInfo();

        String search = employeeInfo.get(1).getName();

        Mockito.when(service.getFilteredAndSortedEmployeesInfo(employeeInfo.get(1).getName(), ""))
                .thenReturn(employeeInfo.stream().filter(e -> e.getName().equals(search)).toList());

        ResultActions result = mvc.perform(get(String.format(
                "/api/employee/all-info?search=%s&sort=", search
        )));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(employeeInfo.get(1).getName())))
                .andExpect(jsonPath("$[0].id", is(employeeInfo.get(1).getId().toString())));
    }

    private List<GeneralEmployeeInfo> getEmployeeInfo() {
        return List.of(
          new GeneralEmployeeInfo()
                  .setId(UUID.randomUUID())
                  .setName("Олег Дмитриев")
                  .setSex(Employee.Sex.MALE.name())
                  .setBirthDate(LocalDate.parse("1990-11-11"))
                  .setAddress("address"),
            new GeneralEmployeeInfo()
                    .setId(UUID.randomUUID())
                    .setName("Максим Максимов")
                    .setSex(Employee.Sex.MALE.name())
                    .setBirthDate(LocalDate.parse("1990-11-11"))
                    .setAddress("address"),
            new GeneralEmployeeInfo()
                    .setId(UUID.randomUUID())
                    .setName("Вадим Вадимов")
                    .setSex(Employee.Sex.MALE.name())
                    .setBirthDate(LocalDate.parse("1990-11-11"))
                    .setAddress("address")
        );
    }

}