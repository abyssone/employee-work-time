package ru.abyssone.employeeworktime.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ru.abyssone.employeeworktime.entity.Employee;
import ru.abyssone.employeeworktime.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository{

    private final Map<EmployeeService.SortField, Function<Root, Expression>> expression = new HashMap<>() {{
        put(EmployeeService.SortField.NAME, root -> root.get("name"));
        put(EmployeeService.SortField.POSITION, root -> root.get("contract").get("position"));
        put(EmployeeService.SortField.CONTRACT_CONCLUSION, root -> root.get("contract").get("dateOfConclusion"));
        put(EmployeeService.SortField.CONTRACT_EXPIRATION, root -> root.get("contract").get("expirationDate"));
    }};

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Employee> findAllFilteredAndSorted(String search, EmployeeService.SortField sort) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        root.fetch("contract", JoinType.LEFT);

        Predicate searchPredicate = criteriaBuilder.like(root.get("name"), search + "%");

        query.select(root).where(searchPredicate);
        query.orderBy(criteriaBuilder.asc(expression.get(sort).apply(root)));

        return entityManager.createQuery(query).getResultList();
    }
}
