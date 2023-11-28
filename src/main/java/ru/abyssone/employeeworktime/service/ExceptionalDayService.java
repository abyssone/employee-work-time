package ru.abyssone.employeeworktime.service;

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

import java.util.List;
import java.util.Optional;
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
        // Т.к. с фронта приходит пустая строка, null необходимо присваивать вручную
        if (exceptionalDayInfo.getStartTime().isEmpty()) exceptionalDayInfo.setStartTime(null);
        if (exceptionalDayInfo.getEndTime().isEmpty()) exceptionalDayInfo.setEndTime(null);

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

    public List<ExceptionalDayInfo> getAllExceptionalDayInfo() {
        List<ExceptionalDay> exceptionalDays = exceptionalDayRepository.findAll();
        return exceptionalDays.stream().map(exceptionalDayMapper::toExceptionalDayInfo).toList();
    }

    public ExceptionalDayInfo getExceptionalDayInfoWithContracts(Long id) {
        Optional<ExceptionalDay> exceptionalDay = exceptionalDayRepository.findByIdFetchContracts(id);
        if (exceptionalDay.isEmpty()) throw new NullPointerException("not found ex day with id" + id);

        ExceptionalDayInfo exceptionalDayInfo = exceptionalDayMapper.toExceptionalDayInfo(exceptionalDay.get());
        Set<Contract> contracts = exceptionalDay.get().getContracts();
        if (!contracts.isEmpty()) {
            contracts.forEach(c -> exceptionalDayInfo.getContracts().add(c.getId()));
        }

        return exceptionalDayInfo;
    }
}
