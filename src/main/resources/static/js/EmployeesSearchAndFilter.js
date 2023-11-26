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
        columns.push(oneLineTag("td", { textContent : employee.name}))
        columns.push(oneLineTag("td", {
            textContent : employee.contract ? employee.contract.position : "-----"
        }))
        columns.push(oneLineTag("td", {
            textContent : employee.contract ? toDDMMYYYYFormat(employee.contract.dateOfConclusion) : "-----"
        }))
        columns.push(oneLineTag("td", {
            textContent : employee.contract ? employee.contract.expirationDate ? toDDMMYYYYFormat(employee.contract.expirationDate) : "-----" : "-----"
        }))

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