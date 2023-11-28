<#import "./lib/components.ftlh" as components/>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Список исключительных дней"/>
<body>
<@components.header />
<div class="container">
    <@components.title text="Список исключительных дней" />
    <div class="row mb-2">
        <div class="col-2">
            <a class="btn btn-dark col-12" href="/exceptionalday/create">Добавить</a>
        </div>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Рабочее время</th>
            <th>Описание</th>
        </tr>
        </thead>
        <tbody>
            <#list exceptionalDays as day>
                <tr>
                    <td>
                        <a href="/exceptionalday/${day.id}/edit">
                            ${day.date}
                        </a>
                    </td>
                    <td>
                        <#if day.endTime?? && day.startTime??>
                            ${day.startTime} – ${day.endTime}
                        <#else>
                            Выходной
                        </#if>
                    </td>
                    <td>
                        ${day.info}
                    </td>
            </#list>
        </tbody>
        <tbody id="table-body"></tbody>
    </table>
</div>

<@components.bootstrapScript />
</body>

</html>