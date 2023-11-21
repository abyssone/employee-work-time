<#macro head title>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    </head>
</#macro>

<#macro bootstrapScript>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</#macro>

<#macro contractStatus state expirationDate>
    <#switch state.name()>
        <#case "EXPIRED">
            Истек ${expirationDate.get()}
            <#break>
        <#case "ACTIVE">
            Активен до ${expirationDate.get()}
            <#break>
        <#case "UNLIMITED">
            Бессрочный
            <#break>
        <#default>
            Неизвестно
            <#break>
    </#switch>
</#macro>