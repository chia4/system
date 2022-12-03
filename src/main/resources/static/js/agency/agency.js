window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/agency/get-agency-data");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            $("welcome").innerText = "你好, " + response["username"];
            $("agency-name").innerText = "机构: " + response["agencyName"];
        }
    }
    var errorInfo = getParamValue("error");
    if (errorInfo !== false) {
        $("error-div").innerText = errorInfo;
    }
    getData();
    configCheck();
}

function $(x) {
    return document.getElementById(x);
}

function getParamValue(key) {
    var query = window.location.search.substring(1);
    var params = query.split("&");
    for (var i = 0; i < params.length; i++) {
        var param = params[i].split("=");
        if (param[0] === key) {
            return decodeURI(param[1]);
        }
    }
    return false;
}

function getData() {
    var dataTable = $("data-table");
    var request = new XMLHttpRequest();
    request.open("GET", "/agency/get-authorization");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            if (response.length === 0) {
                var row = dataTable.insertRow(-1);
                var cell = row.insertCell(-1);
                cell.colSpan = "2";
                cell.innerText = "空";
            } else {
                for (var set in response) {
                    var row = dataTable.insertRow(-1);
                    var cell = row.insertCell(-1);
                    cell.innerText = response[set]["peopleUsername"];
                    cell = row.insertCell(-1);
                    cell.innerText = response[set]["authorizationTime"];
                }
            }
        }
    }
}

function configCheck() {
    var formElement = $("result-form");

    var child = formElement.firstElementChild;
    for (var i = 0; i < formElement.childElementCount; i++) {
        if ((child.nodeName === "SELECT" || child.nodeName === "INPUT") && child.type !== "submit") {
            if (child.nodeName === "SELECT") {
                child.onchange = check;
            } else {
                child.onkeyup = check;
            }
        }
        child = child.nextElementSibling;
    }
}

function check() {
    var formElement = $("result-form");
    var submitElement = $("form-submit");
    var outputElement = $("error-div");
    var passwordElement = $("null");
    var checkPasswordElement = $("null");

    var child = formElement.firstElementChild;
    for (var i = 0; i < formElement.childElementCount; i++) {
        if ((child.nodeName === "SELECT" || child.nodeName === "INPUT") && child.type !== "submit") {
            if ((child.nodeName === "SELECT" && child.value === "not-selected") || (child.nodeName === "INPUT" && child.value === "")) {
                outputElement.innerText = "输入不能为空";
                submitElement.disabled = true;
                return;
            }
        }
        child = child.nextElementSibling;
    }
    if (passwordElement != null && checkPasswordElement != null) {
        if (passwordElement.value !== checkPasswordElement.value) {
            outputElement.innerText = "两次密码输入不一致";
            submitElement.disabled = true;
            return;
        }
    }
    outputElement.innerText = "";
    submitElement.disabled = false;
}