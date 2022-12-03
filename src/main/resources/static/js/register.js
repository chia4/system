window.onload = function () {
    var errorInfo = getParamValue("error");

    if (errorInfo !== false) {
        $("error-div").innerText = errorInfo;
    }

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

/**
 * 这个函数和下面的check()函数都是可以复用的，只需要更改每
 * 个函数中最上面的getElementById参数即可
 */
function configCheck() {
    var formElement = $("register-form");

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
    var formElement = $("register-form");
    var submitElement = $("form-submit");
    var outputElement = $("error-div");
    var passwordElement = $("form-password");
    var checkPasswordElement = $("form-check-password");

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
