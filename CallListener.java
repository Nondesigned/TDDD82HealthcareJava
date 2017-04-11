import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;





/**
 * CallListener
 * Listens for calls and creates new call objects if
 * one is detected
 */
public class CallListener {
    ArrayList<Client> clients = new ArrayList<Client>();
    
    public CallListener (int port) {
        System.setProperty("javax.net.ssl.keyStore", "certs.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "kaffekaka");
        SSLServerSocketFactory factory=(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        String trustStore = System.getProperty("javax.net.ssl.keyStore");
        if (trustStore == null) {
            System.out.println("javax.net.ssl.trustStore is not defined");
        } else {
            System.out.println("javax.net.ssl.trustStore = " + trustStore);
        }

        try{
            ServerSocket server = factory.createServerSocket(port);
            
            while(true){
                try {
                    Client client = new Client(server.accept(),this);
                    client.start();
                    clients.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //server.close();
        }catch(Exception e){
            System.out.println("Connection to client failed");
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

    public void removeFromList(int number){
        try{
            for(int i = 0; i < clients.size(); i++)
                if(clients.get(i).getNumber() == number ){
                    clients.get(i).killStream();
                    clients.remove(i);
                }else if(clients.get(i).getNumber() == 0){
                    clients.remove(i);
                }
        }catch(Exception e){
            System.out.println("Closing stream for number: "+number);
        }
                
    }
}