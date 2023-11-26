<#import "../lib/components.ftlh" as components/>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Главная"/>
<body>
<@components.header />
<div class="container">
    <@components.title text="Список сотрудников" />

    <div class="row">
        <div class="col-3">
            <div class="form-floating col-12">
                <input type="text" class="form-control" id="search-input"><br />
                <label for="name-input" class="form-label">Поиск</label>
            </div>
        </div>

        <div class="col-3">
            <div class="form-floating col-12">
                <select type="text" class="form-select" id="sort-input">
                    <option value="NAME">Имени</option>
                    <option value="POSITION">Должности</option>
                    <option value="CONTRACT_CONCLUSION">Дате создания договора</option>
                    <option value="CONTRACT_EXPIRATION">Дате завершения договора</option>
                </select>
                <label for="name-input" class="form-label">Сортировать по</label>
            </div>
        </div>
    </div>

    <div class="row mb-5">
        <div class="col-1">
            <button class="btn btn-dark" id="submit-btn">Найти</button>
        </div>
    </div>

<table class="table">
    <thead>
        <tr>
            <th>Имя</th>
            <th>Должность</th>
            <th>Договор заключен</th>
            <th>Статус договора</th>
        </tr>
    </thead>
    <tbody id="table-body"></tbody>
</table>
</div>

<script type="text/javascript" src="/js/EmployeesSearchAndFilter.js"></script>
<@components.bootstrapScript />
</body>

</html>