

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Oskar on 2017-03-03.
 */

public class JavaServer{   
    public JavaServer(){

    }
    
    public void startCall (int userID, int callerID) throws IOException{
        String token = getToken(userID);
        sendPost(callerID,token);
        
    }

    public void setToken(int userId,String token) {
        String user = "itkand_2017_3_1";
        String host = "db-und.ida.liu.se";
        String pass = "itkand_2017_3_1_7f41";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties;
            properties = new Properties();
            properties.setProperty("user", "itkand_2017_3_1");
            properties.setProperty("password", "itkand_2017_3_1_7f41");

            Connection conn = DriverManager.getConnection("jdbc:mysql://"+ host+":3306/"+user,properties);
            String query = "REPLACE INTO token(owner_id,data) VALUES("+userId+",'"+token+"');";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e){
        	e.printStackTrace();
        }
    }

    private static String getToken(int userId) {
        String user = "itkand_2017_3_1";
        String host = "db-und.ida.liu.se";
        String pass = "itkand_2017_3_1_7f41";
        String token = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties;
            properties = new Properties();
            properties.setProperty("user", "itkand_2017_3_1");
            properties.setProperty("password", "itkand_2017_3_1_7f41");

            Connection conn = DriverManager.getConnection("jdbc:mysql://"+ host+":3306/"+user,properties);
            String query = "{CALL get_user_token(" + userId + ")}";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                token = rs.getString("data");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

//Skickar HTTP Post till GCM som skickar vidare en pushnotis till klient. JSon data
    public void sendPost(int callerId,String token) throws IOException {
        String authKey = "AIzaSyAfOZbxa1N5C5G8Y7xnYLdwZ8l7HCEENiE";
        String site = "https://fcm.googleapis.com/fcm/send";
        
        URL url = new URL(site);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization" ,"key="+" "+authKey);


        DataOutputStream dos = new DataOutputStream(con.getOutputStream());
        dos.writeBytes(createJson(callerId,token).toString());
        dos.flush();
        dos.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
            
        }
        in.close();



    }

    public JSONObject createJson(int callerId,String token){
        JSONObject JsonPost = new JSONObject();
        JSONObject payload = new JSONObject();
        try {
            payload.put("TYPE","call");
            payload.put("CALLER",callerId);
            JsonPost.put("data", payload);
            JsonPost.put("to", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonPost;
    }

}
