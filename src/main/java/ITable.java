import java.util.ArrayList;
import java.util.HashMap;

public interface ITable {
    boolean createTable();

    String getTableName();

    ArrayList<String> getColumnNameList();

    ArrayList<ArrayList<String>> selectData();

    ArrayList<ArrayList<String>> selectData(String condition);

    ArrayList<ArrayList<String>> selectData(ArrayList<String> selectColumnName);

    ArrayList<ArrayList<String>> selectData(ArrayList<String> selectColumnName, String condition);

    boolean insertData(ArrayList<String> valueList);

    boolean insertData(ArrayList<String> nameList, ArrayList<String> valueList);

    boolean updateData(HashMap<String, String> resetList, String condition);

    boolean deleteData(String condition);
}
