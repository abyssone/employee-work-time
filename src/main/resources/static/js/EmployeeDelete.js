let deleteBtn = document.getElementById("delete-employee");
let employeeId = document.querySelector('[data-employeeId]').getAttribute('data-employeeId');

deleteBtn.addEventListener("click", fetchFunc);

function fetchFunc() {
    if (employeeId == null) return;

    fetch("/api/employee/delete", {
        method: 'post',
        headers: {
            "Content-Type": "application/json"
        },
        redirect: 'follow',
        body: JSON.stringify({
            id: employeeId
        })
    }).then(response => {
        if (response.redirected) {
            document.location = response.url;
        }
    })
        .catch(e => console.log(e));
}