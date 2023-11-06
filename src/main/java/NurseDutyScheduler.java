import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class NurseDutyScheduler {
    private Connection connection;

    // 构造函数
    public NurseDutyScheduler(Connection connection) {
        this.connection = connection;
    }

    // 执行SQL语句
    private void executeSql(String sql) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.execute();
        }
    }

    // 生成新的医生值班表数据
    private void generateNurseDuty(ArrayList<ArrayList<String>> doctorInfo,int[] startTime) throws SQLException {
        // 删除已有的值班表数据
        executeSql("DELETE FROM nurse_duty_schedule");

        // 构建科室编号 -> 医生列表的映射
        Map<String, List<String>> departmentMap = new HashMap<>();
        for (ArrayList<String> info : doctorInfo) {
            String departmentId = info.get(1);
            if (!departmentMap.containsKey(departmentId)) {
                departmentMap.put(departmentId, new ArrayList<>());
            }
            departmentMap.get(departmentId).add(info.get(0));
        }

        // 生成新的值班表数据
        LocalDate startDate = LocalDate.of(startTime[0], startTime[1], startTime[2]);  // 当前日期作为起始日期
        LocalDate endDate = startDate.plusDays(6);  // 结束日期为起始日期后的6天
        String insertSql = "INSERT INTO nurse_duty_schedule (nurse_duty_id, nurse_ID, duty_time, department_ID, duty_type) VALUES (?, ?, ?, ?, ?)";
        Random random = new Random();
        int count = 1;
        for (LocalDate date = startDate; date.compareTo(endDate) <= 0; date = date.plusDays(1)) {
            for (String departmentId : departmentMap.keySet()) {
                List<String> doctorList = departmentMap.get(departmentId);

                // 随机选择一名医生进行白班和夜班
                String dayDoctor = doctorList.get(random.nextInt(doctorList.size()));
                String nightDoctor = dayDoctor;
                while (nightDoctor.equals(dayDoctor)) {
                    nightDoctor = doctorList.get(random.nextInt(doctorList.size()));
                }

                // 生成白班和夜班的值班表数据
                String dayDutyId = "D" + String.format("%05d", count++);
                String nightDutyId = "D" + String.format("%05d", count++);
                String dayDutyTime = date.toString() + " 08:00:00"; //白天值班时间
                String nightDutyTime = date.toString() + " 20:00:00"; //夜间值班时间

                // 插入白班值班信息
                try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                    pstmt.setString(1, dayDutyId);
                    pstmt.setString(2, dayDoctor);
                    pstmt.setString(3, dayDutyTime);
                    pstmt.setString(4, departmentId);
                    pstmt.setString(5, "day");
                    pstmt.executeUpdate();
                }

                // 插入夜班值班信息
                try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
                    pstmt.setString(1, nightDutyId);
                    pstmt.setString(2, nightDoctor);
                    pstmt.setString(3, nightDutyTime);
                    pstmt.setString(4, departmentId);
                    pstmt.setString(5, "night");
                    pstmt.executeUpdate();
                }
            }
        }
    }

    public static void makeNurseDuty(int[] startDate) throws SQLException {
        // 连接到数据库
        String dbUrl = "jdbc:postgresql://8.130.40.31:26000/postgres";
        String dbUser = "dboper";
        String dbPassword = "Xyz@1234";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // 设置自动提交为false，以便控制事务
            connection.setAutoCommit(false);

            // 护士信息数据
            SystemRelationshipModel.initSystemRelationshipModel();
            ITable table=SystemRelationshipModel.getTable(ISystemRelationshipModel.NURSE_INFO_TABLE);

            JdbcOperation.initJdbcParams("8.130.40.31", 26000, "postgres", "dboper", "Xyz@1234");
            ArrayList<ArrayList<String>> nurseInfo = table.selectData();

            // 创建值班安排对象并生成医生值班表数据
            NurseDutyScheduler scheduler = new NurseDutyScheduler(connection);
            scheduler.generateNurseDuty(nurseInfo,startDate);

            // 提交事务并关闭连接
            connection.commit();
        } catch (SQLException e) {
            System.out.println("发生异常，事务回滚：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
