window.onload = function () {
    var request = new XMLHttpRequest();
    request.open("GET", "user-login-data");
    request.send(null);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            var response = JSON.parse(request.responseText);
            var title;
            switch (response["userType"]) {
                case "PEOPLE":
                    title = " 区域疫情管理系统 | 大众 | 待开发";
                    break;
                case "PLACE":
                    title = " 区域疫情管理系统 | 场所 | 待开发";
                    break;
                case "AGENCY":
                    title = " 区域疫情管理系统 | 机构 | 待开发";
                    break;
            }
            document.title = title;
            var bodyTitle = document.getElementById("title");
            var welcome = document.getElementById("welcome");
            bodyTitle.innerText = title;
            welcome.innerText = "你好, " + response["username"]
        }
    }
}