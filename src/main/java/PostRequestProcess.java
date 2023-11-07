import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class PostRequestProcess {
    protected static JSONObject requestJson;

    /**
     * 状态：系统已经初始化过了
     * **/
    public static final String STSYEM_HAS_INIT="SystemHasInit";

    /**状态：系统还未初始化**/
    public static final String STSYEM_NOT_INIT="SystemNotInit";

    /** 检查系统是否初始化**/
    public static final String CHECK_SYSTEM_STATE = "CheckSystemState";

    /** 系统初始化 **/
    public static final String SYSTEM_INIT = "SystemInit";

    /** 登录请求**/
    public static final String LOG_IN = "LogIn";

    /** 增加管理员 **/
    public static final String ADD_ADMIN = "AddAdmin";

    /** 修改管理员 **/
    public static final String EDIT_ADMIN = "EditAdmin";

    /** 删除管理员**/
    public static final String DEL_ADMIN = "DelAdmin";

    /**增加病患**/
    public static final String ADD_PATIENT = "AddPatient";

    /** 修改病患 **/
    public static final String EDIT_PATIENT = "EditPatient";

    /** 删除病患 **/
    public static final String DEL_PATIENT = "DelPatient";

    /** 增加病房**/
    public static final String ADD_WARD = "AddWard";

    /** 修改病房**/
    public static final String EDIT_WARD = "EditWard";

    /** 删除病房**/
    public static final String DEL_WARD = "DelWard";

    /** 增加床位 **/
    public static final String ADD_BED = "AddBed";

    /** 修改床位**/
    public static final String EDIT_BED = "EditBed";

    /** 删除床位 **/
    public static final String DEL_BED = "DelBed";

    /** 增加病例 **/
    public static final String ADD_CASE = "AddCase";

    /** 修改病例 **/
    public static final String EDIT_CASE = "EditCase";

    /** 删除病例 **/
    public static final String DEL_CASE = "DelCase";

    /** 调换病房 **/
    public static final String CHANGE_BED = "ChangeBed";

    /** 预定病房
     *
     */
    public static final String BOOKING_BED = "BookingBed";

    /** 增加医生
     */
    public static final String ADD_DOCTOR = "AddDoctor";

    /** 修改医生
     *
     */
    public static final String EDIT_DOCTOR = "EditDoctor";

    /** 删除医生
     *
     */
    public static final String DEL_DOCTOR = "DelDoctor";

    /** 增加护士
     *
     */
    public static final String ADD_NURSE = "AddNurse";

    /** 修改护士
     *
     */
    public static final String EDIT_NURSE = "EditNurse";

    /** 删除护士
     *
     */
    public static final String DEL_NURSE = "DelNurse";

    /** 制定医生值班表
     *
     */
    public static final String DEVELOP_DOCTOR_DUTY_SCHEDULE = "DevelopDoctorDutySchedule";

    /** 编辑医生值班表
     *
     */
    public static final String EDIT_DOCTOR_DUTY_SCHEDULE = "EditDoctorDutySchedule";
    /** 删除医生值班表
     *
     */
    public static final String DEL_DOCTOR_DUTY_SCHEDULE = "DelDoctorDutySchedule";

    /** 获取医生值班表**/
    public static final String GET_DOCTOR_DUTY_SCHEDULE = "GetDoctorDutySchedule";

    /** 制定护士值班表**/
    public static final String DEVELOP_NURSE_DUTY_SCHEDULE = "DevelopNurseDutySchedule";
    /**编辑护士值班表**/
    public static final String EDIT_NURSE_DUTY_SCHEDULE = "EditNurseDutySchedule";
    /**删除护士值班表**/
    public static final String DEL_NURSE_DUTY_SCHEDULE = "DelNurseDutySchedule";

    /** 获取护士值班表**/
    public static final String GET_NURSE_DUTY_SCHEDULE = "GetNurseDutySchedule";

    /** 获取病患信息**/
    public static final String GET_PATIENT_INFO = "GetPatientInfo";

    /** 获取病房信息**/
    public static final String GET_WARD_INFO = "GetWardInfo";

    /** 获取医生信息**/
    public static final String GET_DOCTOR_INFO = "GetDoctorInfo";

    /** 获取护士信息 **/
    public static final String GET_NURSE_INFO = "GetNurseInfo";

    /**获得床位信息**/
    public static final String GET_BED_INFO = "GetBedInfo";

    /** 获取病房预定信息 **/
    public static final String GET_BED_BOOKING_INFO = "GetWardBookingInfo";

    /**获取管理信息**/
    public static final String GET_ADMIN_INFO="GetAdminInfo";

    /**病例**/
    public static final String GET_CASES_INFO="GetCaseInfo";

    /**取消预约**/
    public static final String CANCEL_BOONING_WARD = "CancelBookingWard";

    /**入住情况**/
    public static final String ADD_CHECK_IN="AddCheckIn";

    public static final String EDIT_CHECK_IN="EditCheckIn";

    public static final String DEL_CHECK_IN="DelCheckIn";

    public static final String ADD_DEPARTMENT="AddDepartment";

    public static final String EDIT_DEPARTMENT="EditDepartment";

    public static final String DEL_DEPARTMENT="DelDepartment";

    public static final String GET_CHECK_IN_INFO = "GetCheckInInfo";

    public static final String GET_DEPARTMENT_INFO = "GetDepartmentInfo";

    public static final String EDIT_BOONING_WARD = "EditBookingWard";

    public static final String CHANGE_PASSWORD = "ChangePassword";

    public static final String CHECK_IN="CheckIn";

    public static final String CHECK_OUT="CheckOut";

    public static final String SELF_BOOKING = "SelfBooking";

    public static final String SELF_CANCEL_BOOKING = "SelfCancelBooking";

    public static final String SELF_SEARCH_BOOKING = "SelfSearchBooking";


   /**
    * 用于从通过HttpServletRequest对象发送的HTTP POST请求中提取并处理JSON请求中的"type"属性
    * 从JSONObject中提取并返回"type"属性的值
    * **/
    protected static String getPostType(HttpServletRequest request) throws IOException {
        // 获取请求的输入流
        InputStream requestBody = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody,StandardCharsets.UTF_8));
        StringBuilder jsonBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        System.out.println(jsonBuilder);

        requestJson = new JSONObject(jsonBuilder.toString());

        System.out.println(requestJson);

        return requestJson.getString("type");
    }

    /**用于检查系统的状态，是否初始化**/
    protected static void checkSystemState(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:
        String filePath = Server.CONFIG_FILE_PATH; // 配置文件的路径
        File file = new File(filePath);
        if (file.exists()) {
            response.getWriter().write(STSYEM_HAS_INIT);
        } else {
            response.getWriter().write(STSYEM_NOT_INIT);
        }
    }
    /**用于初始化系统**/
    protected static void systemInit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:成功就发送  init succ
        String IP="8.130.40.31";
        int PORT=26000;
        String dataBaseName=requestJson.getString("dataBaseName");
        String dbUsername=requestJson.getString("dbUsername");
        String dbPassword=requestJson.getString("dbPassword");
        String adminUsername=requestJson.getString("adminUsername");
        String adminPassword=requestJson.getString("adminPassword");
        String url = "jdbc:postgresql://" + IP + ":" + PORT + "/" + dataBaseName;
        Connection conn = null;
        try {
            Class.forName(JdbcOperation.JDBC_DRIVER_NAME);
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            makeDataBaseParams(IP,PORT,dataBaseName,dbUsername,dbPassword);
            Server.readParams();
            Server.initJDBC();
            //SystemRelationshipModel.initSystemRelationshipModel();
            SystemRelationshipModel.createSystemRelationshipModel();
            if(addAdmin(adminUsername,adminPassword)){
                response.getWriter().write("init succ");
            }
            response.getWriter().write("init fail");
        } catch (SQLException e) {
            response.getWriter().write("init fail");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected static void changeBed(HttpServletRequest request, HttpServletResponse response){
        //TODO:病人更换床位
    }

    protected static String decodePassword(String ciphertext){
        //TODO:密码解码
        if(ciphertext==null)return null;
        String plaintext="";
        return plaintext;
    }
    /**
     * 账号密码登录认证
     * **/
    protected static boolean identityAuthentication(String username,String password){
        //TODO:身份认证
        ITable table = SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);
        System.out.println(username + password);
        String condition = "username='" + username + "' AND password='" + password + "'";
        return table.selectData(condition).size() != 0;
    }

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //模型构建
        SystemRelationshipModel.initSystemRelationshipModel();
        //TODO:处理POST请求
        String type=getPostType(request);
        switch (type){
            // 检查系统是否初始化ok
            case CHECK_SYSTEM_STATE:
                checkSystemState(request,response);
                break;

            // 系统初始化ok
            case SYSTEM_INIT:
                systemInit(request,response);
                break;

            // 登录请求ok
            case LOG_IN:
                System.out.println("登录");
                logIn(request, response);
                break;

            // 增加管理员ok
            case ADD_ADMIN:
                addAdmin(request,response);
                break;

            // 修改管理员ok
            case EDIT_ADMIN:
                editAdmin(request,response);
                break;

            // 删除管理员ok
            case DEL_ADMIN:
                delAdmin(request,response);
                break;

            // 增加病患ok
            case ADD_PATIENT:
                addPatient(request,response);
                break;

            // 修改病患ok
            case EDIT_PATIENT:
                editPatient(request,response);
                break;

            // 删除病患ok
            case DEL_PATIENT:
                delPatient(request,response);
                break;

            // 增加病房
            case ADD_WARD:
                addWard(request,response);
                break;

            // 修改病房
            case EDIT_WARD:
                editWard(request,response);
                break;

            // 删除病房
            case DEL_WARD:
                delWard(request,response);
                break;

            //增加床位
            case ADD_BED:
                addBed(request,response);
                break;

            //编辑床位
            case EDIT_BED:
                editBed(request,response);
                break;

            //删除床位
            case DEL_BED:
                delBed(request,response);
                break;

            // 调换床位
            case CHANGE_BED:
                changeBed(request,response);
                break;

            // 预定床位
            case BOOKING_BED:
                bookingBed(request,response);
                break;

            // 增加医生
            case ADD_DOCTOR:
                addDoctor(request,response);
                break;

            // 修改医生
            case EDIT_DOCTOR:
                editDoctor(request,response);
                break;

            // 删除医生
            case DEL_DOCTOR:
                delDoctor(request,response);
                break;

            // 增加护士
            case ADD_NURSE:
                addNurse(request,response);
                break;

            // 修改护士
            case EDIT_NURSE:
                editNurse(request,response);
                break;

            // 删除护士
            case DEL_NURSE:
                delNurse(request,response);
                break;

            // 制定医生值班表
            case DEVELOP_DOCTOR_DUTY_SCHEDULE:
                developDoctorDutySchedule(request,response);
                break;

            case EDIT_DOCTOR_DUTY_SCHEDULE:
                editDoctorDutySchedule(request,response);
                break;

            case DEL_DOCTOR_DUTY_SCHEDULE:
                delDoctorDutySchedule(request,response);
                break;

            // 获取医生值班表
            case GET_DOCTOR_DUTY_SCHEDULE:
                getDoctorDutySchedule(request,response);
                break;

            // 制定护士值班表
            case DEVELOP_NURSE_DUTY_SCHEDULE:
                developNurseDutySchedule(request,response);
                break;

            case EDIT_NURSE_DUTY_SCHEDULE:
                editNurseDutySchedule(request,response);
                break;

            case DEL_NURSE_DUTY_SCHEDULE:
                delNurseDutySchedule(request,response);
                break;

            // 获取护士值班表
            case GET_NURSE_DUTY_SCHEDULE:
                getNurseDutySchedule(request,response);
                break;

            // 获取病患信息
            case GET_PATIENT_INFO:
                getPatientInfo(request,response);
                break;

            // 获取病房信息
            case GET_WARD_INFO:
                getWardInfo(request,response);
                break;

            // 获取医生信息
            case GET_DOCTOR_INFO:
                getDoctorInfo(request,response);
                break;

            // 获取护士信息
            case GET_NURSE_INFO:
                getNurseInfo(request,response);
                break;

            // 获取病房预定信息
            case GET_BED_BOOKING_INFO:
                getBedBookingInfo(request,response);
                break;

            case GET_ADMIN_INFO:
                getAdminInfo(request,response);
                break;

            case ADD_CASE:
                addCase(request,response);
                break;

            case EDIT_CASE:
                editCase(request,response);
                break;

            case DEL_CASE:
                delCase(request,response);
                break;

            case GET_CASES_INFO:
                getCaseInfo(request,response);
                break;

            case CANCEL_BOONING_WARD:
                cancelBookingWard(request,response);
                break;

            case EDIT_BOONING_WARD:
                editBookingWard(request,response);
                break;

            case ADD_CHECK_IN:
                addCheckIn(request,response);
                break;

            case EDIT_CHECK_IN:
                editCheckIn(request,response);
                break;

            case DEL_CHECK_IN:
                delCheckIn(request,response);
                break;

            case ADD_DEPARTMENT:
                addDepartment(request,response);
                break;

            case EDIT_DEPARTMENT:
                editDepartment(request,response);
                break;

            case DEL_DEPARTMENT:
                delDepartment(request,response);
                break;

            case GET_BED_INFO:
                getBedInfo(request,response);
                break;

            case GET_CHECK_IN_INFO:
                getCheckInInfo(request,response);
                break;

            case GET_DEPARTMENT_INFO:
                getDepartmentInfo(request,response);
                break;

            case CHANGE_PASSWORD:
                changePassword(request,response);
                break;

            case CHECK_IN:
                checkIn(request,response);
                break;

            case CHECK_OUT:
                checkOut(request,response);
                break;

            case SELF_BOOKING:
                selfBooking(request,response);
                break;

            case SELF_CANCEL_BOOKING:
                selfCancelBooking(request,response);
                break;

            case SELF_SEARCH_BOOKING:
                selfSearchBooking(request,response);
                break;

            default:
                response.getWriter().write("error command");
        }

    }

    private static void selfSearchBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:自主查询预定
        //{"type":"SelfSearchBooking","info":"123"}
        ITable patientTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);
        ITable bookingTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        String state;
        String detail;
        String booking_number=requestJson.getString("info");
        ArrayList<ArrayList<String>>result_t=bookingTable.selectData("booking_number='"+booking_number+"'");
        if(result_t.size()>0){
            String patient_ID=result_t.get(0).get(1);
            String bunk_number=result_t.get(0).get(2);
            String is_check_in=result_t.get(0).get(3);
            ArrayList<ArrayList<String>>result_t2=patientTable.selectData("patient_ID='"+patient_ID+"'");
            String patient_name=result_t2.get(0).get(1);
            String patient_sex=result_t2.get(0).get(2);
            String patient_age=result_t2.get(0).get(3);
            ArrayList<ArrayList<String>>result_t3=bunkTable.selectData("bunk_number='"+bunk_number+"'");
            String ward_number=result_t3.get(0).get(1);
            state="succ";
            detail="预定号："+booking_number+"\n" +
                    "床位号："+bunk_number+"\n" +
                    "病房号："+ward_number+"\n" +
                    "病人号："+patient_ID+"\n" +
                    "姓名："+patient_name+"\n" +
                    "性别："+patient_sex+"\n" +
                    "年龄："+patient_age+"\n" +
                    "入住："+is_check_in;
        }else{
            state="fail";
            detail="预定号或预定信息不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private static void selfCancelBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:自主取消预定
        //{"info":"123#&456","type":"SelfCancelBooking"}
        ITable patientTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);
        ITable bookingTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        String state;
        String detail;
        String[] info=requestJson.getString("info").split("#&");
        String booking_number=info[0];
        String patient_ID=info[1];
        ArrayList<ArrayList<String>>result_t=bookingTable.selectData("booking_number='"+booking_number+"' AND patient_ID='"+patient_ID+"'");
        if(result_t.size()>0){
            String is_check_in=result_t.get(0).get(3);
            if(Objects.equals(is_check_in, "是")){
                state="fail";
                detail="您已经入住，当前如过需要停止住院，需要到住院部办理出院";
            }else{
                String bunk_number=result_t.get(0).get(2);
                HashMap<String,String>editMap=new HashMap<>();
                editMap.put("is_check_in","否");
                bunkTable.updateData(editMap,"bunk_number='"+bunk_number+"'");
                bookingTable.deleteData("booking_number='"+booking_number+"'");
                patientTable.deleteData("patient_ID='"+patient_ID+"'");
                state="succ";
                detail="撤销成功";
            }
        }else{
            state="fail";
            detail="预约号不存在或者信息不匹配";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private static void selfBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:自主预定
        //{"type":"SelfBooking","info":"P00001#&B00010"}
        ITable patientTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);
        ITable bookingTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        String[] info= requestJson.getString("info").split("#&");
        String patient_ID=info[0];
        String bunk_number=info[1];
        String booking_number=getNextPrimaryKey(bookingTable,bookingTable.selectData());
        String state;
        String detail;

        if(patientTable.selectData("patient_ID='"+patient_ID+"'").size()>0){
            bookingTable.insertData(new ArrayList<>(Arrays.asList(booking_number,patient_ID,bunk_number,"否")));
            HashMap<String,String>editMap=new HashMap<>();
            editMap.put("is_check_in","是");
            bunkTable.updateData(editMap,"bunk_number='"+bunk_number+"'");
            state="succ";
            detail="请牢记预定号："+booking_number;
        }else{
            state="fail";
            detail="该病人号不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private static void checkOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:病人出院（删除预定表的预定记录）
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);

        String state;
        String detail;
        String booking_number=requestJson.getString("booking_number");

        if(table.selectData("booking_number='"+booking_number+"'").size()>0){
            ArrayList<ArrayList<String>>result_t=table.selectData("booking_number='"+booking_number+"'");
            String bunk_number=result_t.get(0).get(2);

            HashMap<String,String>editMap=new HashMap<>();
            editMap.put("is_check_in","否");
            bunkTable.updateData(editMap,"bunk_number='"+bunk_number+"'");
            table.deleteData("booking_number='"+booking_number+"'");
            state="succ";
            detail="出院成功";
        }else{
            state="fail";
            detail="患者尚未预定病房";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private static void checkIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:病人入住（修改预定表真实入住情况）
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        String state;
        String detail;
        String booking_number=requestJson.getString("booking_number");

        if(table.selectData("booking_number='"+booking_number+"'").size()>0){
            if(table.selectData("booking_number='"+booking_number+"' AND is_check_in='"+"是"+"'").size()>0){
                state="fail";
                detail="该预定已经入住";
            }else{
                HashMap<String,String>editMap=new HashMap<>();
                editMap.put("is_check_in","是");
                table.updateData(editMap,"booking_number='"+booking_number+"'");
                state="succ";
                detail="入住成功";
            }
        }else{
            state="fail";
            detail="患者尚未预定病房";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void makeDataBaseParams(String IP,int PORT,String dateBaseName,String username,String password){
        Properties properties = new Properties();
        properties.setProperty("db.IP", IP);
        properties.setProperty("db.PORT", String.valueOf(PORT));
        properties.setProperty("db.dateBaseName", dateBaseName);
        properties.setProperty("db.username", username);
        properties.setProperty("db.password", password);
        try {
            FileOutputStream outputStream = new FileOutputStream(Server.CONFIG_FILE_PATH);
            properties.store(outputStream, "Database Configuration");
            outputStream.close();
            System.out.println("配置文件已创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void testSelect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ITable table = SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);
        ArrayList<ArrayList<String>> data = table.selectData();
        data.add(0, table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void logIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ITable table = SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);
        String username = requestJson.getString("username");
        String password = requestJson.getString("password");
        System.out.println(username + password);
        String condition = "username='" + username + "' AND password='" + password + "'";
        if (table.selectData(condition).size() != 0) {
            response.getWriter().write("log succ");
        } else {
            response.getWriter().write("log fail");
        }
    }







    protected static void bookingBed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:预定床位
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        //{"new":"P00001##测试-1#&B00002#&是","type":"BookingBed"}
        ArrayList<ArrayList<String>>result_t=table.selectData();
        String new_info=requestJson.getString("new");

        //new Column("booking_number", "VARCHAR(10)"),//预定编号
        //            new Column("patient_ID", "VARCHAR(10)"),//病人号
        //            new Column("bunk_number", "VARCHAR(4)"),//床位号
        //            new Column("is_check_in", "VARCHAR(2)")//入住否

        String booking_number=getNextPrimaryKey(table,result_t);
        String patient_ID=new_info.split("#&")[0];
        String bunk_number=new_info.split("#&")[1];
        String is_check_in=new_info.split("#&")[2];

        String state;
        String detail;

        //查询此床位是否空？
        ArrayList<ArrayList<String>>result_t_2=bunkTable.selectData("bunk_number='"+bunk_number+"'");
        if(result_t_2.size()>0){
            if(Objects.equals(result_t_2.get(0).get(2), "否")){
                if(table.insertData(new ArrayList<>(Arrays.asList(booking_number,patient_ID,bunk_number,is_check_in)))){

                    HashMap<String,String> editMap=new HashMap<>();
                    editMap.put("is_check_in","是");
                    if(bunkTable.updateData(editMap,"bunk_number='"+bunk_number+"'")){
                        state="succ";
                        detail="添加成功";
                    }else{
                        state="fail";
                        detail="添加失败";
                    }
                }else{
                    state="fail";
                    detail="预定号已存在";
                }
            }else{
                state="fail";
                detail="病房已被占用";
            }

        }else{
            state="fail";
            detail="病房不存在";
        }






        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void developDoctorDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:制定医生值班表
        String[] startDate=requestJson.getString("startDate").split("-");
        int[] date=new int[3];
        date[0]=Integer.parseInt(startDate[0]);
        date[1]=Integer.parseInt(startDate[1]);
        date[2]=Integer.parseInt(startDate[2]);
        try {
            DoctorDutyScheduler.makeDoctorDuty(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", "succ");
        jsonResponse.put("detail","okk");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void developNurseDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:制定护士值班表
        String[] startDate=requestJson.getString("startDate").split("-");
        int[] date=new int[3];
        date[0]=Integer.parseInt(startDate[0]);
        date[1]=Integer.parseInt(startDate[1]);
        date[2]=Integer.parseInt(startDate[2]);
        try {
            NurseDutyScheduler.makeNurseDuty(date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", "succ");
        jsonResponse.put("detail","okk");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加管理员
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);

        String new_info=requestJson.getString("new");
        String new_username=new_info.split("#&")[0];
        String new_password=new_info.split("#&")[1];

        String condition="username='"+new_username+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="用户名已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(new_username,new_password)));
            state="succ";
            detail="插入成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加病患
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);

        ArrayList<ArrayList<String>>result_t=table.selectData();
        String new_info=requestJson.getString("new");
        String new_id=getNextPrimaryKey(table,result_t);
        String new_name=new_info.split("#&")[0];
        String new_sex=new_info.split("#&")[1];
        String new_age=new_info.split("#&")[2];

        String condition="patient_ID='"+new_id+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="病患号已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(new_id,new_name,new_sex,new_age)));
            state="succ";
            detail="操作成功\n病人号："+new_id;
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addWard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加病房
        ITable wardTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_INFO_TABLE);
        ITable departmentTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);
        //{"new":"w00001#&1#&","type":"AddWard"}
        String new_info_t=requestJson.getString("new");
        ArrayList<ArrayList<String>> result_t=wardTable.selectData();
        String nextPrimaryKey=getNextPrimaryKey(wardTable,result_t);
        new_info_t=nextPrimaryKey+"#&"+new_info_t;
        String[] new_info=new_info_t.split("#&");

        String condition="department_ID='"+new_info[1]+"'";
        String state;
        String detail;
        if(!departmentTable.selectData(condition).isEmpty()){
            if(wardTable.insertData(new ArrayList<>(Arrays.asList(new_info)))){
                state="succ";
                detail="添加成功";
            }else{
                state="fail";
                detail="病房已存在";
            }
        }else{
            state="fail";
            detail="科室号不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addBed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加床位
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);


        ArrayList<ArrayList<String>>result_t=table.selectData();

        String new_info=requestJson.getString("new");
        String bunk_number=getNextPrimaryKey(table,result_t);
        String ward_number=new_info.split("#&")[0];
        String is_check_in=new_info.split("#&")[1];

        String condition="bunk_number='"+bunk_number+"' AND ward_number='"+ward_number+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="床位已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(bunk_number,ward_number,is_check_in)));
            state="succ";
            detail="添加成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加医生
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_INFO_TABLE);

        ArrayList<ArrayList<String>>result_t=table.selectData();

        String new_info=requestJson.getString("new");
        String doctor_ID=getNextPrimaryKey(table,result_t);
        String department_ID=new_info.split("#&")[0];
        String doctor_name=new_info.split("#&")[1];
        String doctor_sex=new_info.split("#&")[2];
        String doctor_age=new_info.split("#&")[3];

        String condition="doctor_ID='"+doctor_ID+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="医生已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(doctor_ID,department_ID,doctor_name,doctor_sex,doctor_age)));
            state="succ";
            detail="添加成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addNurse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加护士
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_INFO_TABLE);

        ArrayList<ArrayList<String>>result_t=table.selectData();

        String new_info=requestJson.getString("new");
        String nurse_ID=getNextPrimaryKey(table,result_t);
        String department_ID=new_info.split("#&")[0];
        String nurse_name=new_info.split("#&")[1];
        String nurse_sex=new_info.split("#&")[2];
        String nurse_age=new_info.split("#&")[3];

        String condition="nurse_ID='"+nurse_ID+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="护士已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(nurse_ID,department_ID,nurse_name,nurse_sex,nurse_age)));
            state="succ";
            detail="添加成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addCase(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:增加病例
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.CASES_INFO_TABLE);

        String new_info=requestJson.getString("new");
        String hospital_number=new_info.split("#&")[0];
        String patient_ID=new_info.split("#&")[1];
        String doctor_ID=new_info.split("#&")[2];

        String condition="hospital_number='"+hospital_number+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()>0){
            state="fail";
            detail="已存在";
        }else{
            table.insertData(new ArrayList<>(Arrays.asList(hospital_number,patient_ID,doctor_ID)));
            state="succ";
            detail="添加成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void addCheckIn(HttpServletRequest request, HttpServletResponse response){
        //TODO:增加入住
    }

    protected static void addDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //{"new":"1#&2#&3#&4#&","type":"AddDepartment"}
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);
        String newInfo=requestJson.getString("new");
        String state;
        String detail;

        ArrayList<ArrayList<String>> result_t=table.selectData();
        newInfo=getNextPrimaryKey(table,result_t)+"#&"+newInfo;
        if(table.insertData(new ArrayList<>(Arrays.asList(newInfo.split("#&"))))){
            state="succ";
            detail="添加成功";
        }else{
            state="fail";
            detail="已存在";
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static boolean addAdmin(String username,String password){
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);
        return table.insertData(new ArrayList<>(Arrays.asList(username, password)));
    }







    protected static void editAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:编辑管理员
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);

        String lod_info=requestJson.getString("old");
        String new_info=requestJson.getString("new");
        String old_username=lod_info.split("#&")[0];
        String old_password=lod_info.split("#&")[1];
        String new_username=new_info.split("#&")[0];
        String new_password=new_info.split("#&")[1];

        String condition="username='"+old_username+"' AND password='"+old_password+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()==0){
            state="fail";
            detail="未找到该用户";
        }else{
            if(!Objects.equals(old_username, new_username)){
                state="fail";
                detail="不能修改用户名";
            }else{
                HashMap<String,String> updateMap=new HashMap<String,String>(){{put("password",new_password);}};
                table.updateData(updateMap,condition);
                state="succ";
                detail="修改成功";
            }
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:编辑病患
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);

        String new_info=requestJson.getString("new");
        String new_id=new_info.split("#&")[0];
        String new_name=new_info.split("#&")[1];
        String new_sex=new_info.split("#&")[2];
        String new_age=new_info.split("#&")[3];

        String condition="patient_ID='"+new_id+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()==0){
            state="fail";
            detail="病患号不存在";
        }else{
            HashMap<String,String> updateMap=new HashMap<String,String>(){{
                put("patient_ID",new_id);
                put("patient_name",new_name);
                put("patient_sex",new_sex);
                put("patient_age",new_age);
            }};
            table.updateData(updateMap,condition);
            state="succ";
            detail="修改成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editWard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:修改病房
        ITable wardTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_INFO_TABLE);
        ITable departmentTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);
        //{"new":"w00001#&1#&","type":"AddWard"}

        String[] new_info=requestJson.getString("new").split("#&");
        String condition="department_ID='"+new_info[1]+"'";
        String state;
        String detail;
        HashMap<String,String>editMap=new HashMap<>();
        editMap.put("department_ID",new_info[1]);
        String condition2="ward_number='"+new_info[0]+"'";
        //System.out.println(condition2);
        if(!departmentTable.selectData(condition).isEmpty()){
            if(wardTable.updateData(editMap,condition2)){
                state="succ";
                detail="修改成功";
            }else{
                state="fail";
                detail="病房不存在";
            }
        }else{
            state="fail";
            detail="科室号不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editBed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:修改床位
        String state;
        String detail;
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        //{"new":"B00003#&W00001#&是","type":"EditBed"}

        String[] new_info=requestJson.getString("new").split("#&");
        String condition="bunk_number='"+new_info[0]+"'";

        HashMap<String,String>editMap=new HashMap<>();
        editMap.put("ward_number",new_info[1]);
        editMap.put("is_check_in",new_info[2]);

        if(bunkTable.updateData(editMap,condition)){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="床位不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:编辑医生信息
        String state;
        String detail;
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_INFO_TABLE);
        //{"new":"B00003#&W00001#&是","type":"EditBed"}

        String[] new_info=requestJson.getString("new").split("#&");
        String condition="doctor_ID='"+new_info[0]+"'";
        HashMap<String,String>editMap=new HashMap<>();
        editMap.put("department_ID",new_info[1]);
        editMap.put("doctor_name",new_info[2]);
        editMap.put("doctor_sex",new_info[3]);
        editMap.put("doctor_age",new_info[4]);

        if(bunkTable.updateData(editMap,condition)){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="医生不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    private static void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:管理员改密码
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);
        String state;
        String detail;
        //{"oldPassword":"12345678","newPassword":"20030713","type":"ChangePassword","username":"hwmis"}
        String username=requestJson.getString("username");
        String oldPassword=requestJson.getString("oldPassword");
        String newPassword=requestJson.getString("newPassword");
        if(table.selectData("username='"+username+"' AND password='"+oldPassword+"'").size()>0){
            HashMap<String,String>editMap=new HashMap<>();
            editMap.put("password",newPassword);
            if(table.updateData(editMap,"username='"+username+"'")){
                state="succ";
                detail="修改成功";
            }else{
                state="fail";
                detail="修改失败,网络异常";
            }

        }else{
            state="fail";
            detail="修改失败,原密码错误";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editNurse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:修改护士
        String state;
        String detail;
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_INFO_TABLE);
        //{"new":"B00003#&W00001#&是","type":"EditBed"}

        String[] new_info=requestJson.getString("new").split("#&");
        String condition="nurse_ID='"+new_info[0]+"'";
        HashMap<String,String>editMap=new HashMap<>();
        editMap.put("department_ID",new_info[1]);
        editMap.put("nurse_name",new_info[2]);
        editMap.put("nurse_sex",new_info[3]);
        editMap.put("nurse_age",new_info[4]);

        if(table.updateData(editMap,condition)){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="护士不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editCase(HttpServletRequest request, HttpServletResponse response){
        //TODO:修改病例
    }

    protected static void editDoctorDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:编辑医生值班表
        //{"new":"D00003#&D00001#&2023-10-06 08:00:00#&D00001#&day","type":"EditDoctorDutySchedule"}
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_DUTY_SCHEDULE_TABLE);
        String state;
        String detail;
        String[] new_info=requestJson.getString("new").split("#&");
        String doctor_duty_id=new_info[0];
        String doctor_id=new_info[1];
        HashMap<String,String> editMap=new HashMap();
        editMap.put("doctor_id",doctor_id);
        if(table.updateData(editMap,"doctor_duty_id='"+doctor_duty_id+"'")){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="修改失败";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editNurseDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:编辑护士值班表
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_DUTY_SCHEDULE_TABLE);
        String state;
        String detail;
        String[] new_info=requestJson.getString("new").split("#&");
        String nurse_duty_id=new_info[0];
        String  nurse_id=new_info[1];
        HashMap<String,String> editMap=new HashMap();
        editMap.put(" nurse_id", nurse_id);
        if(table.updateData(editMap," nurse_duty_id='"+ nurse_duty_id+"'")){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="修改失败";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editBookingWard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:修改预约床位
        String state;
        String detail;
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);
        ITable bookingTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        String[] new_info=requestJson.getString("new").split("#&");
        //{"new":"W00001#&P00001#&B00001#&是","type":"EditBookingWard"}
        String booking_number=new_info[0];
        String new_patient_ID=new_info[1];
        String new_bunk_number=new_info[2];
        String new_check_in=new_info[3];

        ArrayList<ArrayList<String>> result_t=bookingTable.selectData("booking_number='"+booking_number+"'");
        String old_patient_ID=result_t.get(0).get(1);
        String old_bunk_number=result_t.get(0).get(2);
        String old_check_in=result_t.get(0).get(3);

        state="succ";
        detail="修改成功";

        if(!Objects.equals(new_bunk_number, old_bunk_number)){
            HashMap<String,String>editMapOfBooking=new HashMap<>();
            editMapOfBooking.put("bunk_number",new_bunk_number);
            String condition="booking_number='"+booking_number+"'";
            if(bookingTable.updateData(editMapOfBooking,condition)){
                HashMap<String,String>editMapOfBunk=new HashMap<>();
                editMapOfBunk.put("is_check_in","否");
                String condition2="bunk_number='"+old_bunk_number+"'";
                bunkTable.updateData(editMapOfBunk,condition2);//老床位置空
                editMapOfBunk.put("is_check_in","是");
                String condition3="bunk_number='"+new_bunk_number+"'";
                bunkTable.updateData(editMapOfBunk,condition3);//老床位标记为占用
                state="succ";
                detail="修改成功";
            }else{
                state="fail";
                detail="修改失败";
            }
        }

        if(!Objects.equals(new_check_in, old_check_in)){
            HashMap<String,String>editMapOfBooking=new HashMap<>();
            editMapOfBooking.put("is_check_in",new_check_in);
            String condition="booking_number='"+booking_number+"'";
            bookingTable.updateData(editMapOfBooking,condition);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void editCheckIn(HttpServletRequest request, HttpServletResponse response){
        //TODO:入住
    }

    protected static void editDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);
        //{"old":"D00002#&123#&1#&1#&","type":"DelDepartment"}

        String[] new_info=requestJson.getString("new").split("#&");
        String condition="department_ID='"+new_info[0]+"'";
        String state;
        String detail;
        HashMap<String,String> editMap=new HashMap<>();
        for(int i=0;i<table.getColumnNameList().size();++i){
            editMap.put(table.getColumnNameList().get(i),new_info[i]);
        }
        if(table.updateData(editMap,condition)){
            state="succ";
            detail="修改成功";
        }else{
            state="fail";
            detail="不存在的科室";
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }







    protected static void delAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除管理员
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);

        String old_info=requestJson.getString("old");
        String old_username=old_info.split("#&")[0];
        String old_password=old_info.split("#&")[1];

        String condition="username='"+old_username+"' AND password='"+old_password+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        System.out.println(table.selectData());
        if((table.selectData()).size()<2){
            state="fail";
            detail="管理员账户总数不能少于一个";
        }else if(result.size()==0){
            state="fail";
            detail="未找到该用户";
        }else{
            table.deleteData(condition);
            if(table.selectData(condition).size()==0){
                state="succ";
                detail="删除成功";
            }else{
                state="fail";
                detail="未知错误";
            }
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除病患
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);

        String new_info=requestJson.getString("old");
        String new_id=new_info.split("#&")[0];

        String condition="patient_ID='"+new_id+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;

        if(result.size()==0){
            state="fail";
            detail="未找到该病人";
        }else{
            table.deleteData(condition);
            state="succ";
            detail="删除成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delWard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除病房
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_INFO_TABLE);

        String old_info=requestJson.getString("old");
        String ward_number=old_info.split("#&")[0];
        String department_ID=old_info.split("#&")[1];

        String condition="ward_number='"+ward_number+"' AND department_ID='"+department_ID+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()==0){
            state="fail";
            detail="病房不存在";
        }else{
            table.deleteData(condition);
            state="succ";
            detail="删除成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delBed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除床位
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);

        String old_info=requestJson.getString("old");
        String bunk_number=old_info.split("#&")[0];
        String ward_number=old_info.split("#&")[1];

        String condition="bunk_number='"+bunk_number+"' AND ward_number='"+ward_number+"'";
        ArrayList<ArrayList<String>> result=table.selectData(condition);
        String state;
        String detail;
        if(result.size()==0){
            state="fail";
            detail="床位不存在";
        }else{
            table.deleteData(condition);
            state="succ";
            detail="删除成功";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除医生
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_INFO_TABLE);

        String old_info=requestJson.getString("old");
        String doctor_ID=old_info.split("#&")[0];
        String condition="doctor_ID='"+doctor_ID+"'";

        String state;
        String detail;
        if(table.deleteData(condition)){
            state="succ";
            detail="删除成功";
        }else{
            state="fail";
            detail="床位不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delNurse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:删除护士
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_INFO_TABLE);

        String old_info=requestJson.getString("old");
        String nurse_ID=old_info.split("#&")[0];
        String condition="nurse_ID='"+nurse_ID+"'";

        String state;
        String detail;
        if(table.deleteData(condition)){
            state="succ";
            detail="删除成功";
        }else{
            state="fail";
            detail="床位不存在";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delCase(HttpServletRequest request, HttpServletResponse response){
        //TODO:删除病例
    }

    protected static void delDoctorDutySchedule(HttpServletRequest request, HttpServletResponse response){
        //TODO:删除医生值班表
    }

    protected static void delNurseDutySchedule(HttpServletRequest request, HttpServletResponse response){
        //TODO:删除护士值班表
    }

    protected static void cancelBookingWard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:取消预约床位
        ITable bookingTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);
        ITable bunkTable=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);

        String[] old_info=requestJson.getString("old").split("#&");
        String booking_number=old_info[0];
        String bunk_number=old_info[2];
        String state;
        String detail;
        String condition="booking_number='"+booking_number+"'";
        if(bookingTable.deleteData(condition)){
            HashMap<String,String>editMap=new HashMap<>();
            editMap.put("is_check_in","否");
            String condition2="bunk_number='"+bunk_number+"'";
            bunkTable.updateData(editMap,condition2);
            state="succ";
            detail="删除成功";
        }else{
            state="fail";
            detail="删除失败";
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    protected static void delCheckIn(HttpServletRequest request, HttpServletResponse response){
        //TODO:入住
    }

    protected static void delDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);
        //{"old":"D00002#&123#&1#&1#&","type":"DelDepartment"}

        String old_info=requestJson.getString("old");
        String condition="department_ID='"+old_info.split("#&")[0]+"'";
        String state;
        String detail;
        if(table.deleteData(condition)){
            state="succ";
            detail="删除成功";
        }else{
            state="fail";
            detail="不存在的科室";
        }
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("state", state);
        jsonResponse.put("detail",detail);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }








    protected static void getAdminInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取管理员信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.ADMIN_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getBedInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.BUNK_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getCheckInInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.CHECK_IN_SITUATION_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getDepartmentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取科室信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getNurseDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取护士值班表
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_DUTY_SCHEDULE_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getPatientInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取病患信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.PATIENT_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getWardInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取病房信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getDoctorInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取医生信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getNurseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取护士信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getBedBookingInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取床位预定信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.WARD_BOOKING_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getCaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取病例信息
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.CASES_INFO_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected static void getDoctorDutySchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO:获取医生值班表
        String condition=requestJson.getString("condition");

        ITable admin_table=SystemRelationshipModel.getTable(ISystemRelationshipModel.DOCTOR_DUTY_SCHEDULE_TABLE);

        ArrayList<ArrayList<String>> data=admin_table.selectData(condition);

        data.add(0, admin_table.getColumnNameList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }


    protected static String getNextPrimaryKey(ITable table,ArrayList<ArrayList<String>> result_t){
        List<String> result_t2 = new ArrayList<>();
        for(int i=0;i<result_t.size();++i){
            result_t2.add(result_t.get(i).get(0));
        }
        Collections.sort(result_t2, new NumericComparator());
        String letter = null;
        int num;
        int length;
        if(result_t2.size()>0){
            letter=String.valueOf(result_t2.get(0).charAt(0));
            num=Integer.parseInt(result_t2.get(0).substring(1))+1;
            length=result_t2.get(0).length();
        }else{
            switch(table.getTableName()){
                case ISystemRelationshipModel.ADMIN_INFO_TABLE:
                    letter=String.valueOf('A');
                    break;

                case ISystemRelationshipModel.PATIENT_INFO_TABLE:
                    letter=String.valueOf('P');
                    break;

                case ISystemRelationshipModel.WARD_INFO_TABLE:
                    letter=String.valueOf('W');
                    break;

                case ISystemRelationshipModel.BUNK_INFO_TABLE:
                    letter=String.valueOf('B');
                    break;

                case ISystemRelationshipModel.CASES_INFO_TABLE:
                    letter=String.valueOf('C');
                    break;

                case ISystemRelationshipModel.WARD_BOOKING_TABLE:
                    letter=String.valueOf('W');
                    break;

                case ISystemRelationshipModel.DOCTOR_INFO_TABLE:
                    letter=String.valueOf('D');
                    break;

                case ISystemRelationshipModel.NURSE_INFO_TABLE:
                    letter=String.valueOf('N');
                    break;

                case ISystemRelationshipModel.DOCTOR_DUTY_SCHEDULE_TABLE:
                    letter=String.valueOf('D');
                    break;

                case ISystemRelationshipModel.NURSE_DUTY_SCHEDULE_TABLE:
                    letter=String.valueOf('N');
                    break;

                case ISystemRelationshipModel.CHECK_IN_SITUATION_TABLE:
                    letter=String.valueOf('C');
                    break;

                case ISystemRelationshipModel.DEPARTMENT_INFO_TABLE:
                    letter=String.valueOf('D');
                    break;
            }
            num=1;
            length=6;
        }
        return generateFixedLengthString(letter,num,length);
    }

    public static String generateFixedLengthString(String letter, int number, int length) {
        // 将数字部分格式化为固定长度的字符串（补零）
        String formattedNumber = String.format("%0" + (length - 1) + "d", number);

        // 组合字母和数字部分
        String code = letter + formattedNumber;

        return code;
    }


}

class NumericComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        // 提取数字部分，并将其解析为整数
        int num1 = Integer.parseInt(s1.substring(1));
        int num2 = Integer.parseInt(s2.substring(1));

        return num2 - num1;  // 按照数字大小进行比较
    }
}