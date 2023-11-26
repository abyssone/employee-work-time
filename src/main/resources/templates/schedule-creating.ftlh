<#import "./lib/components.ftlh" as components>

<!DOCTYPE html>

<html lang="ru">
<@components.head title="Добавление графика" />
<body>
<@components.header />
<div class="container mb-5">
    <div class="row">
        <div class="col-12">
            <@components.title text="Добавление графика" />
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-12 d-flex justify-content-center">
            <div style="max-width: 800px">
                <!------------------------------------------------------------------>

                <form method="post" action="/schedule/create" class="row">

                    <div class="row mb-3">
                        <div class="col-12">
                            <input type="radio" id="scheduleType1" name="type" value="FIXED_WORK_WEEK" checked>
                            <label for="scheduleType1">Фиксированная неделя</label>

                            <input type="radio" id="scheduleType2" name="type" value="SHIFT_WORK_SCHEDULE">
                            <label for="scheduleType2">Сменный график</label>
                        </div>
                    </div>

                    <div class="col-12 mb-3">
                        <div class="form-floating col-12">
                            <input type="text" name="title" class="form-control" id="title-input">
                            <label for="title-input" class="form-label">Название</label>
                        </div>
                    </div>

                    <div class="row mb-3" id="shift-work-schedule-block">
                        <p class="col-12 fw-bolder">Сменный график</p>
                        <div class="row mb-3">
                            <div class="col-6">

                                <div class="form-floating col-12">
                                    <input type="date" name="startWorkSchedule" class="form-control" id="startWorkSchedule-input">
                                    <label for="startWorkSchedule-input" class="form-label">Начало графика</label>
                                </div>

                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-6">
                                <div class="col-12">

                                    <div class="form-floating col-12">
                                        <input type="number" name="workDaysNumber" class="form-control" id="workDaysNumber-input">
                                        <label for="workDaysNumber-input" class="form-label">Рабочих дней</label>
                                    </div>

                                </div>
                            </div>

                            <div class="col-6">
                                <div class="col-12">

                                    <div class="form-floating col-12">
                                        <input type="number" name="daysOffNumber" class="form-control" id="daysOffNumber-input">
                                        <label for="daysOffNumber-input" class="form-label">Выходных дней</label>
                                    </div>

                                </div>
                            </div>

                        </div>
                        <div class="row mb-3">

                            <div class="col-6">
                                <div class="col-12">

                                    <div class="form-floating col-12">
                                        <input type="time" name="workHours.startTime" class="form-control" id="workHours.startTime-input">
                                        <label for="workHours.startTime-input" class="form-label">Начало рабочего дня</label>
                                    </div>

                                </div>
                            </div>

                            <div class="col-6">
                                <div class="col-12">

                                    <div class="form-floating col-12">
                                        <input type="time" name="workHours.endTime" class="form-control" id="workHours.endTime-input">
                                        <label for="workHours.endTime-input" class="form-label">Конец рабочего дня</label>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <#macro dayOfWeek_timePeriod title dayOfWeek>
                        <div class="col-6">

                            <div class="row">
                                <div class="col-12">${title}</div>
                                <div class="col-6">

                                    <div class="form-floating col-12">
                                        <input type="time" name="workWeekHours[${dayOfWeek}].startTime" class="form-control" id="${dayOfWeek}-start-input">
                                        <label for="${dayOfWeek}-start-input" class="form-label">Начало</label>
                                    </div>

                                </div>
                                <div class="col-6">

                                    <div class="form-floating col-12">
                                        <input type="time" name="workWeekHours[${dayOfWeek}].endTime" class="form-control" id="${dayOfWeek}-end-input">
                                        <label for="${dayOfWeek}-end-input" class="form-label">Конец</label>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </#macro>

                    <div class="row mb-3" id="fixed-work-week-block">
                        <p class="col-12 fw-bolder">Фиксированная неделя</p>
                        <div class="row">
                            <@dayOfWeek_timePeriod title="Понедельник" dayOfWeek="MONDAY" />
                            <@dayOfWeek_timePeriod title="Вторник" dayOfWeek="TUESDAY" />
                            <@dayOfWeek_timePeriod title="Среда" dayOfWeek="WEDNESDAY" />
                            <@dayOfWeek_timePeriod title="Четверг" dayOfWeek="THURSDAY" />
                            <@dayOfWeek_timePeriod title="Пятница" dayOfWeek="FRIDAY" />
                            <@dayOfWeek_timePeriod title="Суббота" dayOfWeek="SATURDAY" />
                            <@dayOfWeek_timePeriod title="Воскресенье" dayOfWeek="SUNDAY" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-2">
                            <input type="submit" value="Добавить" class="btn btn-dark col-12">
                        </div>
                    </div>

                </form>

                <!------------------------------------------------------------------>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/ScheduleTypeToggle.js"></script>
<@components.bootstrapScript />
</body>
</html>