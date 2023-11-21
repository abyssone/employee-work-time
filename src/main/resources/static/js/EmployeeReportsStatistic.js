let btn = document.getElementById("fetch-btn");
let contractId = document.querySelector('[data-contractId]').getAttribute('data-contractId');
let startDateInput = document.getElementById("input-startDate");
let endDateInput = document.getElementById("input-endDate");
let tbody = document.getElementById("statistic-tbody");

console.log(contractId);

btn.addEventListener("click", () => fetchFunc());

function fetchFunc() {
    if (contractId != null && contractId != 'null') {
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

    data.forEach(report => {
       let row = oneLineTag("tr");
       let date = oneLineTag("td", { textContent : report.date});
       let scheduledTime = oneLineTag("td",
           { textContent : `${report.scheduledTime?.startTime} – ${report.scheduledTime?.endTime}`});
       let actualWorkTime = oneLineTag("td",
           { textContent : `${report.actualWorkTime?.startTime} – ${report.actualWorkTime?.endTime}`});
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

function oneLineTag(tag,options){
    return Object.assign(document.createElement(tag),options);
}