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
 * Client
 */
public class Client extends Thread{
    Socket tcpSocket;
    ControlPacket lastControlPacket;
    boolean initialized = false;
    int number;
    CallListener listener;
    public Client (Socket incommingSocket, CallListener listener) {
        this.tcpSocket = incommingSocket;
        this.listener = listener;
    }

    @Override
    public void run(){
        try {
            System.out.println("Handling: " + tcpSocket.getRemoteSocketAddress());
            while(true){
                ControlPacket ctrlPacket = new ControlPacket(readData(tcpSocket.getInputStream()));

                if(number == 0)
                    number = ctrlPacket.getSource();
                if(ctrlPacket.getFlag(0) && !initialized)
                    initialize(ctrlPacket);
                else
                    listener.relay(ctrlPacket);  
            }
        } catch (Exception e) {
            System.out.println("Connection Lost");
            listener.removeFromList(number);
        }
    }



    public void initialize(ControlPacket ctrlPacket){
        GCMHandler gcm = new GCMHandler();
        try {
            gcm.startCall(ctrlPacket.getSource(), ctrlPacket.getDestination());
        } catch (Exception e) {
            //System.out.println("GCM could not be contacted");
            //return;
        }
        if(!tokenIsValid(ctrlPacket.getPayload(), ctrlPacket.getSource())){
            listener.removeFromList(ctrlPacket.getSource());
            return;
        }
        initialized = true;
    }

    public boolean tokenIsValid(byte[] token, int number){
            JWT jwt = new JWT(token);
            return (jwt.valid() && jwt.getNumber() == number && number != 0);
        }

    public byte[] readData(InputStream input) throws Exception{
        DataInputStream dataInputStream = new DataInputStream(input);
        byte[] data = new byte[2044];
        try {
            dataInputStream.readFully(data);
        } catch (Exception e) {
            killStream();
        }
        return data;
    }

    public int getNumber() {
        return this.number;
    }

    public void killStream() throws Exception{
        tcpSocket.getOutputStream().close();
    }

    public void sendControlPacket(ControlPacket pkt) {
        try{
            ServerUtils.sendBytes(pkt.getPacketBytes(), tcpSocket);
        }catch (Exception e){}
    }
    
    @Override
    public boolean equals(Object object)
    {
        return (this.number == ((Client)object).getNumber());
    }

    @Override
    public int hashCode() {
        return this.number;
    }
}