import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class JdbcOperation {
    protected static String URL;

    protected static String username;

    protected static String password;

    public static String JDBC_DRIVER_NAME = "org.postgresql.Driver";

    /**
     * 初始化数据库连接参数，包括数据库的 IP 地址、端口、数据库名、用户名和密码。这个方法会加载指定的 JDBC 驱动
     * **/
    public static void initJdbcParams(String IP, int PORT, String dateBaseName, String _username, String _password) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER_NAME);
        //Logger.getLogger("org.postgresql").setLevel(java.util.logging.Level.WARNING);
        URL = "jdbc:postgresql://" + IP + ":" + PORT + "/" + dateBaseName;
        System.out.println(URL);
        username = _username;
        password = _password;
    }
    /**
     * 执行查询数据库表中的数据。它接受表名、列名列表、以及可选的查询条件作为参数，
     * 并返回一个二维 ArrayList，其中包含查询结果的行和列数据
     * **/
    public static ArrayList<ArrayList<String>> selectData(String tableName, ArrayList<String> nameList, String condition) {
        // 构建查询语句
        String selectQuery = "SELECT ";
        for (int i = 0; i < nameList.size(); ++i) {
            selectQuery += nameList.get(i);
            if (i != (nameList.size() - 1)) selectQuery += ",";
        }
        selectQuery += " FROM " + tableName;
        if (!Objects.equals(condition, "")) selectQuery += " WHERE " + condition;
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, username, password);
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // 处理查询结果
                ArrayList<String> tempResult = new ArrayList<>();
                for (int i = 0; i < nameList.size(); ++i) {
                    tempResult.add(resultSet.getString(nameList.get(i)));
                }
                if (tempResult.size() != 0) result.add(tempResult);
            }
            resultSet.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 执行从数据库表中删除数据的操作。它接受表名和删除条件作为参数，并返回一个布尔值，表示是否成功删除数据
     * **/
    public static boolean deleteData(String tableName, String condition) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE " + condition;

        // 删除数据
        try (Connection connection = DriverManager.getConnection(URL, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("成功删除了 " + rowsAffected + " 条数据。");
                return true;
            } else {
                System.out.println("没有符合条件的数据行需要删除。");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 执行更新数据库表中数据的操作。它接受表名、列名列表、新值列表以及更新条件作为参数，
     * 然后返回一个布尔值，表示是否成功更新数据
     * **/
    public static boolean updateData(String tableName, ArrayList<String> nameList, ArrayList<String> valueList, String condition) {
        if (nameList.size() != valueList.size()) return false;
        String updateQuery = "UPDATE " + tableName + " SET ";
        for (int i = 0; i < nameList.size(); ++i) {
            updateQuery += nameList.get(i) + "='" + valueList.get(i);
            if (i != (valueList.size() - 1)) updateQuery += "',";
        }
        updateQuery += "' WHERE " + condition;
        try (Connection connection = DriverManager.getConnection(URL, username, password);
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertData(String tableName, ArrayList<String> valueList) {
        return insertData(tableName, new ArrayList<>(), valueList);
    }
    /**执行向数据库表中插入数据的操作。它有两个重载版本，一个接受表名和值列表，
     * 另一个接受表名、列名列表和值列表。这两个方法返回一个布尔值，表示是否成功插入数据
     * **/
    public static boolean insertData(String tableName, ArrayList<String> nameList, ArrayList<String> valueList) {
        if ((nameList.size() != valueList.size()) && nameList.size() != 0) return false;
        String insertSQL;
        if (nameList.size() == 0) {
            insertSQL = "INSERT INTO " + tableName + " VALUES ('";
            for (int i = 0; i < valueList.size(); ++i) {
                insertSQL += valueList.get(i);
                if (i != (valueList.size() - 1)) insertSQL += "','";
            }
            insertSQL += "')";
        } else {
            insertSQL = "INSERT INTO " + tableName + "(";
            for (int i = 0; i < nameList.size(); ++i) {
                insertSQL += nameList.get(i);
                if (i != (nameList.size() - 1)) insertSQL += ",";
            }
            insertSQL += ") VALUES ('";
            for (int i = 0; i < valueList.size(); ++i) {
                insertSQL += valueList.get(i);
                if (i != (valueList.size() - 1)) insertSQL += "','";
            }
            insertSQL += "')";
        }
        try (Connection conn = DriverManager.getConnection(URL, username, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**执行创建数据库表的操作。它接受表名、列信息列表和可选的其他表定义信息作为参数，
     * 并返回一个布尔值，表示是否成功创建表
     * **/

    public static boolean createTable(String tableName, ArrayList<Column> columnList,String addition) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (int i = 0; i < columnList.size(); ++i) {
            createTableSql += columnList.get(i).toString();
            if (i != (columnList.size() - 1)) createTableSql += ",";
        }
        if(!Objects.equals(addition, "")){
            createTableSql += ","+addition+");";
        }else{
            createTableSql += ");";
        }

        try (Connection conn = DriverManager.getConnection(URL, username, password);
             Statement stmt = conn.createStatement()) {
            boolean tableCreated = stmt.execute(createTableSql);
            return tableCreated;
        } catch (SQLException e) {
            return false;
        }
    }
}
