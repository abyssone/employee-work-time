<#import "../lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="${employee.name}" />
<body data-contractId=
    <#if employee.contract.isPresent()>
        "${employee.contract.get().id}"
    <#else >
        ""
    </#if>
>

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
    
    <div class="row">
        <a href="/employee/${employee.id}/edit" class="col-1 btn btn-dark">Изменить</a>

        <#if employee.contract.isPresent()>
            <a role="button" class="col-2 ms-1 btn btn-dark" href="/contract/${employee.contract.get().getId()}/report/create">Добавить отчет</a>
        </#if>
    </div>

    <div class="row">
        <p class="align-middle col-auto">Статистика за:</p>
        <input type="date" id="input-startDate" class="col-2">
        <p class="align-middle col-auto">–</p>
        <input type="date" id="input-endDate" class="col-2">
        <button id="fetch-btn" class="btn btn-dark col-1">Найти</button>
    </div>
    <div class="row">
        <p class="fw-bolder">ВСЕГО</p>
        <p>Отсутствовал:<span id="total-missed"></span> Сверхурочно:<span id="total-overtime"></span></p>
        <p class="fw-bolder">ПО ПРИЧИНЕ</p>
        <p>
            Не указано:<span id="missed-no-reason"></span>
            Больничный:<span id="missed-sick-leave"></span>
            Командировка:<span id="missed-business-trip"></span>
        </p>
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
<@components.bootstrapScript />
</body>
</html>