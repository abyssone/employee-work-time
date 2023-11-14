<#import "./lib/general-macros.ftlh" as mcro>

<!DOCTYPE html>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${employee.name}</title>
</head>
<body>
<@mcro.header />
Информация о сотруднике:
    <p>${employee.id}</p>
    <p>${employee.name}</p>
    <a href="/contract/create?employee_id=${employee.id}">
        Добавить контракт
    </a>
</body>
</html>