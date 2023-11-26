<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>
<html lang="ru">
<@components.head title="Error"/>

<body>
<@components.header />
<div class="container">
    <@components.title text="Что-то пошло не по плану..." />

    <p> <span class="fw-bolder">URL: </span>${url}</p>
    <p> <span class="fw-bolder">EXCEPTION: </span>${exception}</p>
    <p> <span class="fw-bolder">MESSAGE: </span>${message}</p>
</div>
<@components.bootstrapScript />
</body>
</html>