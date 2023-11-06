// 状态：系统已经初始化过了
const STSYEM_HAS_INIT = "SystemHasInit";

// 状态：系统还未初始化
const STSYEM_NOT_INIT = "SystemNotInit";

// 检查系统是否初始化
const CHECK_SYSTEM_STATE = "CheckSystemState";

// 系统初始化
const SYSTEM_INIT = "SystemInit";

// 登录请求
const LOG_IN = "LogIn";

// 增加管理员
const ADD_ADMIN = "AddAdmin";

// 修改管理员
const EDIT_ADMIN = "EditAdmin";

// 删除管理员
const DEL_ADMIN = "DelAdmin";

// 增加病患
const ADD_PATIENT = "AddPatient";

// 修改病患
const EDIT_PATIENT = "EditPatient";

// 删除病患
const DEL_PATIENT = "DelPatient";

// 增加病房
const ADD_WARD = "AddWard";

// 修改病房
const EDIT_WARD = "EditWard";

// 删除病房
const DEL_WARD = "DelWard";

// 增加床位
const ADD_BED = "AddBed";

// 修改床位
const EDIT_BED = "EditBed";

// 删除床位
const DEL_BED = "DelBed";

// 调换病房
const CHANGE_BED = "ChangeBed";

// 预定病房
const BOOKING_BED = "BookingBed";

// 增加医生
const ADD_DOCTOR = "AddDoctor";

// 修改医生
const EDIT_DOCTOR = "EditDoctor";

// 删除医生
const DEL_DOCTOR = "DelDoctor";

// 增加护士
const ADD_NURSE = "AddNurse";

// 修改护士
const EDIT_NURSE = "EditNurse";

// 删除护士
const DEL_NURSE = "DelNurse";

// 制定医生值班表
const DEVELOP_DOCTOR_DUTY_SCHEDULE = "DevelopDoctorDutySchedule";

// 获取医生值班表
const GET_DOCTOR_DUTY_SCHEDULE = "GetDoctorDutySchedule";

// 制定护士值班表
const DEVELOP_NURSE_DUTY_SCHEDULE = "DevelopNurseDutySchedule";

// 获取护士值班表
const GET_NURSE_DUTY_SCHEDULE = "GetNurseDutySchedule";

// 获取病患信息
const GET_PATIENT_INFO = "GetPatientInfo";

// 获取病房信息
const GET_WARD_INFO = "GetWardInfo";

// 获取医生信息
const GET_DOCTOR_INFO = "GetDoctorInfo";

// 获取护士信息
const GET_NURSE_INFO = "GetNurseInfo";

// 获取病房预定信息
const GET_BED_BOOKING_INFO = "GetWardBookingInfo";

// 获取登录按钮的 DOM 元素
const loginButton = document.getElementById("log_button");

// 添加点击事件监听器
loginButton.addEventListener('click', () => {
    // 执行登录逻辑
    login();
});

// 登录逻辑
function login() {
    showLoading()
    // 获取用户名和密码输入框的值
    const usernameInput = document.getElementById("username_input").value
    const passwordInput = document.getElementById("password_input").value

    const url = getPostRequestUrl();

    var data={
        type:LOG_IN,
        'username':usernameInput,
        'password':passwordInput
    }

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var result;
                if (xhr.responseText.indexOf("log succ") !== -1) {
                    result = "登陆成功";
                } else {
                    result = "登陆失败";
                }
                setTimeout(function () {
                    if (result === "登陆成功") result = result + "\n1秒后自动跳转主页"
                    closeLoading()
                    showMessageBox(result);
                    setTimeout(function () {
                        if (result.indexOf("登陆成功") !== -1) {
                            setLoginStatus(usernameInput)
                            window.location.href = "admin.html"
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

function setLoginStatus(username) {
    document.cookie = `loggedIn=true; expires=${getExpiryDate(7)}; path=/`;
    document.cookie = `username=${username}; expires=${getExpiryDate(7)}; path=/`;
}

// 获取指定天数后的过期日期
function getExpiryDate(days) {
    var date = new Date();
    date.setDate(date.getDate() + days);
    return date.toUTCString();
}

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

function clearLoginStatus() {
    document.cookie = "loggedIn=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

window.onload = function () {
    var logStatus=getLoginStatus()
    if(logStatus.loggedIn){
        showMessageBox("您已经登陆过了,请勿重复登陆。1秒后自动跳转管理页");
        setTimeout(function () {
            window.location.href = "admin.html"
        }, 2000);
    }
}

