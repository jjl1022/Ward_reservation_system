import java.util.*;

public class HWMIS {
    protected static String IP = "8.130.40.31";

    protected static int PORT = 26000;

    protected static String dateBaseName = "postgres";

    protected static String username = "dboper";

    protected static String password = "Xyz@1234";

    public static void main(String[] args) throws ClassNotFoundException {
        initJDBC();
        SystemRelationshipModel.initSystemRelationshipModel();
        SystemRelationshipModel.createSystemRelationshipModel();
    }

    public static void initJDBC() throws ClassNotFoundException {
        JdbcOperation.initJdbcParams(IP, PORT, dateBaseName, username, password);
    }

}
