package ru.abyssone.employeeworktime.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContractController {

    @GetMapping("/contract/create")
    public String getContractCreating(@RequestParam("employee_id") UUID employee_id, Model model) {
        model.addAttribute("employee_id", employee_id);
        return "contract-creating";
    }
}
