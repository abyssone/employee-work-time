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
<@components.header />
<div class="container">
    <@components.title text="Информация о сотруднике" />

    <p>Имя: ${employee.name}</p>
    <#if employee.contract.isPresent()>
        <p>Должность: ${employee.contract.get().position}</p>
        <p>Дата заключения договора: ${employee.contract.get().dateOfConclusion}</p>
        <p>Статус: <@components.contractStatus
            state=employee.contract.get().state
            expirationDate=employee.contract.get().expirationDate/></p>
        <p>График работы: ${employee.schedule.get().title}</p>
        <p>Тип графика: <@components.scheduleName type=employee.schedule.get().type /></p>
    <#else >
        <p>Нет контракта</p>
    </#if>
    
    <div class="row">
        <div class="col-2">
            <a href="/employee/${employee.id}/edit" class="btn  btn-dark col-12">Изменить</a>
        </div>

        <#if employee.contract.isPresent()>
            <div class="col-2">
                <a role="button" class="btn btn-dark col-12" href="/contract/${employee.contract.get().getId()}/report/create">
                    Добавить отчет
                </a>
            </div>
        </#if>
        <hr class="mt-2">
    </div>

    <div class="row">

        <div class="col-12">
            <h3 class="text-center mb-5">Статистика</h3>
        </div>

        <div class="row mb-3">

            <div class="col-2">
                <div class="form-floating">
                    <input type="date" id="input-startDate" class="form-control">
                    <label for="input-startDate" class="form-label">С</label>
                </div>
            </div>

            <div class="col-2">
                <div class="form-floating">
                    <input type="date" id="input-endDate" class="form-control">
                    <label for="input-endDate" class="form-label">До</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-2"></div>
            <div class="col-2">
                <button id="fetch-btn" class="btn btn-dark col-12">Найти</button>
            </div>
        </div>

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