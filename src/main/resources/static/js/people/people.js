window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/people/get-people-data");
    request.send(null);
    request.onreadystatechange = function () {
        var response = JSON.parse(request.responseText);
        if (request.readyState === 4 && request.status === 200) {
            var welcome = document.getElementById("welcome");
            var fullName = document.getElementById("full-name");
            var idCardNumber = document.getElementById("id-card-number");
            var code = document.getElementById("code");
            welcome.innerText = "你好, " + response["username"];
            fullName.innerText = response["fullName"];
            idCardNumber.innerText = response["idCardNumber"];
            if (response["greenCodeAfter"] < Date.now() / 1000) {
                code.style.background = "green";
            } else {
                code.style.background = "red";
            }
        }
    }
}