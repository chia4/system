window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/agency/get-agency-data");
    request.send(null);
    request.onreadystatechange = function () {
        var response = JSON.parse(request.responseText);
        if (request.readyState === 4 && request.status === 200) {
            $("welcome").innerText = "你好, " + response["username"];
            $("agency-name").innerText = response["agencyName"];
        }
    }
    getData();
}

function $(x) {
    return document.getElementById(x);
}

function getData() {
    var dataTable = $("data-table");
    var request = new XMLHttpRequest();
    request.open("GET", "/agency/get-authorization");
    request.send(null);
    request.onreadystatechange = function () {
        var response = JSON.parse(request.responseText);
        for (var set in response) {
            var row = dataTable.insertRow(-1)
            var cell = row.insertCell(-1);
            cell.innerText = response[set]["peopleUsername"];
            cell = row.insertCell(-1);
            cell.innerText = response[set]["authorizationTime"];
        }
    }
}
