package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.FullContractInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.mapper.ContractMapper;
import ru.abyssone.employeeworktime.repository.ContractRepository;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public void save(FullContractInfo contractInfo) {
        Contract contract = contractMapper.toContract(contractInfo);
        contractRepository.save(contract);
    }
}
