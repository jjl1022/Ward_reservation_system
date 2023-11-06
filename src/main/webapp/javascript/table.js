var lastRow = []
var thisTableName
var thisCondition
var ADMIN_INFO_TABLE = "admin_info_table";//管理员信息表
var PATIENT_INFO_TABLE = "patient_info_table";//患者信息表
var WARD_INFO_TABLE = "ward_info_table";//病房信息表
var BUNK_INFO_TABLE = "bunk_info_table";//床位信息表
var CASES_INFO_TABLE = "cases_info_table";//病例信息表
var WARD_BOOKING_TABLE = "ward_booking_table";//病房预定表
var DOCTOR_INFO_TABLE = "doctor_info_table";//医生信息表
var NURSE_INFO_TABLE = "nurse_info_table";//护士信息表
var DOCTOR_DUTY_SCHEDULE_TABLE = "doctor_duty_schedule";//医生值班表
var NURSE_DUTY_SCHEDULE_TABLE = "nurse_duty_schedule";//护士值班表
var CHECK_IN_SITUATION_TABLE = "check_in_situation_table";//入住信息表
var DEPARTMENT_INFO_TABLE = "department_info_table";//科室信息表
var ADD_DATA = "AddData"
var EDIT_DATA = "EditData"
var DEL_DATA = "DelData"
const STSYEM_HAS_INIT = "SystemHasInit";
const STSYEM_NOT_INIT = "SystemNotInit";
const CHECK_SYSTEM_STATE = "CheckSystemState";
const SYSTEM_INIT = "SystemInit";
const LOG_IN = "LogIn";
const ADD_ADMIN = "AddAdmin";
const EDIT_ADMIN = "EditAdmin";
const DEL_ADMIN = "DelAdmin";
const ADD_PATIENT = "AddPatient";
const EDIT_PATIENT = "EditPatient";
const DEL_PATIENT = "DelPatient";
const ADD_WARD = "AddWard";
const EDIT_WARD = "EditWard";
const DEL_WARD = "DelWard";
const ADD_BED = "AddBed";
const EDIT_BED = "EditBed";
const DEL_BED = "DelBed";
const ADD_CASE = "AddCase";
const EDIT_CASE = "EditCase";
const DEL_CASE = "DelCase";
const CHANGE_BED = "ChangeBed";
const BOOKING_BED = "BookingBed";
const ADD_DOCTOR = "AddDoctor";
const EDIT_DOCTOR = "EditDoctor";
const DEL_DOCTOR = "DelDoctor";
const ADD_NURSE = "AddNurse";
const EDIT_NURSE = "EditNurse";
const DEL_NURSE = "DelNurse";
const DEVELOP_DOCTOR_DUTY_SCHEDULE = "DevelopDoctorDutySchedule";
const GET_DOCTOR_DUTY_SCHEDULE = "GetDoctorDutySchedule";
const DEVELOP_NURSE_DUTY_SCHEDULE = "DevelopNurseDutySchedule";
const GET_NURSE_DUTY_SCHEDULE = "GetNurseDutySchedule";
const GET_PATIENT_INFO = "GetPatientInfo";
const GET_WARD_INFO = "GetWardInfo";
const GET_DOCTOR_INFO = "GetDoctorInfo";
const GET_NURSE_INFO = "GetNurseInfo";
const GET_BED_BOOKING_INFO = "GetWardBookingInfo";
const GET_ADMIN_INFO = "GetAdminInfo";
const GET_CASES_INFO = "GetCaseInfo";
const EDIT_BOONING_WARD = "EditBookingWard"
const CANCEL_BOONING_WARD = "CancelBookingWard";
const EDIT_DOCTOR_DUTY_SCHEDULE = "EditDoctorDutySchedule";
const DEL_DOCTOR_DUTY_SCHEDULE = "DelDoctorDutySchedule";
const EDIT_NURSE_DUTY_SCHEDULE = "EditNurseDutySchedule";
const DEL_NURSE_DUTY_SCHEDULE = "DelNurseDutySchedule";
const ADD_CHECK_IN="AddCheckIn";
const EDIT_CHECK_IN="EditCheckIn";
const DEL_CHECK_IN="DelCheckIn";
const ADD_DEPARTMENT="AddDepartment";
const EDIT_DEPARTMENT="EditDepartment";
const DEL_DEPARTMENT="DelDepartment";
const GET_BED_INFO = "GetBedInfo";
const GET_CHECK_IN_INFO = "GetCheckInInfo"
const GET_DEPARTMENT_INFO = "GetDepartmentInfo"
const SELF_BOOKING = "SelfBooking"
const SELF_CANCEL_BOOKING = "SelfCancelBooking"
const SELF_SEARCH_BOOKING = "SelfSearchBooking"



function getDataFromServer(tableName,condition){
    var requestType;
    switch (tableName){
        case ADMIN_INFO_TABLE:
            requestType=GET_ADMIN_INFO
            break;
        case PATIENT_INFO_TABLE:
            requestType=GET_PATIENT_INFO
            break;
        case WARD_INFO_TABLE:
            requestType=GET_WARD_INFO
            break;
        case BUNK_INFO_TABLE:
            requestType=GET_BED_INFO
            break;
        case CASES_INFO_TABLE:
            requestType=GET_CASES_INFO
            break;
        case WARD_BOOKING_TABLE:
            requestType=GET_BED_BOOKING_INFO
            break;
        case DOCTOR_INFO_TABLE:
            requestType=GET_DOCTOR_INFO
            break;
        case NURSE_INFO_TABLE:
            requestType=GET_NURSE_INFO
            break;
        case DOCTOR_DUTY_SCHEDULE_TABLE:
            requestType=GET_DOCTOR_DUTY_SCHEDULE
            break;
        case NURSE_DUTY_SCHEDULE_TABLE:
            requestType=GET_NURSE_DUTY_SCHEDULE
            break;
        case CHECK_IN_SITUATION_TABLE:
            requestType=GET_CHECK_IN_INFO
            break;
        case DEPARTMENT_INFO_TABLE:
            requestType=GET_DEPARTMENT_INFO
            break;
        default:
            break;
    }

    var state=false//记录回复结果
    var responseMsg;

    const xhr = new XMLHttpRequest();
    const url = getPostRequestUrl();

    const requestData = {
        type: requestType,
        condition:condition
    };

    console.log(JSON.stringify(requestData))

    xhr.open("POST", url, false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                state=true;
                responseMsg=JSON.parse(xhr.responseText)
            } else {
                state=false;
            }
        }
    };
    xhr.send(JSON.stringify(requestData));

    return {state,responseMsg}
}



function syncDataToServer(tableName, operateType, operateData) {
    var requestType;
    switch(tableName){
        case ADMIN_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_ADMIN;
                    break;
                case EDIT_DATA:
                    requestType=EDIT_ADMIN
                    break;
                case DEL_DATA:
                    requestType=DEL_ADMIN
                    break;
            }
            break;
        case PATIENT_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_PATIENT
                    break;
                case EDIT_DATA:
                    requestType=EDIT_PATIENT
                    break;
                case DEL_DATA:
                    requestType=DEL_PATIENT
                    break;
            }
            break;
        case WARD_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_WARD
                    break;
                case EDIT_DATA:
                    requestType=EDIT_WARD
                    break;
                case DEL_DATA:
                    requestType=DEL_WARD
                    break;
            }
            break;
        case BUNK_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_BED
                    break;
                case EDIT_DATA:
                    requestType=EDIT_BED
                    break;
                case DEL_DATA:
                    requestType=DEL_BED
                    break;
            }
            break;
        case CASES_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_CASE
                    break;
                case EDIT_DATA:
                    requestType=EDIT_CASE
                    break;
                case DEL_DATA:
                    requestType=DEL_CASE
                    break;
            }
            break;
        case WARD_BOOKING_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=BOOKING_BED
                    break;
                case EDIT_DATA:
                    requestType=EDIT_BOONING_WARD
                    break;
                case DEL_DATA:
                    requestType=CANCEL_BOONING_WARD
                    break;
            }
            break;
        case DOCTOR_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_DOCTOR
                    break;
                case EDIT_DATA:
                    requestType=EDIT_DOCTOR
                    break;
                case DEL_DATA:
                    requestType=DEL_DOCTOR
                    break;
            }
            break;
        case NURSE_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_NURSE
                    break;
                case EDIT_DATA:
                    requestType=EDIT_NURSE
                    break;
                case DEL_DATA:
                    requestType=DEL_NURSE
                    break;
            }
            break;
        case DOCTOR_DUTY_SCHEDULE_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=DEVELOP_DOCTOR_DUTY_SCHEDULE
                    break;
                case EDIT_DATA:
                    requestType=EDIT_DOCTOR_DUTY_SCHEDULE
                    break;
                case DEL_DATA:
                    requestType=DEL_DOCTOR_DUTY_SCHEDULE
                    break;
            }
            break;
        case NURSE_DUTY_SCHEDULE_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=DEVELOP_NURSE_DUTY_SCHEDULE
                    break;
                case EDIT_DATA:
                    requestType=EDIT_NURSE_DUTY_SCHEDULE
                    break;
                case DEL_DATA:
                    requestType=DEL_NURSE_DUTY_SCHEDULE
                    break;
            }
            break;
        case CHECK_IN_SITUATION_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_CHECK_IN
                    break;
                case EDIT_DATA:
                    requestType=EDIT_CHECK_IN
                    break;
                case DEL_DATA:
                    requestType=DEL_CHECK_IN
                    break;
            }
            break;
        case DEPARTMENT_INFO_TABLE:
            switch(operateType){
                case ADD_DATA:
                    requestType=ADD_DEPARTMENT
                    break;
                case EDIT_DATA:
                    requestType=EDIT_DEPARTMENT
                    break;
                case DEL_DATA:
                    requestType=DEL_DEPARTMENT
                    break;
            }
            break;
        default:
            break;
    }
    console.log(requestType)
    var state=false//记录回复结果
    var responseMsg;

    const xhr = new XMLHttpRequest();
    const url = getPostRequestUrl();
    operateData.type=requestType

    xhr.open("POST", url, false);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                state=(response["state"] === "succ");
                responseMsg=response["detail"]
            } else {
                alert("网络连接失败")
            }
        }
    };
    //alert(JSON.stringify(operateData))
    xhr.send(JSON.stringify(operateData));
    return {state,responseMsg}
}


function createTable(containerID, data) {
    var container = document.getElementById(containerID);
    var table = document.createElement("table");

    // 创建表头
    var thead = document.createElement("thead");
    var headerRow = document.createElement("tr");

    var columnHeader = data[0].concat(["操作"]); // 创建新的表头数组

    columnHeader.forEach(function (cellData) {
        var th = document.createElement("th");
        th.textContent = cellData;
        headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    // 创建表格主体部分
    var tbody = document.createElement("tbody");

    for (var i = 1; i < data.length; i++) {
        var row = document.createElement("tr");

        var rowData = data[i].concat([""]); // 创建新的行数据数组

        rowData.forEach(function (cellData, index) {
            var td = document.createElement("td");
            td.textContent = cellData;

            // 如果是最后一列，添加操作按钮
            if (index === rowData.length - 1) {
                var edit_button = document.createElement("button");
                edit_button.textContent = "编辑";
                edit_button.style.borderRadius = "5px 0px 0px 5px"
                edit_button.onclick = function () {
                    editRow(containerID, this)
                }
                var del_button = document.createElement("button");
                del_button.textContent = "删除";
                del_button.onclick = function () {
                    delRow(containerID, this)
                }
                del_button.style.borderRadius = "0px 5px 5px 0px"
                var button_div = document.createElement("div");
                button_div.appendChild(edit_button)
                button_div.appendChild(del_button)
                td.appendChild(button_div);
            }

            row.appendChild(td);
        });
        tbody.appendChild(row);
    }

    table.appendChild(tbody);

    // 将表格添加到容器中
    container.appendChild(table);
}

function editRow(containerID, button) {
    var rowIndex = getRowIndex(button)
    if (button.textContent === "编辑") {
        lastRow = getRow(containerID, parseInt(rowIndex))
        console.log("存放临时行数据："+lastRow)
        button.textContent = "保存"
        var div = document.getElementById(containerID);
        var table = div.querySelector("table");
        var rows = table.rows;
        if (rowIndex >= 0 && rowIndex < rows.length) {
            var row = rows[rowIndex + 1];
            var cells = row.cells;
            for (var cellIndex = 0; cellIndex < cells.length - 1; cellIndex++) {
                var cell = cells[cellIndex];
                var text = cell.innerText.trim();

                var input = document.createElement("input");
                input.type = "text";
                input.value = text;
                cell.innerHTML = "";
                cell.appendChild(input);
            }
        } else {
            console.log("行索引无效：" + rowIndex);
        }
    } else {
        var div = document.getElementById(containerID);
        var table = div.querySelector("table");
        var rows = table.rows;
        var thisRow = []//记录修改后的数据

        //TODO:查看原来是否有数据，判断是增加还是修改
        var editFlag = false
        for (var i = 0; i < lastRow.length; ++i) {
            if (lastRow[i] !== "") {
                editFlag = true
            }
        }

        if (rowIndex >= 0 && rowIndex < rows.length) {
            var row = rows[rowIndex + 1];
            var cells = row.cells;
            for (var cellIndex = 0; cellIndex < cells.length - 1; cellIndex++) {
                var cell = cells[cellIndex];
                var text = cell.querySelector("input").value
                thisRow.push(text)
            }
        } else {
            console.log("行索引无效：" + rowIndex);
        }

        var oldRow=""
        for(var i=0;i<lastRow.length;++i){
            oldRow+=lastRow[i]+"#&"
        }

        var newRow=""
        for(var i=0;i<thisRow.length;++i){
            newRow+=thisRow[i]+"#&"
        }

        var operateData={
            type:"",
            new:newRow
        }

        if(editFlag) {
            operateData.old=oldRow
        }

        var result=syncDataToServer(thisTableName,(editFlag?EDIT_DATA:ADD_DATA),operateData)

        if(result.state){
            if (rowIndex >= 0 && rowIndex < rows.length) {
                var row = rows[rowIndex + 1];
                var cells = row.cells;
                for (var cellIndex = 0; cellIndex < cells.length - 1; cellIndex++) {
                    var cell = cells[cellIndex];
                    cell.innerHTML = thisRow[cellIndex];
                }
            } else {
                console.log("行索引无效：" + rowIndex);
            }
            alert("服务器回复："+result.responseMsg)
            if (editFlag) alert(lastRow + "\n更改为\n" + thisRow)
            else alert("新增行\n" + thisRow)
            button.textContent = "编辑"
        }else{
            alert("操作失败"+result.responseMsg)
        }
    }
}

function delRow(containerID, button) {
    var rowIndex = getRowIndex(button)
    var div = document.getElementById(containerID);
    var table = div.querySelector("table");
    var rows = table.rows;

    if (rowIndex >= 0 && rowIndex < rows.length) {
        var confirmation = confirm("您确定要删除这一行吗？");
        if (confirmation) {
            lastRow = getRow(containerID, rowIndex)

            var oldRow=""
            for(var i=0;i<lastRow.length;++i){
                oldRow+=lastRow[i]+"#&"
            }

            var operateData={
                type:"",
                old:oldRow
            }
            var result=syncDataToServer(thisTableName,DEL_DATA,operateData)
            if(result.state){
                rows[rowIndex + 1].remove();
                alert("删除成功")
                console.log("已删除行：" + rowIndex + "\n" + lastRow);
            }else{
                alert("删除失败:"+result.responseMsg)
            }

        } else {
            console.log("取消删除行：" + rowIndex);
        }
    } else {
        console.log("行索引无效：" + rowIndex);
    }
}

function addRow(containerID) {
    var div = document.getElementById(containerID);
    var table = div.querySelector("table");
    var colNum = table.rows[0].cells.length

    var row = document.createElement("tr");

    var rowData = new Array(colNum).fill("");
    alert(rowData.length)

    var edit_button;

    rowData.forEach(function (cellData, index) {
        var td = document.createElement("td");
        td.textContent = cellData;

        // 如果是最后一列，添加操作按钮
        if (index === rowData.length - 1) {
            edit_button = document.createElement("button");
            edit_button.textContent = "编辑";
            edit_button.style.borderRadius = "5px 0px 0px 5px"
            edit_button.onclick = function () {
                editRow(containerID, this)
            }
            var del_button = document.createElement("button");
            del_button.textContent = "删除";
            del_button.onclick = function () {
                delRow(containerID, this)
            }
            del_button.style.borderRadius = "0px 5px 5px 0px"
            var button_div = document.createElement("div");
            button_div.appendChild(edit_button)
            button_div.appendChild(del_button)
            td.appendChild(button_div);
        }
        row.appendChild(td);
    })

    table.appendChild(row);
    editRow(containerID, edit_button)
    for(var i=0;i<lastRow.length;++i){concat([""])
        lastRow[i]=""
    }
    console.log("已添加新行");
}

function getRowIndex(button) {//获取在第几行，数据从0开始
    var row = button.closest("tr");
    if (row) {
        var rowIndex = row.rowIndex - 1;
        return rowIndex;
    }
    return -1;
}

function getRow(containerID, rowIndex) {
    var div = document.getElementById(containerID);
    var table = div.querySelector("table");
    var rowData = [];
    if (table && rowIndex >= 0 && rowIndex < table.rows.length) {
        var cells = table.rows[rowIndex + 1].cells;

        for (var i = 0; i < cells.length - 1; i++) {
            var cellData = cells[i].textContent;
            rowData.push(cellData);
        }
    }
    return rowData;
}

function clearSelectElement(id){
    let selectElement = document.getElementById(id)
    while (selectElement.options.length > 0) {
        selectElement.remove(0);
    }
}



function refreshTable(containerID){
    var div=document.getElementById(containerID)//"tableContainer"
    while (div.firstChild) {
        div.removeChild(div.firstChild);
    }
    var data=getDataFromServer(thisTableName,thisCondition).responseMsg
    createTable(containerID, data);
}
