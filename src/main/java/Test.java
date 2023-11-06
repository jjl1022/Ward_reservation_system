import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        String url = "http://8.130.40.31:8080/HWMIS/Server";
        String data = "{\"type\": \"GetInfo\"," +
                "\"name\": \"John\"," +
                "\"age\": 30," +
                "\"city\": \"New York\"" +
                "}";
        String response = sendMsg(url, data);
        System.out.println(response);
    }

    public static String sendMsg(String... params) {
        String url = params[0];
        String jsonData = params[1];
        String response = "";

        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true); // Set doOutput to true

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonData.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
            } else {
                response = "Error: " + responseCode;
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
