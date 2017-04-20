

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
import com.mysql.jdbc.Driver;
/**
 * Created by Oskar on 2017-03-03.
 */

public class GCMHandler{   
    private String user = "test";
    private String host = "itkand-3-1.tddd82-2017.ida.liu.se";
    private String pass = "kaffekaka";
    private String db = "healthcare";
    public GCMHandler(){

    }
    
    private Connection getDBConnection() throws Exception{
        Properties properties;
        properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", pass);
        return DriverManager.getConnection("jdbc:mysql://"+ host+":3306/"+db,properties);
}

    /**
     * Send GCM
     */
    public void startCall (int callerID, int userID, boolean isVideo) throws IOException{
        String token = getToken(userID);    

        sendPost(callerID,token,isVideo);
    }

    public void setToken(int userId,String token) {
        try {
            Connection conn = getDBConnection();
            String query    = "REPLACE INTO token(owner_id,data) VALUES("+userId+",'"+token+"');";
            Statement stmt  = conn.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }
    }

    /**
     * getFireBase-token
     */
    private String getToken(int userId) {
        
        String token = null;
        try {
            Connection conn = getDBConnection();
            String query = "SELECT data FROM healthcare.token WHERE owner_number="+userId+";";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
                token = rs.getString("data");
            
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
    public void sendPost(int callerId,String token, boolean isVideo) throws IOException {
        String authKey = "AIzaSyAfOZbxa1N5C5G8Y7xnYLdwZ8l7HCEENiE";
        String site = "https://fcm.googleapis.com/fcm/send";
        
        URL url = new URL(site);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Authorization" ,"key="+" "+authKey);

        DataOutputStream dos = new DataOutputStream(con.getOutputStream());
        dos.writeBytes(createJson(callerId,token, isVideo).toString());
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

    public JSONObject createJson(int callerId,String token, boolean isVideo){
        JSONObject JsonPost = new JSONObject();
        JSONObject payload = new JSONObject();
        try {
            payload.put("TYPE","call");
            payload.put("CALLER",callerId);
            payload.put("isVideo",Boolean.toString(isVideo));
            JsonPost.put("data", payload);
            JsonPost.put("to", token);
            System.out.println(JsonPost.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonPost;
    }

}
