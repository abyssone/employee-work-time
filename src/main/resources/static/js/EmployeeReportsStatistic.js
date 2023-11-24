let btn = document.getElementById("fetch-btn");
let contractId = document.querySelector('[data-contractId]').getAttribute('data-contractId');
let startDateInput = document.getElementById("input-startDate");
let endDateInput = document.getElementById("input-endDate");
let tbody = document.getElementById("statistic-tbody");

let totalMissed = document.getElementById("total-missed");
let totalOvertime = document.getElementById("total-overtime");
let missedNoReason = document.getElementById("missed-no-reason");
let missedSickLeave = document.getElementById("missed-sick-leave");
let missedBusinessTrip = document.getElementById("missed-business-trip");

console.log(contractId);

btn.addEventListener("click", () => fetchFunc());

function fetchFunc() {
    if (contractId != null) {
        fetch(`/api/statistic?contractId=${contractId}&startDate=${startDateInput.value}&endDate=${endDateInput.value}`)
            .then(json => json.json())
            .then(data => updateTable(data))
            .catch(e => console.log(e));
    }
}

function updateTable(data) {
    let first = tbody.firstElementChild;
    while (first) {
        first.remove();
        first = tbody.firstElementChild;
    }

    totalMissed.textContent = data.totalMissed;
    totalOvertime.textContent = data.totalOvertime;
    missedNoReason.textContent = data.missedForReason.NO_REASON;
    missedSickLeave.textContent = data.missedForReason.SICK_LEAVE;
    missedBusinessTrip.textContent = data.missedForReason.BUSINESS_TRIP;

    data.reports.forEach(report => {
       let row = oneLineTag("tr");

       let date = oneLineTag("td", { textContent : toDDMMYYYYFormat(report.date)});

       let scheduledTime = document.createElement("td");
       if (report.scheduledTime && report.scheduledTime.startTime !== report.scheduledTime.endTime) {
           scheduledTime.textContent = `${report.scheduledTime.startTime} – ${report.scheduledTime?.endTime}`;
       } else {
           scheduledTime.textContent = 'Выходной';
       }

        let actualWorkTime = document.createElement("td");
        if (report.actualWorkedTime) {
            actualWorkTime.textContent = `${report.actualWorkedTime.startTime} – ${report.actualWorkedTime.endTime}`;
        } else {
            actualWorkTime.textContent = '-----';
        }

       let missedTime = oneLineTag("td", { textContent : report.missedTime});
       let overtime = oneLineTag("td", { textContent : report.overtime});
       let absenceReason = oneLineTag("td", { textContent : report.absenceReason});
       row.appendChild(date);
       row.appendChild(scheduledTime);
       row.appendChild(actualWorkTime);
       row.appendChild(missedTime);
       row.appendChild(overtime);
       row.appendChild(absenceReason);
       tbody.appendChild(row);
    });
}

function toDDMMYYYYFormat(date) {
    return date.toString().split('-').reverse().join('.');
}

function oneLineTag(tag,options){
    return Object.assign(document.createElement(tag),options);
}