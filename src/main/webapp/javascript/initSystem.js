const CHECK_SYSTEM_STATE = "CheckSystemState";
const SYSTEM_INIT = "SystemInit";
const LOG_IN = "LogIn";

function useDefaultParam(){
    document.getElementById("db_name_input").value="postgres"
    document.getElementById("db_username_input").value="dboper"
    document.getElementById("db_password_input").value="dboper@123"
}
function nextPanel(){
    document.getElementById("set_param_block").style.display="none";
    document.getElementById("reg_block").style.display="block";
}
function lastPanel(){
    document.getElementById("reg_block").style.display="none";
    document.getElementById("set_param_block").style.display="block";
}
function initSystem() {
    var dataBaseName=document.getElementById("db_name_input").value
    var dbUsername=document.getElementById("db_username_input").value
    var dbPassword=document.getElementById("db_password_input").value
    var adminUsername=document.getElementById("admin_username_input").value
    var adminPassword=document.getElementById("admin_password_input").value
    var repeatPassword=document.getElementById("repeat_password_input").value

    if(dataBaseName.length===0
        ||dbUsername.length===0
        ||dbPassword.length===0
        ||adminUsername.length===0
        ||adminPassword.length===0
        ||repeatPassword.length===0){
        showMessageBox("信息填写不全")
        return
    }
    if(adminPassword!==repeatPassword){
        showMessageBox("两次输入的管理员密码不同")
        return
    }
    if(adminUsername.length<3 || adminUsername.length>20){
        showMessageBox("请保持管理员用户名长度介于4~20之间")
        return
    }
    if(adminPassword.length<8 || adminUsername.length>20){
        showMessageBox("请保持管理员口令长度介于8~20之间")
        return
    }

    showLoading()
    const url = getPostRequestUrl();

    var data={
        type:SYSTEM_INIT,
        'dataBaseName':dataBaseName,
        'dbUsername':dbUsername,
        'dbPassword':dbPassword,
        'adminUsername':adminUsername,
        'adminPassword':adminPassword
    }

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var result;
                if (xhr.responseText.indexOf("init succ") !== -1) {
                    result = "初始化成功";
                } else {
                    result = "初始化失败";
                }
                setTimeout(function () {
                    if (result === "初始化成功") result = result + "\n1秒后自动跳转主页"
                    closeLoading()
                    showMessageBox(result);
                    setTimeout(function () {
                        if (result.indexOf("初始化成功") !== -1) {
                            window.location.href = "index.html"
                        }
                    }, 2000);
                }, 500);
                console.log(xhr.responseText);
            } else {
                closeLoading()
                showMessageBox("网络连接失败");
            }
        }
    };
    xhr.send(JSON.stringify(data));
}
function showMessageBox(msg) {
    showMask()
    document.getElementById("message").innerText = msg
    document.getElementById("message_block").style.display = "block"
}

function closeMessageBox() {
    closeMask()
    document.getElementById("mask").style.display = 'none'
    document.getElementById("message_block").style.display = "none"
}

function showMask() {
    document.getElementById("mask").style.display = 'block'
}

function closeMask() {
    document.getElementById("mask").style.display = 'none'

}

function showLoading() {
    showMask()
    document.getElementById("loading_block").style.display = 'block'
}

function closeLoading() {
    closeMask()
    document.getElementById("loading_block").style.display = 'none'
}

