<#import "./lib/components.ftlh" as components/>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Главная"/>
<body>
<div class="container">
<h3>Список сотрудников</h3>

<table class="table">
    <thead>
        <tr>
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
                    <a href="employee/${employee.id}">${employee.name}</a>
                </td>
                <#if employee.contract.isPresent()>
                    <td>${employee.contract.get().position}</td>
                    <td>${employee.contract.get().dateOfConclusion}</td>
                    <td><#switch employee.contract.get().state.name()>
                        <#case "EXPIRED">
                            Истек ${employee.contract.get().expirationDate.get()}
                            <#break>
                        <#case "ACTIVE">
                            Активен до ${employee.contract.get().expirationDate.get()}
                            <#break>
                        <#case "UNLIMITED">
                            Бессрочный
                            <#break>
                        <#default>
                            Неизвестно
                            <#break>
                    </#switch></td>
                    <#else>
                        <td>-----</td>
                        <td>-----</td>
                        <td>-----</td>
                </#if>
            </tr>
        </#list>
    </tbody>
</table>
</div>
<@components.bootstrapScript />
</body>

</html>