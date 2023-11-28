package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.abyssone.employeeworktime.dto.contract.ContractState;
import ru.abyssone.employeeworktime.dto.contract.FullContractInfo;
import ru.abyssone.employeeworktime.dto.contract.GeneralContractInfo;
import ru.abyssone.employeeworktime.entity.Contract;
import ru.abyssone.employeeworktime.repository.EmployeeRepository;
import ru.abyssone.employeeworktime.repository.WorkTimeModelRepository;

import java.time.LocalDate;
import java.util.Optional;

@Mapper
public abstract class ContractMapper {

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

        if (contractInfo.getId() != null) contract.setId(contractInfo.getId());
        contract.setDateOfConclusion(contractInfo.getDateOfConclusion());
        contract.setExpirationDate(contractInfo.getExpirationDate());
        contract.setPosition(contractInfo.getPosition());

        return contract;
    }

    public FullContractInfo toFullContractInfo(Contract contract) {
        FullContractInfo fullContractInfo = new FullContractInfo();

        fullContractInfo.setId(contract.getId());
        fullContractInfo.setPosition(contract.getPosition());
        fullContractInfo.setScheduleId(contract.getWorkTimeModel().getId());
        fullContractInfo.setDateOfConclusion(contract.getDateOfConclusion());
        fullContractInfo.setExpirationDate(contract.getExpirationDate().orElse(null));

        return fullContractInfo;
    }
}
