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
                System.out.println(ctrlPacket.getFlag(1));
                if(ctrlPacket.getFlag(0) && !initialized)
                    System.out.println("int");
                    //initialize(ctrlPacket);
                else
                    listener.relay(ctrlPacket);
            }
        } catch (Exception e) {
            System.out.println("Connection Lost");
        }
    }



    public void initialize(ControlPacket ctrlPacket){
        GCMHandler gcm = new GCMHandler();
        try {
            gcm.startCall(ctrlPacket.getSource(), ctrlPacket.getDestination());
        } catch (Exception e) {
            System.out.println("GCM could not be contacted");
        }
        if(tokenIsValid(ctrlPacket.getPayload()));
        //Get GCM token
        //Make GCM call
        //Init:
        //- Init, get token for Client
        //- Get its address
    }

    public boolean tokenIsValid(byte[] token){
        System.out.println("TOKEN: "+ new String(token));
        return true;
    }

    public byte[] readData(InputStream input) throws Exception{
        DataInputStream dataInputStream = new DataInputStream(input);
        byte[] data = new byte[2044];
        try {
            dataInputStream.readFully(data);
        } catch (Exception e) {
            tcpSocket.close();
        }
        return data;
    }

    public int getNumber() {
        return this.number;
    }

    public void sendControlPacket(ControlPacket pkt) {
        try{
            ServerUtils.sendBytes(pkt.getPacketBytes(), tcpSocket);
        }catch (Exception e){
        }
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