window.onload = function () {
    var errorDiv = document.getElementById("error-div");
    var errorInfo = getParamValue("error");
    if (errorInfo != false) {
        errorDiv.innerText = decodeURI(errorInfo);
    }
}

function getParamValue(key) {
    var query = window.location.search.substring(1);
    var params = query.split("&");
    for (var i = 0; i < params.length; i++) {
        var param = params[i].split("=");
        if (param[0] == key) {
            return param[1];
        }
    }
    return false;
}