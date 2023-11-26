<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>
<html lang="ru">
<@components.head title="Добавление контракта"/>

<body>
<@components.header />
<div class="container">
    <div class="d-grid gap-2">
        <@components.title text="Добавление контракта" />
        <form method="post" action="/contract/create" class="d-grid gap-2">
            <div class="form-floating">
                <select name="employeeId" class="form-select" id="employee-select">
                    <#list employees as employee>
                        <option value="${employee.id} ">${employee.name}
                            <#if employee.contract.isPresent()> (${employee.contract.get().position})</#if>
                        </option>
                    </#list>
                </select>
                <label for="employee-select" class="form-label">Сотрудник</label>
            </div>

            <div class="form-floating">
                <input type="text" class="form-control" id="position-input" name="position" />
                <label for="position-input" class="form-label">Должность</label>
            </div>

            <div class="form-floating">
                <select class="form-select" id="schedule-select" name="scheduleId">
                    <#list schedules as schedule>
                        <option value="${schedule.id}">
                            ${schedule.title} (<#switch schedule.type>
                                <#case "FIXED_WORK_WEEK">
                                    ФРН
                                <#break>
                                <#case "SHIFT_WORK_SCHEDULE">
                                    СГр
                                <#break>
                                <#default >
                                    Unknown
                                <#break>
                            </#switch>)
                        </option>
                    </#list>
                </select>
                <label for="schedule-select" class="form-label">График работы</label>
            </div>

            <div class="row">
                <div class="form-floating col-6">
                    <input type="date" name="dateOfConclusion" class="form-control" id="dateOfConclusion-input">
                    <label for="dateOfConclusion-input" class="form-label">Дата заключения контракта</label>
                </div>

                <div class="form-floating col-6">
                    <input type="date" name="expirationDate" class="form-control" id="expirationDate-input"><br/>
                    <label for="expirationDate-input" class="form-label">Дата окончания действия контракта (опционально)</label>
                </div>
            </div>

            <input type="submit" value="Добавить" class="btn btn-dark col-2"/>
        </form>
    </div>
</div>
<@components.bootstrapScript />
</body>
</html>