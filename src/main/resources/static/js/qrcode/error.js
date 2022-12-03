window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/user-login-data");
    request.send(null);
    request.onreadystatechange = function () {
        var response = JSON.parse(request.responseText);
        if (request.readyState === 4 && request.status === 200) {
            $("welcome").innerText = "你好, " + response["username"];
        }
    }

    var errorInfo = getParamValue("error");
    if (errorInfo !== false) {
        $("error-h").innerText = decodeURI(errorInfo);
    }

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
            return param[1];
        }
    }
    return false;
}
