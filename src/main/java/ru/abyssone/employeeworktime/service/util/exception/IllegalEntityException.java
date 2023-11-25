package ru.abyssone.employeeworktime.service.util.exception;

public class IllegalEntityException extends IllegalArgumentException{

    public IllegalEntityException(String s) {
        super(s);
    }
    public IllegalEntityException(String clazz, String msg) {
        super(String.format("Exception in %s: %s", clazz, msg));
    }
}
