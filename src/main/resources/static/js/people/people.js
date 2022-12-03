window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/people/get-people-data");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            $("welcome").innerText = "你好, " + response["username"];
            $("full-name").innerText = response["fullName"];
            $("id-card-number").innerText = response["idCardNumber"];
            if (response["greenCodeAfter"] > Date.now() / 1000) {
                $("green-code-after-label").innerText = "恢复绿码: ";
                date = new Date(response["greenCodeAfter"] * 1000);
                $("green-code-after").innerText = date.toLocaleString();
            }
        }
    }
    getResultData();
    getAuthorizationData();
}

function $(x) {
    return document.getElementById(x);
}

function getResultData() {
    var resultTable = $("result-table");
    var request = new XMLHttpRequest();
    request.open("GET", "/people/get-result");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            if (request.responseText === "") {
                var row = resultTable.insertRow(-1);
                var cell = row.insertCell(-1);
                cell.colSpan = "3";
                cell.innerText = "空";
            } else {
                var response = JSON.parse(request.responseText);
                var date = new Date(response[0]["resultTime"] * 1000);
                if (response[0]["result"] === "NEGATIVE") {
                    $("latest-test").innerText = date.toLocaleString() + " " + "阴性";
                } else if (response[0]["result"] === "POSITIVE") {
                    $("latest-test").innerText = date.toLocaleString() + " " + "阳性";
                }
                for (var set in response) {
                    var row = resultTable.insertRow(-1);
                    var cell = row.insertCell(-1);
                    cell.innerText = response[set]["agencyName"];
                    cell = row.insertCell(-1);
                    date = new Date(response[set]["resultTime"] * 1000);
                    cell.innerText = date.toLocaleString();
                    cell = row.insertCell(-1);
                    if (response[set]["result"] === "NEGATIVE") {
                        cell.innerText = "阴性";
                        row.style.backgroundColor = "darkseagreen";
                    } else if (response[set]["result"] === "POSITIVE") {
                        cell.innerText = "阳性";
                        row.style.backgroundColor = "lightcoral";
                    }
                }
            }
        }
    }
}

function getAuthorizationData() {
    var authorizationLable = $("authorization-table");
    request = new XMLHttpRequest();
    request.open("GET", "/people/get-authorization");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            if (request.responseText === "") {
                var row = authorizationLable.insertRow(-1);
                var cell = row.insertCell(-1);
                cell.colSpan = "2";
                cell.innerText = "空";
            } else {
                var response = JSON.parse(request.responseText);
                for (var set in response) {
                    var row = authorizationLable.insertRow(-1);
                    var cell = row.insertCell(-1);
                    cell.innerText = response[set]["agencyName"];
                    cell = row.insertCell(-1);
                    date = new Date(response[set]["authorizationTime"] * 1000);
                    cell.innerText = date.toLocaleString();
                    row.style.backgroundColor = "khaki";
                }
            }
        }
    }
}