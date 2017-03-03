import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * Call
 */
public class Call implements Runnable{
    Socket tcpSocket;
    ControlPacket lastControlPacket;

    public Call (Socket incommingSocket) {
        this.tcpSocket = incommingSocket;    
    }

    @Override
    public void run(){
        try {
            byte[] data = ServerUtils.inputStreamToByte(tcpSocket.getInputStream());
            lastControlPacket = new ControlPacket(data);
        } catch (Exception e) {
        }
    }
}