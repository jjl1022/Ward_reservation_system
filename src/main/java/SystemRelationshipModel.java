import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SystemRelationshipModel {
    protected static ArrayList<Table> tableList = new ArrayList<>();

    protected static ArrayList<Column> DEFAULT_ADMIN_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //超级管理员
            new Column("username", "VARCHAR(20)"),
            new Column("password", "VARCHAR(20)")
    ));

    protected static String DEFAULT_ADMIN_INFO_TABLE_ADDITION="PRIMARY KEY (username)";
/*
关系模型
有以下
1.科室（一些基本信息）
2.病房（一些基本信息，属于某个科室，床位入住情况）
3.医生（一些基本信息）
4.护士（一些基本信息）
5.患者（一些基本信息）
6.病房预定（一些基本信息，是否入住）
7.医生值班（一些基本信息）
8.护士值班（一些基本信息）
一个病房有3个床位（这个怎么用关系模式表示），一个病人只能预定一个床位，
 */
//PRIMARY KEY (order_id, customer_id)
//FOREIGN KEY (student_id) REFERENCES students(id)

    protected static ArrayList<Column> DEFAULT_DEPARTMENT_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //科室
            new Column("department_ID", "VARCHAR(10)"),//科室号
            new Column("department_name", "VARCHAR(50)"),//科室名
            new Column("department_address", "VARCHAR(50)"),//科室地址
            new Column("department_phone", "VARCHAR(15)")//科室电话
    ));

    protected static String DEFAULT_DEPARTMENT_INFO_TABLE_ADDITION="PRIMARY KEY (department_ID)";

    protected static ArrayList<Column> DEFAULT_PATIENT_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //患者
            new Column("patient_ID", "VARCHAR(10)"),//病人号
            new Column("patient_name", "VARCHAR(50)"),//患者姓名
            new Column("patient_sex", "VARCHAR(2)"),//患者性别
            new Column("patient_age", "VARCHAR(3)")//患者年龄
    ));

    protected static String DEFAULT_PATIENT_INFO_TABLE_ADDITION="PRIMARY KEY (patient_ID)";

    protected static ArrayList<Column> DEFAULT_DOCTOR_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //医生
            new Column("doctor_ID", "VARCHAR(10)"),//医生编号
            new Column("department_ID", "VARCHAR(10)"),//科室号
            new Column("doctor_name", "VARCHAR(50)"),//医生名称
            new Column("doctor_sex", "VARCHAR(50)"),//医生性别
            new Column("doctor_age", "VARCHAR(3)")//医生年龄
    ));

    protected static String DEFAULT_DOCTOR_INFO_TABLE_ADDITION="" +
            "PRIMARY KEY (doctor_ID)," +
            "FOREIGN KEY (department_ID) REFERENCES DEPARTMENT_INFO_TABLE(department_ID)";

    protected static ArrayList<Column> DEFAULT_NURSE_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //护士
            new Column("nurse_ID", "VARCHAR(10)"),//护士编号
            new Column("department_ID", "VARCHAR(50)"),//科室号
            new Column("nurse_name", "VARCHAR(50)"),//护士名称
            new Column("nurse_sex", "VARCHAR(2)"),//护士性别
            new Column("nurse_age", "VARCHAR(3)")//护士年龄
    ));

    protected static String DEFAULT_NURSE_INFO_TABLE_ADDITION="" +
            "PRIMARY KEY (nurse_ID)," +
            "FOREIGN KEY (department_ID) REFERENCES DEPARTMENT_INFO_TABLE(department_ID)";

    protected static ArrayList<Column> DEFAULT_WARD_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //病房
            new Column("ward_number", "VARCHAR(10)"),//病房号
            new Column("department_ID", "VARCHAR(10)")//科室号
    ));

    protected static String DEFAULT_WARD_INFO_TABLE_ADDITION="" +
            "PRIMARY KEY (ward_number)," +
            "FOREIGN KEY (department_ID) REFERENCES DEPARTMENT_INFO_TABLE(department_ID)";

    protected static ArrayList<Column> DEFAULT_BUNK_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //床位
            new Column("bunk_number", "VARCHAR(10)"),//床位号
            new Column("ward_number", "VARCHAR(10)"),//病房号
            new Column("is_check_in", "VARCHAR(2)")//入住否
    ));

    protected static String DEFAULT_BUNK_INFO_TABLE_ADDITION="" +
            "PRIMARY KEY (bunk_number)," +
            "FOREIGN KEY (ward_number) REFERENCES WARD_INFO_TABLE(ward_number)";

    protected static ArrayList<Column> DEFAULT_CASES_INFO_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //病例
            new Column("hospital_number", "VARCHAR(10)"),//病历号
            new Column("patient_ID", "VARCHAR(10)"),//病人号
            new Column("doctor_ID", "VARCHAR(10)")//医生编号
    ));

    protected static String DEFAULT_CASES_INFO_TABLE_ADDITION="" +
            "PRIMARY KEY (hospital_number)," +
            "FOREIGN KEY (patient_ID) REFERENCES PATIENT_INFO_TABLE(patient_ID)," +
            "FOREIGN KEY (doctor_ID) REFERENCES DOCTOR_INFO_TABLE(doctor_ID)";

    protected static ArrayList<Column> DEFAULT_WARD_BOOKING_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //预定床位
            new Column("booking_number", "VARCHAR(10)"),//预定编号
            new Column("patient_ID", "VARCHAR(10)"),//病人号
            new Column("bunk_number", "VARCHAR(10)"),//床位号
            new Column("is_check_in", "VARCHAR(2)")//入住否
    ));

    protected static String DEFAULT_WARD_BOOKING_TABLE_ADDITION=""+
            "PRIMARY KEY (booking_number),"+
            "FOREIGN KEY (patient_ID) REFERENCES PATIENT_INFO_TABLE(patient_ID)," +
            "FOREIGN KEY (bunk_number) REFERENCES BUNK_INFO_TABLE(bunk_number)";

    protected static ArrayList<Column> DEFAULT_CHECK_IN_SITUATION_COLUMN = new ArrayList<>(Arrays.asList(  //入住情况
            new Column("check_in_number", "VARCHAR(10)"),//入住编号
            new Column("patient_ID", "VARCHAR(10)"),//病人号
            new Column("bunk_number", "VARCHAR(4)")//床位号
    ));

    protected static String DEFAULT_CHECK_IN_SITUATION_ADDITION=""+
            "PRIMARY KEY (check_in_number),"+
            "FOREIGN KEY (patient_ID) REFERENCES PATIENT_INFO_TABLE(patient_ID)," +
            "FOREIGN KEY (bunk_number) REFERENCES BUNK_INFO_TABLE(bunk_number)";

    protected static ArrayList<Column> DEFAULT_DOCTOR_DUTY_SCHEDULE_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //医生值班
            new Column("doctor_duty_id", "VARCHAR(10)"),//医生值班表编号
            new Column("doctor_ID", "VARCHAR(10)"),//医生编号
            new Column("duty_time", "VARCHAR(50)"),//值班时间
            new Column("department_ID", "VARCHAR(10)"),//值班时间
            new Column("duty_type", "VARCHAR(50)")//值班类型，白班夜班？
    ));

    protected static String DEFAULT_DOCTOR_DUTY_SCHEDULE_TABLE_ADDITION=""+
            "PRIMARY KEY (doctor_duty_id),"+
            "FOREIGN KEY (doctor_ID) REFERENCES DOCTOR_INFO_TABLE(doctor_ID)"+
            "FOREIGN KEY (department_ID) REFERENCES department_info_table(department_ID)";

    protected static ArrayList<Column> DEFAULT_NURSE_DUTY_SCHEDULE_TABLE_COLUMN = new ArrayList<>(Arrays.asList(  //护士值班
            new Column("nurse_duty_id", "VARCHAR(10)"),//护士值班表编号
            new Column("nurse_ID", "VARCHAR(10)"),//护士编号
            new Column("duty_time", "VARCHAR(50)"),//值班时间
            new Column("department_ID", "VARCHAR(10)"),
            new Column("duty_type", "VARCHAR(50)")//值班类型，白班夜班？
    ));

    protected static String DEFAULT_NURSE_DUTY_SCHEDULE_TABLE_ADDITION=""+
            "PRIMARY KEY (nurse_duty_id),"+
            "FOREIGN KEY (nurse_ID) REFERENCES NURSE_INFO_TABLE(nurse_ID)"+
            "FOREIGN KEY (department_ID) REFERENCES department_info_table(department_ID);";

    public static void initSystemRelationshipModel() {
        //TODO:加载数据模型
        tableList.add(new Table(ISystemRelationshipModel.ADMIN_INFO_TABLE, DEFAULT_ADMIN_INFO_TABLE_COLUMN,DEFAULT_ADMIN_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.DEPARTMENT_INFO_TABLE, DEFAULT_DEPARTMENT_INFO_TABLE_COLUMN,DEFAULT_DEPARTMENT_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.PATIENT_INFO_TABLE, DEFAULT_PATIENT_INFO_TABLE_COLUMN,DEFAULT_PATIENT_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.DOCTOR_INFO_TABLE, DEFAULT_DOCTOR_INFO_TABLE_COLUMN,DEFAULT_DOCTOR_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.NURSE_INFO_TABLE, DEFAULT_NURSE_INFO_TABLE_COLUMN,DEFAULT_NURSE_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.WARD_INFO_TABLE, DEFAULT_WARD_INFO_TABLE_COLUMN,DEFAULT_WARD_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.BUNK_INFO_TABLE, DEFAULT_BUNK_INFO_TABLE_COLUMN,DEFAULT_BUNK_INFO_TABLE_ADDITION));
        //tableList.add(new Table(ISystemRelationshipModel.CASES_INFO_TABLE, DEFAULT_CASES_INFO_TABLE_COLUMN,DEFAULT_CASES_INFO_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.WARD_BOOKING_TABLE, DEFAULT_WARD_BOOKING_TABLE_COLUMN,DEFAULT_WARD_BOOKING_TABLE_ADDITION));
        //tableList.add(new Table(ISystemRelationshipModel.CHECK_IN_SITUATION_TABLE, DEFAULT_CHECK_IN_SITUATION_COLUMN,DEFAULT_CHECK_IN_SITUATION_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.DOCTOR_DUTY_SCHEDULE_TABLE, DEFAULT_DOCTOR_DUTY_SCHEDULE_TABLE_COLUMN,DEFAULT_DOCTOR_DUTY_SCHEDULE_TABLE_ADDITION));
        tableList.add(new Table(ISystemRelationshipModel.NURSE_DUTY_SCHEDULE_TABLE, DEFAULT_NURSE_DUTY_SCHEDULE_TABLE_COLUMN,DEFAULT_NURSE_DUTY_SCHEDULE_TABLE_ADDITION));
    }

    public static void createSystemRelationshipModel(){
        //TODO:创建数据库数据模型（建表）
        for(ITable table:tableList){
            table.createTable();
        }
    }

    public static ITable getTable(String tableName) {
        for (ITable table : tableList) {
            if (Objects.equals(table.getTableName(), tableName)) {
                return table;
            }
        }
        return null;
    }
}
