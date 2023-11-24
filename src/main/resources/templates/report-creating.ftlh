<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Добавление отчета" />

<body>

<div class="container">
    <h3>Добавление отчета</h3>
    <form action="/contract/${contractId}/report/create" method="post">
        <div class="form-floating col-3">
            <input type="date" name="date" class="form-control" id="date-input"><br/>
            <label for="date-input" class="form-label">Дата</label>
        </div>
        <p>Фактическое время работы</p>
        <div class="row">
            <p class="col-auto">С </p>
            <input type="time" name="startTime" class="form-control col-2">
            <p class="col-auto">До </p>
            <input type="time" name="endTime" class="form-control col-2">
        </div>

        <div class="form-floating">
            <select class="form-select" id="absenceReason-select" name="absenceReason">
                <option value="NO_REASON" selected>
                    Не указано
                </option>
                <option value="SICK_LEAVE">
                    Больничный
                </option>
                <option value="BUSINESS_TRIP">
                    Командировка
                </option>
            </select>
            <label for="absenceReason-select" class="form-label">Причина отсутствия</label>
        </div>

        <input type="submit" value="Добавить" class="btn btn-dark">
    </form>
</div>
<@components.bootstrapScript />
</body>
</html>