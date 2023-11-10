/**
 * 基本的表单信息都在这里
 * **/
public interface ISystemRelationshipModel {
    String ADMIN_INFO_TABLE = "admin_info_table";//管理员信息表

    String DEPARTMENT_INFO_TABLE = "department_info_table";//科室信息表ok
    String WARD_INFO_TABLE = "ward_info_table";//病房信息表ok
    String BUNK_INFO_TABLE = "bunk_info_table";//床位信息表ok
    String DOCTOR_INFO_TABLE = "doctor_info_table";//医生信息表ok
    String NURSE_INFO_TABLE = "nurse_info_table";//护士信息表ok
    String PATIENT_INFO_TABLE = "patient_info_table";//患者信息表


    String WARD_BOOKING_TABLE = "ward_booking_table";//病房预定表
    /*
    接下来要做的是
    用户主页增加预定功能（先填写病人信息在申请床位）
    管理页的入住功能用于修改预定后的真实入住情况（修改预定记录），出院用于删除预定记录
     */

    String DOCTOR_DUTY_SCHEDULE_TABLE = "doctor_duty_schedule";//医生值班表
    String NURSE_DUTY_SCHEDULE_TABLE = "nurse_duty_schedule";//护士值班表



    String CASES_INFO_TABLE = "cases_info_table";//病例信息表  TODO:废弃不用的表
    String CHECK_IN_SITUATION_TABLE = "check_in_situation_table";//入住信息表  TODO:废弃不用的表

}
