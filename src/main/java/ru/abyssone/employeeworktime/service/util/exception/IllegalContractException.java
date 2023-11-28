package ru.abyssone.employeeworktime.service.util.exception;

import ru.abyssone.employeeworktime.entity.Contract;

public class IllegalContractException extends IllegalEntityException {
    public IllegalContractException(String msg) {
        super(Contract.class.toString(), msg);
    }
}
