package ru.abyssone.employeeworktime.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.abyssone.employeeworktime.dto.ExceptionalDayInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.entity.ExceptionalDay;
import ru.abyssone.employeeworktime.mapper.ExceptionalDayMapper;
import ru.abyssone.employeeworktime.repository.ContractRepository;
import ru.abyssone.employeeworktime.repository.ExceptionalDayRepository;
import ru.abyssone.employeeworktime.service.util.Validator;
import ru.abyssone.employeeworktime.service.util.exception.IllegalEntityException;
import ru.abyssone.employeeworktime.service.util.exception.IllegalExceptionalDayInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExceptionalDayService {

    private final Validator validator;
    private final ContractRepository contractRepository;
    private final ExceptionalDayMapper exceptionalDayMapper;
    private final ExceptionalDayRepository exceptionalDayRepository;

    public void save(ExceptionalDayInfo exceptionalDayInfo) throws IllegalExceptionalDayInfo {
        try {
            validator.check(exceptionalDayInfo);
        } catch (IllegalEntityException e) {
            log.error(e.getMessage());
            throw e;
        }

        List<Contract> contracts = contractRepository.findByIdList(exceptionalDayInfo.getContracts());

        if (contracts.size() != exceptionalDayInfo.getContracts().size()) {
            List<String> notFoundContractId = exceptionalDayInfo.getContracts().stream()
                    .filter(contracts::contains)
                    .map(uuid -> uuid.toString())
                    .collect(Collectors.toList());
            String msg = String.format("Contracts with id: %s not found",
                    String.join(", ", notFoundContractId));

            log.error(msg);
            throw new IllegalExceptionalDayInfo(msg);
        }

        ExceptionalDay exceptionalDay = exceptionalDayMapper.toExceptionalDay(exceptionalDayInfo);
        contracts.stream().forEach(exceptionalDay::addContract);

        exceptionalDayRepository.save(exceptionalDay);
    }

    //todo: delete
    @PostConstruct
    private void test() {
        List<Contract> byIdList = contractRepository.findByIdList(Arrays.asList(
                UUID.fromString("6ff7681d-f77c-4577-b6e7-9827fd599d68"),
                UUID.fromString("6ff7681d-f77c-4577-b6e7-9827fd599d61"),
                UUID.fromString("b60ecc53-0521-4c35-b907-38a08deca381")
        ));

        System.out.println();
    }
}
