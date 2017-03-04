import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
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
            System.out.println("Handling: " + tcpSocket.getRemoteSocketAddress());
            while(true){
            byte[] bytes = readFully(tcpSocket.getInputStream());
            System.out.println(new String(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] readFully(InputStream input) throws IOException{
        byte[] buffer = new byte[2];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1 && output.toByteArray().length<=3)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}