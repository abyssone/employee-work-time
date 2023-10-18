package ru.abyssone.employeeworktime.service;

import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.repository.FinalContractRepository;

import java.util.Map;

@Service
public class ContractService {
    private final FinalContractRepository contractRepository;

    public ContractService(FinalContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public void add(Contract contract) {
        this.contractRepository.save(contract);
    }

    public Map<Long,Contract> findAll() {
        Map<Long,Contract> contracts = this.contractRepository.getAllAsMap();
        return contracts;
    }
}
