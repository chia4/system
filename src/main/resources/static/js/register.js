window.onload = function () {
    var errorDiv = document.getElementById("error-div");
    var errorInfo = getParamValue("error");

    var registerButton = document.getElementById("login-button");
    var formSubmit = document.getElementById("form-submit");

    formSubmit.disabled = true;
    registerButton.onclick = function () { window.location.href = "/login"; }

    if (errorInfo != false) {
        errorDiv.innerText = decodeURI(errorInfo);
    }

    var formType = document.getElementById("form-type");
    var formUsername = document.getElementById("form-username");
    var formPassword = document.getElementById("form-password");
    var formCheckPassword = document.getElementById("form-check-password");

    formType.onchange = check;
    formUsername.onkeyup = check;
    formPassword.onkeyup = check;
    formCheckPassword.onkeyup = check;
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
    var formType = document.getElementById("form-type");
    var formUsername = document.getElementById("form-username");
    var formPassword = document.getElementById("form-password");
    var formCheckPassword = document.getElementById("form-check-password");

    var formSubmit = document.getElementById("form-submit");
    var errorDiv = document.getElementById("error-div");

    if (formType.value == "not-selected" || formUsername.value == "" || formPassword.value == "" || formCheckPassword.value == "") {
        errorDiv.innerText = "输入不能为空";
        formSubmit.disabled = true;
    } else if (formPassword.value != formCheckPassword.value) {
        errorDiv.innerText = "两次密码输入不一致";
        formSubmit.disabled = true;
    } else {
        errorDiv.innerText = "";
        formSubmit.disabled = false;
    }
}