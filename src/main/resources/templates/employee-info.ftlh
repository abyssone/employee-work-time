<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="${employee.name}" />
<body data-contractId="
    <#if employee.contract.isPresent()>
        ${employee.contract.get().id}
    <#else >
        null
    </#if>
">

<div class="container">
    <h3>Информация о сотруднике</h3>
    <p>Имя: ${employee.name}</p>
    <#if employee.contract.isPresent()>
        <p>Должность: ${employee.contract.get().position}</p>
        <p>Дата заключения договора: ${employee.contract.get().dateOfConclusion}</p>
        <p>Статус: <@components.contractStatus
            state=employee.contract.get().state
            expirationDate=employee.contract.get().expirationDate/></p>
        <p>График работы: ${employee.schedule.get().title}</p>
        <p>Тип графика: ${employee.schedule.get().type}</p>
    <#else >
        <p>Нет контракта</p>
    </#if>
    <a href="/contract/create?employee_id=${employee.id}" class="btn btn-dark">
        Добавить контракт
    </a>

    <div class="row">
        Статистика за:
        <input type="date" id="input-startDate" class="col-1"> -
        <input type="date" id="input-endDate" class="col-1">
        <button id="fetch-btn" class="btn btn-dark col-1">Fetch</button>
    </div>
    <div class="row">
        <table class="table">
            <thead>
                <tr>
                    <th>Дата</th>
                    <th>По графику</th>
                    <th>Фактически</th>
                    <th>Отсутствовал</th>
                    <th>Сверхурочно</th>
                    <th>Причина</th>
                </tr>
            </thead>
            <tbody id="statistic-tbody"></tbody>
        </table>
    </div>
</div>


<script type="text/javascript" src="/js/EmployeeReportsStatistic.js"></script>
</body>
</html>