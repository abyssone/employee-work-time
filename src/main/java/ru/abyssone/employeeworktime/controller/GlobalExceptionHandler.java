package ru.abyssone.employeeworktime.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.abyssone.employeeworktime.service.util.exception.IllegalContractException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;
import ru.abyssone.employeeworktime.service.util.exception.IllegalWorkTimeReportInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            NullPointerException.class,
            IllegalWorkTimeReportInfo.class,
            IllegalContractException.class,
            IllegalEmployeeException.class,
            IllegalExceptionalDayInfo.class})
    public ModelAndView handleException(HttpServletRequest req, Exception exception) {
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("exception", exception.getClass());
        mav.addObject("url", req.getRequestURL());
        mav.addObject("message", exception.getMessage());
        return mav;
    }
}
