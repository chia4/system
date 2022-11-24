window.onload = function () {
    var errorDiv = document.getElementById("error-div");
    var errorInfo = getParamValue("error");

    var registerButton = document.getElementById("register-button");
    var formSubmit = document.getElementById("form-submit");

    formSubmit.disabled = true;
    registerButton.onclick = function () { window.location.href = "/register"; }

    if (errorInfo != false) {
        errorDiv.innerText = decodeURI(errorInfo);
    }

    var formUsername = document.getElementById("form-username");
    var formPassword = document.getElementById("form-password");

    formUsername.onkeyup = check;
    formPassword.onkeyup = check;
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

function check() {
    var formUsername = document.getElementById("form-username");
    var formPassword = document.getElementById("form-password");

    var formSubmit = document.getElementById("form-submit");
    var errorDiv = document.getElementById("error-div");

    if (formUsername.value == "" || formPassword.value == "") {
        errorDiv.innerText = "输入不能为空";
        formSubmit.disabled = true;
    } else {
        errorDiv.innerText = "";
        formSubmit.disabled = false;
    }
}