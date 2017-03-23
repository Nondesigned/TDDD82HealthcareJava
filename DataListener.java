import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DataListener
 */
public class DataListener extends Thread{
    ArrayList<DataClient> clients = new ArrayList<DataClient>();
    byte[] bytes = new byte[65507];
    int port;

    public DataListener (int port) {
        this.port = port;
    }

    public void run(){
        try{
            DatagramSocket server = new DatagramSocket(port);
            while(true){
                try {
                    DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                    server.receive(packet);
                    byte[] bytes = Arrays.copyOfRange(packet.getData(), 4, 8);
                    int number = ByteBuffer.wrap(bytes).getInt();
                    if(clientExists(number)){
                        relay(packet,number);
                    }else{
                        DataClient client = new DataClient(server, packet, this);
                        client.start();
                        clients.add(client);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //server.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection to client failed");
        }
    }

    public void relay(DatagramPacket packet, int number) {
        for (DataClient client : clients) {
            if(client.getNumber() == number)
                client.sendDataPacket(packet);
        }
    }

    public boolean clientExists(int number){
        for(DataClient client : clients)
            if(client.getNumber() == number)
                return true;
        return false;
    }
}