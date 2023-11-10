
function removeChild(elementId) {
    const element = document.getElementById(elementId)
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

function showHome() {
    document.getElementById("home_block").style.display="inline-block"
    document.getElementById("patient_center_block").style.display="none"
    document.getElementById("message_show_block").style.display="none"
    document.getElementById("data_manage_block").style.display="none"
    document.getElementById("data_query_block").style.display="none"
    document.getElementById("data_stat_block").style.display="none"
    document.getElementById("admin_center_block").style.display="none"

}

function showPatientCenter() {
    document.getElementById("home_block").style.display="none"
    document.getElementById("patient_center_block").style.display="inline-block"
    document.getElementById("message_show_block").style.display="none"
    document.getElementById("data_manage_block").style.display="none"
    document.getElementById("data_query_block").style.display="none"
    document.getElementById("data_stat_block").style.display="none"
    document.getElementById("admin_center_block").style.display="none"
}

function showInformationDisplay() {
    document.getElementById("home_block").style.display="none"
    document.getElementById("patient_center_block").style.display="none"
    document.getElementById("message_show_block").style.display="inline-block"
    document.getElementById("data_manage_block").style.display="none"
    document.getElementById("data_query_block").style.display="none"
    document.getElementById("data_stat_block").style.display="none"
    document.getElementById("admin_center_block").style.display="none"
}

function showDataManage() {
    document.getElementById("home_block").style.display="none"
    document.getElementById("patient_center_block").style.display="none"
    document.getElementById("message_show_block").style.display="none"
    document.getElementById("data_manage_block").style.display="inline-block"
    document.getElementById("data_query_block").style.display="none"
    document.getElementById("data_stat_block").style.display="none"
    document.getElementById("admin_center_block").style.display="none"

    //病患、病房、床位、医生、护士、医生值班、护士值班、管理员

}

function showDataQuery() {
    document.getElementById("home_block").style.display="none"
    document.getElementById("patient_center_block").style.display="none"
    document.getElementById("message_show_block").style.display="none"
    document.getElementById("data_manage_block").style.display="none"
    document.getElementById("data_query_block").style.display="inline-block"
    document.getElementById("data_stat_block").style.display="none"
    document.getElementById("admin_center_block").style.display="none"
}

function showDataStat() {
    document.getElementById("home_block").style.display="none"
    document.getElementById("patient_center_block").style.display="none"
    document.getElementById("message_show_block").style.display="none"
    document.getElementById("data_manage_block").style.display="none"
    document.getElementById("data_query_block").style.display="none"
    document.getElementById("data_stat_block").style.display="inline-block"
    document.getElementById("admin_center_block").style.display="none"
}

function patientCenter(){
        window.location.href="patientBookingBunk.html"

}

function openTab(evt, tabName) {
    //createTable(data)
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
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

function setLogStatus(state) {
    if(state){
        document.getElementById("admin_show").innerText=getLoginStatus().username
        document.getElementById("log_out").style.display="inline-block";
    }else{
        clearLoginStatus()
        location.reload()
    }
}

function checkSystemState() {
    const url = getPostRequestUrl();
    const data = {
        type:CHECK_SYSTEM_STATE
    }
    const xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                if (xhr.responseText.indexOf("SystemNotInit") !== -1) {
                    window.location.href="initSystem.html";
                }
                console.log(xhr.responseText);
            }
        }
    };
    xhr.send(JSON.stringify(data));
}

window.onload=function(){
    checkSystemState()
    window.location.href="patientBookingBunk.html"
}





function searchAdmin(containerID) {
    var username=document.getElementById("adminSearchUsernameInput").value;
    var condition="username LIKE '%"+username+"%'"
    thisTableName=ADMIN_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchPatient(containerID){
    var id=document.getElementById("patientSearchIdInput").value;
    var name=document.getElementById("patientSearchNameInput").value;
    var condition="patient_ID LIKE '%"+id+"%' AND patient_name LIKE '%"+name+"%'"
    thisTableName=PATIENT_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchDepartment(containerID){
    var id=document.getElementById("departmentSearchNumberInput").value;
    var name=document.getElementById("departmentSearchIdInput").value;
    var condition="department_ID LIKE '%"+id+"%' AND department_name LIKE '%"+name+"%'"
    thisTableName=DEPARTMENT_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchWard(containerID) {
    var id=document.getElementById("wardSearchNumberInput").value;
    var department=document.getElementById("wardSearchDepartmentInput").value;
    var condition="ward_number LIKE '%"+id+"%' AND department_ID LIKE '%"+department+"%'"
    thisTableName=WARD_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchBed(containerID) {
    var bunkNumber=document.getElementById("bedSearchBunkNumberInput").value;
    var wardNumber=document.getElementById("bedSearchWardNumberInput").value;
    var checkIn=document.getElementById("bedSearchIsCheckIn").value;
    var condition="bunk_number LIKE '%"+bunkNumber+"%' AND ward_number LIKE '%"+wardNumber+"%' AND is_check_in LIKE '%"+checkIn+"%'"
    thisTableName=BUNK_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchDoctor(containerID){
    var id=document.getElementById("doctorSearchIdInput").value;
    var department=document.getElementById("doctorSearchDepartmentInput").value;
    var name=document.getElementById("doctorSearchNameInput").value;
    var condition="doctor_ID LIKE '%"+id+"%' AND department_ID LIKE '%"+department+"%'"+"%' AND doctor_name LIKE '%"+name+"%'"
    thisTableName=DOCTOR_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchNurse(containerID) {
    var id=document.getElementById("nurseSearchIdInput").value;
    var department=document.getElementById("nurseSearchDepartmentInput").value;
    var name=document.getElementById("nurseSearchNameInput").value;
    var condition="nurse_ID LIKE '%"+id+"%' AND department_ID LIKE '%"+department+"%'"+"%' AND nurse_name LIKE '%"+name+"%'"
    thisTableName=NURSE_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchDoctorDuty(containerID){
    var dutyId=document.getElementById("doctorWorkSearchDutyIdInput").value;
    var doctorId=document.getElementById("doctorWorkSearchDoctorIdInput").value;
    var time=document.getElementById("doctorWorkSearchTimeInput").value;
    var condition="doctor_duty_id LIKE '%"+dutyId+"%' AND doctor_ID LIKE '%"+doctorId+"%'"+"%' AND duty_time LIKE '%"+time+"%'"
    thisTableName=DOCTOR_DUTY_SCHEDULE_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchNurseDuty(containerID){
    var dutyId=document.getElementById("nurseWorkSearchDutyIdInput").value;
    var nurseId=document.getElementById("nurseWorkSearchDoctorIdInput").value;
    var time=document.getElementById("nurseWorkSearchTimeInput").value;
    var condition="nurse_duty_id LIKE '%"+dutyId+"%' AND nurse_ID LIKE '%"+nurseId+"%'"+"%' AND duty_time LIKE '%"+time+"%'"
    thisTableName=NURSE_DUTY_SCHEDULE_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchBooking(containerID) {
    var bookingNumber=document.getElementById("bookingSearchBookingNumberInput").value;
    var patientId=document.getElementById("bookingSearchPatientIdInput").value;
    var bunkNumber=document.getElementById("bookingSearchBunkNumberInput").value;
    var condition="booking_number LIKE '%"+bookingNumber+"%' AND patient_ID LIKE '%"+patientId+"%'"+"%' AND bunk_number LIKE '%"+bunkNumber+"%'"
    thisTableName=WARD_BOOKING_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchCase(containerID) {
    var hospitalNumber=document.getElementById("caseSearchHospitalNumberInput").value;
    var patientId=document.getElementById("caseSearchPatientIdInput").value;
    var doctorId=document.getElementById("caseSearchDoctorIdInput").value;
    var condition="hospital_number LIKE '%"+hospitalNumber+"%' AND patient_ID LIKE '%"+patientId+"%'"+"%' AND doctor_ID LIKE '%"+doctorId+"%'"
    thisTableName=CASES_INFO_TABLE
    thisCondition=condition
    refreshTable(containerID)
}

function searchCheckIn(containerID){
    var check_in_number=document.getElementById("checkInSearchCheckInNumberInput").value;
    var hospital_number=document.getElementById("checkInSearchHospitalNumberInput").value;
    var bunk_number=document.getElementById("checkInSearchBunkNumberInput").value;
    var condition="check_in_number LIKE '%"+check_in_number+"%' AND hospital_number LIKE '%"+hospital_number+"%'"+"%' AND bunk_number LIKE '%"+bunk_number+"%'"
    thisTableName=CHECK_IN_SITUATION_TABLE
    thisCondition=condition
    refreshTable(containerID)
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


