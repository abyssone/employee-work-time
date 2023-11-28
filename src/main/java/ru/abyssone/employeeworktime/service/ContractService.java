package ru.abyssone.employeeworktime.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abyssone.employeeworktime.dto.contract.FullContractInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.mapper.ContractMapper;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;
import ru.abyssone.employeeworktime.service.util.Validator;
import ru.abyssone.employeeworktime.service.util.exception.IllegalContractException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEmployeeException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final WorkTimeModelRepository workTimeModelRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractMapper contractMapper;
    private final Validator validator;

    public void save(FullContractInfo contractInfo) {
        Contract contract = contractMapper.toContract(contractInfo);

        try {
            validator.check(contract);
        } catch (IllegalContractException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        contract.setWorkTimeModel(workTimeModelRepository.findById(contractInfo.getScheduleId()).get());
        contract.setEmployee(employeeRepository.findById(contractInfo.getEmployeeId()).get());

        contractRepository.save(contract);
    }

    public FullContractInfo getFullContractInfo(UUID id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isEmpty()) throw new NullPointerException(
                String.format("Contract with id: %s not found", id)
        );

        FullContractInfo fullContractInfo = contractMapper.toFullContractInfo(contract.get());

        return fullContractInfo;
    }

    @Transactional
    public void update(FullContractInfo contractInfo) {
        Contract contract = contractMapper.toContract(contractInfo);

        try {
            validator.check(contract);
        } catch (IllegalContractException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        contract.setWorkTimeModel(workTimeModelRepository.findById(contractInfo.getScheduleId()).get());

        contractRepository.updateIgnoreEmployee(contract);
    }
}
