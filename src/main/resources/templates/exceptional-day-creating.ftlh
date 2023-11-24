<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Изменение рабочего дня" />

<body>
<div class="container">
    <h3>Изменение рабочего дня</h3>
    <form action="/exceptionalday/create" method="post">

        <div class="row justify-content-between">
            <div class="form-floating col-3">
                <input type="date" name="date" class="form-control" id="date-input"><br/>
                <label for="date-input" class="form-label">Дата</label>
            </div>

            <div class="col">
                <div class="row justify-content-end">
                    <div class="form-floating col-3">
                        <input type="time" name="startTime" class="form-control" id="startTime-input"><br/>
                        <label for="startTime-input" class="form-label">Начало рабочего дня</label>
                    </div>

                    <div class="form-floating col-3">
                        <input type="time" name="endTime" class="form-control" id="endTime-input"><br/>
                        <label for="endTime-input" class="form-label">Конец рабочего дня</label>
                    </div>
                </div>

            </div>

        </div>


        <div class="form-floating">
            <input type="text" name="info" class="form-control" id="info-input"><br/>
            <label for="info-input" class="form-label">Дополнительная информация</label>
        </div>
        <div class="row justify-content-end">
            <input type="submit" class="btn btn-dark col-1" value="Добавить">
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
                        <input class="form-check-input" name="contracts[]" type="checkbox" value="${employee.contract.get().id}">
                    </td>
                    <td>
                        <a href="employee/${employee.id}">${employee.name}</a>
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