package ru.abyssone.employeeworktime.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @GetMapping("/im")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

}
