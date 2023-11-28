<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Изменение исключительного дня" />

<body>
<@components.header />
<div class="container">

    <@components.title text="Изменение исключительного дня" />

    <form action="/exceptionalday/${exceptionalDay.id}/edit" method="post" class="row d-flex justify-content-center">
        <div class="row mb-5" style="max-width: 800px;">
            <div class="row">
                <div class="form-floating col-md-4">
                    <input type="date" name="date" class="form-control" id="date-input" value="${exceptionalDay.date}"><br/>
                    <label for="date-input" class="form-label">Дата</label>
                </div>

                <div class="form-floating col-md-4">
                    <input type="time" name="startTime" class="form-control" id="startTime-input" value="${exceptionalDay.startTime!""}">
                    <label for="startTime-input" class="form-label">Начало рабочего дня</label>
                </div>

                <div class="form-floating col-md-4">
                    <input type="time" name="endTime" class="form-control" id="endTime-input" value="${exceptionalDay.endTime!""}">
                    <label for="endTime-input" class="form-label">Конец рабочего дня</label>
                </div>

                <div class="form-floating">
                    <input type="text" name="info" class="form-control" id="info-input" value="${exceptionalDay.info!""}"><br/>
                    <label for="info-input" class="form-label">Дополнительная информация</label>
                </div>
            </div>

            <input type="submit" class="btn btn-dark col-md-auto" value="Добавить">
        </div>
        <p class="fw-bolder">Сотрудники</p>

        <table class="table">
            <thead>
            <tr>
                <th> </th>
                <th>Имя</th>
                <th>Должность</th>
                <th>Договор заключен</th>
                <th>Статус договора</th>
            </tr>
            </thead>
            <tbody>
            <#list employees as employee>
                <tr>
                    <td>
                        <input class="form-check-input"
                               name="contracts[]"
                               type="checkbox"
                               value="${employee.contract.get().id}"
                               <#if (employee.contract.isPresent() && exceptionalDay.contracts?seq_contains(employee.contract.get().id))>
                                checked
                               </#if>
                    </td>
                    <td>
                        ${employee.name}
                    </td>
                    <#if employee.contract.isPresent()>
                        <td>${employee.contract.get().position}</td>
                        <td>${employee.contract.get().dateOfConclusion}</td>
                        <td>
                            <@components.contractStatus
                            state=employee.contract.get().state
                            expirationDate=employee.contract.get().expirationDate />
                        </td>
                    <#else>
                        <td>-----</td>
                        <td>-----</td>
                        <td>-----</td>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </form>
</div>
<@components.bootstrapScript />
</body>
</html>