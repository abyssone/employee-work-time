let searchInput = document.getElementById("search-input");
let sortInput = document.getElementById("sort-input");
let submitBtn = document.getElementById("submit-btn");
let tableBody = document.getElementById("table-body");

submitBtn.addEventListener("click", fetchFunc);
fetchFunc();
function fetchFunc() {
    fetch(`/api/employee/all-info?search=${searchInput.value}&sort=${sortInput.value}`)
        .then(json => json.json())
        .then(data => updateTable(data))
        .catch(e => console.log(e));
}

function updateTable(data) {

    // Очищение таблицы
    let first = tableBody.firstElementChild;
    while (first) {
        first.remove();
        first = tableBody.firstElementChild;
    }

    data.forEach(employee => {
        let columns = [];

        let link = oneLineTag("a", {href: `employee/${employee.id}`, textContent: employee.name});
        let linkTd = document.createElement("td");
        linkTd.appendChild(link);
        columns.push(linkTd);

        columns.push(oneLineTag("td", {
            textContent : employee.contract ? employee.contract.position : "-----"
        }))
        columns.push(oneLineTag("td", {
            textContent : employee.contract ? toDDMMYYYYFormat(employee.contract.dateOfConclusion) : "-----"
        }))

        columns.push(oneLineTag("td", {
            textContent : employee.contract
                ? parseStatus(employee.contract.state, employee.contract.expirationDate)
                : '-----'
        }));

        let row = oneLineTag("tr");
        columns.forEach(col => row.appendChild(col));
        tableBody.appendChild(row);
    })


}

function toDDMMYYYYFormat(date) {
    return date.toString().split('-').reverse().join('.');
}

function oneLineTag(tag,options){
    return Object.assign(document.createElement(tag),options);
}

function parseStatus(status, date) {
    switch (status) {
        case 'ACTIVE': return `Активен до ${toDDMMYYYYFormat(date)}`;
        case 'EXPIRED': return `Истек ${toDDMMYYYYFormat(date)}`;
        case 'UNLIMITED': return 'Бессрочный';
        default: return 'Неизвестно';
    }
}