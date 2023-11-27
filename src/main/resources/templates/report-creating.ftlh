<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Добавление отчета" />

<body>
<@components.header />

<div class="container">
    <@components.title text="Добавление отчета" />

    <form action="/contract/${contractId}/report/create" method="post" class="d-flex justify-content-center">
        <div class="row" style="max-width: 800px;">
            <div class="row mb-2">
                <div class="col-md-4">
                    <p class="fw-bolder">Дата отчета</p>
                    <div class="row">
                        <div class="form-floating">
                            <input type="date" name="date" class="form-control" id="date-input" value="${.now?date?iso_utc}"><br/>
                            <label for="date-input" class="form-label">Дата</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-8">
                    <p class="fw-bolder">Время работы</p>
                        <div class="row">
                            <div class="form-floating col-md-6">
                                <input type="time" name="startTime" class="col-6 form-control" id="startTime-input">
                                <label for="startTime-input" class="form-label">С</label>
                            </div>

                            <div class="form-floating col-md-6">
                                <input type="time" name="endTime" class="col-6 form-control" id="endTime-input">
                                <label for="endTime-input" class="form-label">До</label>
                            </div>
                        </div>
                </div>
            </div>

            <div class="row mb-2">
                <div class="form-floating col-md-6">
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
            </div>

            <input type="submit" value="Добавить" class="btn btn-dark col-md-auto">
        </div>
    </form>
</div>
<@components.bootstrapScript />
</body>
</html>