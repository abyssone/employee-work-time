<!DOCTYPE html>

<html lang="ru">
<head>

    <meta charset="UTF-8">

    <title> Пример простой страницы html</title>
</head>

<body>

<h3>Список сотрудников</h3>

<#list employees as employee>
    <div>
        <p>${employee.id}</p>
        <a href="employee/${employee.id}">
            <p>${employee.name}</p>
        </a>
        <#list employee.contracts as contract>
            <p>${contract.id}</p>
            <p>${contract.position}</p>
            <p>${contract.dateOfConclusion}</p>
            <#switch contract.state.name()>
                <#case "EXPIRED">
                    <p>Истек ${contract.expirationDate.get()}</p>
                <#break>
                <#case "ACTIVE">
                    <p>Активен до ${contract.expirationDate.get()}</p>
                <#break>
                <#case "UNLIMITED">
                    <p>Бессрочный</p>
                <#break>
                <#default>
                    <p>Неизвестно</p>
                <#break>
            </#switch>
        </#list>
    </div>
</#list>

</body>

</html>