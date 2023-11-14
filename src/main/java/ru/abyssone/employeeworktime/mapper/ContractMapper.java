package ru.abyssone.employeeworktime.mapper;

import org.mapstruct.Mapper;
import ru.abyssone.employeeworktime.dto.ContractState;
import ru.abyssone.employeeworktime.dto.GeneralContractInfo;
import ru.abyssone.employeeworktime.entity.Contract;

import java.time.LocalDate;
import java.util.Optional;

@Mapper
public abstract class ContractMapper {

    public GeneralContractInfo toGeneralContractInfo(Contract contract) {
        GeneralContractInfo contractStatus = new GeneralContractInfo();

        contractStatus.setId(contract.getId());
        contractStatus.setPosition(contract.getPosition());
        contractStatus.setDateOfConclusion(contract.getDateOfConclusion());
        LocalDate expirationDate = contract.getExpirationDate();

        contractStatus.setExpirationDate(Optional.of(expirationDate));

        if (expirationDate == null) {
            contractStatus.setState(ContractState.UNLIMITED);
        } else if (expirationDate.isBefore(LocalDate.now())) {
            contractStatus.setState(ContractState.EXPIRED);
        } else {
            contractStatus.setState(ContractState.ACTIVE);;
        }

        return contractStatus;
    }
}
