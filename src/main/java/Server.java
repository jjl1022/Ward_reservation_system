import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

@WebServlet("/Server")
public class Server extends HttpServlet {
    protected static String IP = "localhost";

    protected static int PORT = 26000;

    protected static String dataBaseName = "admin";

    protected static String username = "admin";

    protected static String password = "admin";

    public static String CONFIG_FILE_PATH;



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CONFIG_FILE_PATH=getServletContext().getRealPath("/")+"WEB-INF/config/config.properties";
        readParams();
        initJDBC();
        solveCrossOriginProblem(request, response);
        PostRequestProcess.processRequest(request,response);
    }

    protected void solveCrossOriginProblem(HttpServletRequest request, HttpServletResponse response) {
        //TODO:解决跨域问题
        String origin = request.getHeader("Origin");
        response.setContentType("text/plain;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    protected static void readParams() {
        File file = new File(Server.CONFIG_FILE_PATH);
        if (file.exists()) {
            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream(Server.CONFIG_FILE_PATH)) {
                properties.load(fis);
                IP = properties.getProperty("db.IP");
                PORT = Integer.parseInt(properties.getProperty("db.PORT"));
                dataBaseName = properties.getProperty("db.dateBaseName");
                username = properties.getProperty("db.username");
                password = properties.getProperty("db.password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initJDBC() {
        try {
            JdbcOperation.initJdbcParams(IP, PORT, dataBaseName, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
