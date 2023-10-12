package ru.abyssone.employeeworktime.exception;

public class IncorrectArgsNumber extends IllegalArgumentException {

    public IncorrectArgsNumber() {
        super();
    }

    public IncorrectArgsNumber(String s) {
        super(s);
    }

    public IncorrectArgsNumber(Integer expected, Integer received) {
        super(String.format("Expected %d args, but received %d", expected, received));
    }

}
