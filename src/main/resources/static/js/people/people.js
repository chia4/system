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
            var greenCodeAfter = document.getElementById("green-code-after");
            var greenCodeAfterLabel = document.getElementById("green-code-after-label");
            welcome.innerText = "你好, " + response["username"];
            fullName.innerText = response["fullName"];
            idCardNumber.innerText = response["idCardNumber"];
            if (response["greenCodeAfter"] > Date.now() / 1000) {
                greenCodeAfterLabel.innerText = "恢复绿码: "
                date = new Date(response["greenCodeAfter"] * 1000)
                greenCodeAfter.innerText = date.toLocaleString();
            }
        }
    }
}