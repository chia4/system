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
}

function $(x) {
    return document.getElementById(x);
}