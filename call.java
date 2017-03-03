import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Call
 */
public class Call implements Runnable{
    Socket tcpSocket;
    InputStreamReader sreader;
    BufferedReader breader;
    OutputStreamWriter writer;
    public Call (Socket incommingSocket) {
        this.tcpSocket = incommingSocket;    
    }

    @Override
    public void run(){
        try {
            inputStream = tcpSocket.getInputStream();
            sreader = new InputStreamReader(inputStream);
            breader = new BufferedReader(sreader);
            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}