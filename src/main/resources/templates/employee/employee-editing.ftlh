<#import "../lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Редактирование сотрудника"/>

<body
    data-employeeId=
    <#if employee.contract.isPresent()>
        "${employee.id}"
    <#else >
        ""
    </#if>
>
<@components.header />
<div class="container">
    <@components.title text="Редактирование сотрудника" />

    <form action="/employee/${employee.id}/edit" method="post">

        <div class="form-floating">
            <input type="text" name="name" class="form-control" id="name-input" value="${employee.name}"><br/>
            <label for="name-input" class="form-label">Имя</label>
        </div>

        <div class="form-floating">
            <input type="text" name="address" class="form-control" id="address-input" value="${employee.address}"><br/>
            <label for="address-input" class="form-label">Адрес</label>
        </div>

        <div class="row">
            <div class="form-floating col-3">
                <input type="date" name="birthDate" class="form-control" id="birthDate-input" value="${employee.birthDate}"><br/>
                <label for="birthDate-input" class="form-label">Дата рождения</label>
            </div>

            <div class="col-2">
                Пол
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sex" id="sex-radio-male" value="MALE"
                        <#if employee.sex == "MALE">CHECKED</#if>>
                    <label class="form-check-label" for="sex-radio-male">
                        Мужской
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sex" id="ex-radio-female" value="FEMALE"
                           <#if employee.sex == "FEMALE">CHECKED</#if>>
                    <label class="form-check-label" for="ex-radio-female">
                        Женский
                    </label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-2">
                <input type="submit" class="btn btn-dark col-12" value="Изменить">
            </div>

            <div class="col-2">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-dark col-12" data-bs-toggle="modal" data-bs-target="#exampleModal">
                    Удалить
                </button>

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Удаление</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                Вы действительно хотите удалить ${employee.name} из списка сотрудников?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                                <a href="/employee/${employee.id}/delete"></a>
                                <button type="button" class="btn btn-primary" id="delete-employee">Удалить</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>

<script type="text/javascript" src="/js/EmployeeDelete.js"></script>
<@components.bootstrapScript />
</body>
</html>