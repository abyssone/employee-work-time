package ru.abyssone.employeeworktime.repository;

import org.springframework.stereotype.Repository;
import ru.abyssone.employeeworktime.entity.Contract;

@Repository
public interface FinalContractRepository extends CustomMapRepository, MapRepository<Contract>{
}
