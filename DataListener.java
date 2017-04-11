import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * DataListener
 */
public class DataListener extends Thread{
    HashMap<Integer, InetAddress> ips = new HashMap<>();
    HashMap<Integer, Integer> ports = new HashMap<>();
    byte[] bytes = new byte[65507];
    int port;
    DatagramSocket server;

    public DataListener (int port) {
        this.port = port;
        try {
            server =  new DatagramSocket(port);
        } catch (Exception e) {
            System.out.println("Socket Couldn't be created");
        }
    }

    /**
     * Listen for packets and relay if connection exists.
     */
    public void run(){
        try {
            while(true){
                final DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                server.receive(packet);
                byte[] numberBytes = Arrays.copyOfRange(packet.getData(), 0, 4);
                int sender = ByteBuffer.wrap(numberBytes).getInt();
                numberBytes = Arrays.copyOfRange(packet.getData(), 4, 8);
                int receiver = ByteBuffer.wrap(numberBytes).getInt();
                
                if(!ips.containsKey(sender))
                    ips.put(sender, packet.getAddress());
                else if(!ips.get(sender).equals(packet.getAddress()))
                    ips.replace(sender, packet.getAddress());

                if(!ports.containsKey(sender))
                    ports.put(sender, packet.getPort());
                else if(!ports.get(sender).equals(packet.getPort()))
                    ports.replace(sender, packet.getPort());
                    
                if(ips.containsKey(receiver) && ports.containsKey(sender))
                    relay(packet, receiver);
            }
        } catch (Exception e) {
            System.out.println("receive handling error");
            run();
        }
    }

    public void relay(final DatagramPacket packet, int number) {
        packet.setAddress( ips.get(number));
        packet.setPort(ports.get(number));
        new Thread(){
            @Override
            public void run() {
                try {
                    server.send(packet);
                } catch (Exception e) {}
            }
        }.start();        
    }
}