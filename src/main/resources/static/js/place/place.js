window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "/place/get-place-data");
    request.send(null);
    request.onreadystatechange = function () {
        var response = JSON.parse(request.responseText);
        if (request.readyState === 4 && request.status === 200) {
            $("welcome").innerText = "你好, " + response["username"];
            $("place-name").innerText = response["placeName"];
            $("place-address").innerText = response["placeAddress"];
            if (response["lowRiskAfter"] > Date.now() / 1000) {
                $("low-risk-after-label").innerText = "恢复低风险: "
                date = new Date(response["lowRiskAfter"] * 1000)
                $("low-risk-after").innerText = date.toLocaleString();
            }
        }
    }
}

function $(x) {
    return document.getElementById(x);
}