window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/user-login-data");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            $("welcome").innerText = "你好, " + response["username"];
        }
    }

    var peopleUsername = getParamValue("peopleUsername");
    var authorizationTime = getParamValue("authorizationTime");
    if (peopleUsername !== false && authorizationTime !== false) {
        $("feedback").innerText = "采样登记成功: (用户id)" + peopleUsername + " | (时间戳)" + authorizationTime;
        $("feedback").style.color = "green";
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
            return decodeURI(param[1]);
        }
    }
    return false;
}
