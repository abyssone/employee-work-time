<!DOCTYPE html>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавление графика</title>
</head>
<body>
Добавление графика
<form method="post" action="/schedule/create">
    <input type="radio" id="scheduleType1" name="type" value="FIXED_WORK_WEEK" checked/>
    <label for="scheduleType1">Фиксированная неделя</label>

    <input type="radio" id="scheduleType2" name="type" value="SHIFT_WORK_SCHEDULE" />
    <label for="scheduleType2">Сменный график</label>

    <input type="text" name="title" placeholder="Название">

    <div> Сменный график:<br/>
        Начало графика: <input type="date" name="startWorkSchedule"/><br/>
        Кол-во рабочих дней: <input type="number" name="workDaysNumber"/><br/>
        Кол-во выходных: <input type="number" name="daysOffNumber"/><br/>
        Начало рабочего дня: <input type="time" name="workHours.startTime"/><br/>
        Конец рабочего дня: <input type="time" name="workHours.endTime"/>
    </div>

    <#macro dayOfWeek_timePeriod title dayOfWeek>
        ${title}: Начало <input type="time" name="workWeekHours[${dayOfWeek}].startTime"/>
                  Конец <input type="time" name="workWeekHours[${dayOfWeek}].endTime"/><br/>
    </#macro>

    <div> Фиксированная неделя:<br/>
        <@dayOfWeek_timePeriod title="Пн" dayOfWeek="MONDAY" />
        <@dayOfWeek_timePeriod title="Вт" dayOfWeek="TUESDAY" />
        <@dayOfWeek_timePeriod title="Ср" dayOfWeek="WEDNESDAY" />
        <@dayOfWeek_timePeriod title="Чт" dayOfWeek="THURSDAY" />
        <@dayOfWeek_timePeriod title="Пт" dayOfWeek="FRIDAY" />
        <@dayOfWeek_timePeriod title="Сб" dayOfWeek="SATURDAY" />
        <@dayOfWeek_timePeriod title="Вс" dayOfWeek="SUNDAY" />
    </div>

    <input type="submit" value="Добавить">
</form>
</body>
</html>