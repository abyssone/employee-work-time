<!DOCTYPE html>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавление контракта</title>
</head>
<body>
Добавление контракта для ${employee_id}
<form method="post" action="/contract/create">
    <input type="text" name="name" placeholder="Имя"/>
    <input type="submit" value="Добавить"/>
</form>
</body>
</html>