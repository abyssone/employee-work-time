package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.abyssone.employeeworktime.dto.ContractState;
import ru.abyssone.employeeworktime.dto.FullContractInfo;
import ru.abyssone.employeeworktime.dto.GeneralContractInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;

import java.time.LocalDate;
import java.util.Optional;

@Mapper
public abstract class ContractMapper {

    @Autowired
    protected WorkTimeModelRepository workTimeModelRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    public GeneralContractInfo toGeneralContractInfo(Contract contract) {
        GeneralContractInfo contractStatus = new GeneralContractInfo();

        contractStatus.setId(contract.getId());
        contractStatus.setPosition(contract.getPosition());
        contractStatus.setDateOfConclusion(contract.getDateOfConclusion());
        Optional<LocalDate> expirationDate = contract.getExpirationDate();

        contractStatus.setExpirationDate(expirationDate);

        if (expirationDate.isEmpty()) {
            contractStatus.setState(ContractState.UNLIMITED);
        } else if (expirationDate.get().isBefore(LocalDate.now())) {
            contractStatus.setState(ContractState.EXPIRED);
        } else {
            contractStatus.setState(ContractState.ACTIVE);;
        }

        return contractStatus;
    }

    public Contract toContract(FullContractInfo contractInfo) {
        Contract contract = new Contract();

        contract.setDateOfConclusion(contractInfo.getDateOfConclusion());
        contract.setExpirationDate(contractInfo.getExpirationDate());
        contract.setPosition(contractInfo.getPosition());

        contract.setWorkTimeModel(workTimeModelRepository.findById(contractInfo.getScheduleId()).get());
        contract.setEmployee(employeeRepository.findById(contractInfo.getEmployeeId()).get());

        return contract;
    }
}
