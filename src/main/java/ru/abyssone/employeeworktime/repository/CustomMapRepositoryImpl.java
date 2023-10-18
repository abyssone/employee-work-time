package ru.abyssone.employeeworktime.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.abyssone.employeeworktime.entity.Contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomMapRepositoryImpl implements CustomMapRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Проблема в том как сделать обобщенный метод, отправляющий select в таблицу, привязанную к <T>
     * @return
     */
    @Override
    public Map getAllAsMap() {
        Map<Long, Contract> result = new HashMap<>();

        Query query = entityManager.createQuery("SELECT c FROM Contract c");
        List<Contract> entities = query.getResultList();

        for (Contract contract : entities) {
            result.put(contract.getId(), contract);
        }

        return result;
    }
}
