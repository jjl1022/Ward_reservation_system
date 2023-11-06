import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Table implements ITable {
    protected String tableName;

    protected ArrayList<Column> columnList = new ArrayList<>();

    protected ArrayList<String> nameList = new ArrayList<>();

    protected String addition;

    public Table(String tableName, ArrayList<Column> columnList,String addition) {
        this.addition=addition;
        this.tableName = tableName;
        this.columnList = columnList;
        for (Column c : columnList) {
            nameList.add(c.valueName);
        }
    }

    public String getTableName() {
        return tableName;
    }

    public boolean createTable() {
        return JdbcOperation.createTable(tableName, columnList,addition);
    }

    public ArrayList<String> getColumnNameList() {
        return nameList;
    }

    public ArrayList<ArrayList<String>> selectData() {
        return selectData(nameList, "");
    }

    public ArrayList<ArrayList<String>> selectData(String condition) {
        return selectData(nameList, condition);
    }

    public ArrayList<ArrayList<String>> selectData(ArrayList<String> selectColumnName) {
        return selectData(selectColumnName, "");
    }

    public ArrayList<ArrayList<String>> selectData(ArrayList<String> selectColumnName, String condition) {
        return JdbcOperation.selectData(tableName, selectColumnName, condition);
    }

    public boolean insertData(ArrayList<String> valueList) {
        return insertData(new ArrayList<>(), valueList);
    }

    public boolean insertData(ArrayList<String> nameList, ArrayList<String> valueList) {
        return JdbcOperation.insertData(tableName, nameList, valueList);
    }

    public boolean updateData(HashMap<String, String> resetList, String condition) {
        ArrayList<String> resetColumnNameList = new ArrayList<>();
        ArrayList<String> resetColumnValueList = new ArrayList<>();
        for (Map.Entry<String, String> entry : resetList.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
            resetColumnNameList.add(key);
            resetColumnValueList.add(value);
        }
        return JdbcOperation.updateData(tableName, resetColumnNameList, resetColumnValueList, condition);
    }

    public boolean deleteData(String condition) {
        return JdbcOperation.deleteData(tableName, condition);
    }
}
