<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>
<html lang="ru">
<@components.head title="Error"/>

<body>
<div class="container">
    <h3>Что-то пошло не так...</h3>
    <p> <span class="fw-bolder">URL: </span>${url}</p>
    <p> <span class="fw-bolder">EXCEPTION: </span>${exception}</p>
    <p> <span class="fw-bolder">MESSAGE: </span>${message}</p>
</div>
<@components.bootstrapScript />
</body>
</html>