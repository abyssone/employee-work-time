<#import "../lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Добавление сотрудника"/>

<body>
<@components.header />
<div class="container">
    <@components.title text="Добавление сотрудника" />

    <form action="/employee/create" method="post">

        <div class="form-floating">
            <input type="text" name="name" class="form-control" id="name-input"><br/>
            <label for="name-input" class="form-label">Имя</label>
        </div>

        <div class="form-floating">
            <input type="text" name="address" class="form-control" id="address-input"><br/>
            <label for="address-input" class="form-label">Адрес</label>
        </div>

        <div class="row">
            <div class="form-floating col-3">
                <input type="date" name="birthDate" class="form-control" id="birthDate-input"><br/>
                <label for="birthDate-input" class="form-label">Дата рождения</label>
            </div>

            <div class="col-2">
                Пол
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sex" id="sex-radio-male" value="MALE" checked>
                    <label class="form-check-label" for="sex-radio-male">
                        Мужской
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sex" id="ex-radio-female" value="FEMALE">
                    <label class="form-check-label" for="ex-radio-female">
                        Женский
                    </label>
                </div>
            </div>
        </div>

        <input type="submit" class="btn btn-dark" value="Добавить">
    </form>
</div>

<@components.bootstrapScript />
</body>
</html>