import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;




/**
 * CallListener
 * Listens for calls and creates new call objects if
 * one is detected
 */
public class CallListener {
    ArrayList<Client> clients = new ArrayList<Client>();
    
    public CallListener (int port) {
        try{
            ServerSocket server = new ServerSocket(port);
            while(true){
                try {
                    Client client = new Client(server.accept(),this);
                    client.start();
                    clients.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server.close();
        }catch(Exception e){
            System.out.println("Connection to client failed")
        }
    }

    /**
     * Relay packets to packet destination if one can be
     * found in the list of clients
     */
    public void relay(ControlPacket pkt) {
        for(Client client : clients)
            if(client.getNumber() == pkt.getDestination())
                client.sendControlPacket(pkt);
    }

}