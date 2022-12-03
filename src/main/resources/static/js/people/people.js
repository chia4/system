window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/people/get-people-data");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            $("welcome").innerText = "你好, " + response["username"];
            $("full-name").innerText = response["fullName"];
            $("id-card-number").innerText = response["idCardNumber"];
            if (response["greenCodeAfter"] > Date.now() / 1000) {
                $("green-code-after-label").innerText = "恢复绿码: "
                date = new Date(response["greenCodeAfter"] * 1000)
                $("green-code-after").innerText = date.toLocaleString();
            }
        }
    }
}

function $(x) {
    return document.getElementById(x);
}
