import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Scanner;

class TCPClient
{   
    /**
     * TCPClient for testing. (Some code found on the web...)
     */
    public static void main(String argv[]) throws Exception{    
        Scanner scan = new Scanner(System.in);
        /*System.out.println("Enter IP:");
        String ip = scan.nextLine();
        */
        System.out.println("Enter source:");
        int src = scan.nextInt();        
        System.out.println("Enter destination:");
        int dest = scan.nextInt();
        String ip = "192.168.199.128";
        int port = 1337;
        Socket clientSocket = new Socket(ip, port);
        while(true){ 
            byte[] bytes = new byte[2044];
            ControlPacket ctrl = new ControlPacket(bytes);
            int val = 111;
            String val2 = "KeyForEncryption";
            ctrl.setSource(src);
            ctrl.setDestination(dest);
            ctrl.setKey(val2);
            ctrl.setPayload(val2.getBytes());
            ControlFlags flags = new ControlFlags();
            for (ControlFlag flag : ControlFlag.values())
            flags.setFlag(flag, true);
            ServerUtils.sendBytes(ctrl.getPacketBytes(),clientSocket);
            System.out.println(new ControlPacket(readData(clientSocket.getInputStream())).getDestination());
        }
        //clientSocket.close();
    }
        public static byte[] readData(InputStream input) throws Exception{
        DataInputStream dataInputStream = new DataInputStream(input);
        byte[] data = new byte[2044];
        try {
            dataInputStream.readFully(data);
        } catch (Exception e) {
            input.close();
        }
        return data;
    }

}