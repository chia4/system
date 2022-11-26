window.onload = function () {
    var errorDiv = document.getElementById("error-div");
    var errorInfo = getParamValue("error");

    if (errorInfo !== false) {
        errorDiv.innerText = decodeURI(errorInfo);
    }

    configCheck();
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

function configCheck() {
    var formElement = document.getElementById("login-form");

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
    var formElement = document.getElementById("login-form");
    var submitElement = document.getElementById("form-submit");
    var outputElement = document.getElementById("error-div");
    var passwordElement = document.getElementById("null");
    var checkPasswordElement = document.getElementById("null");

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
