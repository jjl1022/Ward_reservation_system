//TODO:用于菜单点击展开关闭和点击打开对应页面
function toggleSubMenu(submenuId) {
    var submenu = document.getElementById("submenu-" + submenuId);
    if (submenu.style.display === "block") {
        submenu.style.display = "none";
    } else {
        submenu.style.display = "block";
    }
}
// 获取所有的菜单按钮
const menuItems = document.querySelectorAll('.single-function-button');
// 获取所有的页面
const pages = document.querySelectorAll('.childPage');
// 为每个菜单按钮添加点击事件处理程序
menuItems.forEach(function(item) {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        // 隐藏所有的页面
        pages.forEach(function(page) {
            console.log("ok")
            page.style.display = 'none';
        });
        // 获取目标页面的ID
        var target = item.getAttribute('data-target');
        // 显示目标页面
        document.getElementById(target).style.display = 'block';
    });
});

function getLoginStatus() {
    var cookies = document.cookie.split(';');
    var loggedIn = false;
    var username = "";

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();

        if (cookie.startsWith("loggedIn=")) {
            loggedIn = cookie.substring("loggedIn=".length) === "true";
        } else if (cookie.startsWith("username=")) {
            username = cookie.substring("username=".length);
        }
    }

    return {loggedIn, username};
}

window.onload = function () {
    var logStatus=getLoginStatus()
    if(!logStatus.loggedIn){
        window.location.href="login.html"
    }
}

function setLogStatus(state) {
    if(state){

    }else{
        clearLoginStatus()
        location.reload()
    }
}

function clearLoginStatus() {
    document.cookie = "loggedIn=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}