<#macro head title>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            * {
                padding: 0;
            }
        </style>
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

<#macro scheduleName type>
    <#switch type>
        <#case "FIXED_WORK_WEEK">
            Фиксированная неделя
            <#break>
        <#case "SHIFT_WORK_SCHEDULE">
            Сменный график
            <#break>
        <#default>
            Неизвестно
            <#break>
    </#switch>
</#macro>

<#macro header>
    <header class="p-3 bg-dark text-white mb-4">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center">
<#--                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">-->
<#--                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>-->
<#--                </a>-->

                <ul class="nav col-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="/" class="nav-link px-2 text-warning">Главная</a></li>
                    <li><a href="/employee/create" class="nav-link px-2 text-white">Сотрудник</a></li>
                    <li><a href="/contract/create" class="nav-link px-2 text-white">Контракт</a></li>
                    <li><a href="/schedule/create" class="nav-link px-2 text-white">График</a></li>
                    <li><a href="/exceptionalday/create" class="nav-link px-2 text-white">ExDay</a></li>
                </ul>

<#--                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">-->
<#--                    <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search">-->
<#--                </form>-->

<#--                <div class="text-end">-->
<#--                    <button type="button" class="btn btn-outline-light me-2">Login</button>-->
<#--                    <button type="button" class="btn btn-warning">Красивая кнопка</button>-->
<#--                </div>-->
            </div>
        </div>
    </header>

</#macro>

<#macro title text>
    <h3 class="text-center mb-5">${text}</h3>
</#macro>