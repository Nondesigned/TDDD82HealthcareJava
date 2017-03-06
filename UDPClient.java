import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
    
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress ip = InetAddress.getByName("localhost");
      byte[] sendData = testData();
      //ByteBuffer.allocate(65507).putInt(123).array();
      byte[] receiveData = new byte[65507];
      System.out.println("Lenght: " + ByteBuffer.wrap(sendData).getInt());
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 1338);
      
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
      clientSocket.close();
   }
   public static byte[] testData(){
             Scanner scan = new Scanner(System.in);

       System.out.println("Enter source:");
        int src = scan.nextInt();        
        System.out.println("Enter destination:");
        int dest = scan.nextInt();
        byte[] bytes = new byte[65507];
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
        return ctrl.getPacketBytes();
   }
}