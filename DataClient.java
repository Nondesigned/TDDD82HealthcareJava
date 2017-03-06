import java.io.DataInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.Socket;
import java.nio.*;
import java.util.Arrays;
import java.net.DatagramSocket;

/**
 * DataClient
 */
public class DataClient extends Thread{
    DatagramSocket socket;
    int number = 0;
    DataListener listener;
    DatagramPacket packet;
    public DataClient (DatagramSocket socket, DatagramPacket packet, DataListener listener) {
        this.socket = socket;
        this.listener = listener;
        this.packet = packet;
        this.number = getSource(packet);
        System.out.println(new String(packet.getData()));
    }

    public int getSource(DatagramPacket packet){
        byte[] bytes = Arrays.copyOfRange(packet.getData(), 0, 4);
        return ByteBuffer.wrap(bytes).getInt();
    }

    public void run(){
        System.out.println("Serving:"+ socket.getRemoteSocketAddress());
        //listener.relay(packet);
    }

    public void handleDataPacket(DatagramPacket packet) {
        System.out.println("asd");
        //listener.relay(packet);
    }
    public int getNumber() {
        return this.number;
    }   


    public byte[] readData(InputStream input) throws Exception{
        //DataInputStream dataInputStream = new DataInputStream(input);
        byte[] data = new byte[2044];
        /*try {
            dataInputStream.readFully(data);
        } catch (Exception e) {
            tcpSocket.close();
        }*/
        return data;
    }

    public void sendDataPacket(DatagramPacket pkt) {
        try{
            socket.send(pkt);
        }catch (Exception e){

        }
    }


}