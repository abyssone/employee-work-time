package ru.abyssone.employeeworktime.service.util.exception;

import ru.abyssone.employeeworktime.entity.Employee;

public class IllegalEmployeeException extends IllegalEntityException {

    public IllegalEmployeeException(String msg) {
        super(Employee.class.toString(), msg);
    }
}
