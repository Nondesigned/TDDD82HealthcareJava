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
        String ip = "localhost";
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
            ctrl.setPayload(new String("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtb2JpbGUiLCJleHAiOjE1MTk5ODAxODQsImlhdCI6MTQ4ODQ0NDE4NCwiaXNzIjoiU2p1a3bDpXJkc2dydXBwZW4iLCJzdWIiOiJUREREODJMb2dpbiJ9.otRhLd-pm_cuF973fUapuLkTUOhzUwD7JRJTp45emsysGKopkexVWh9GcI3aWAx_9eRedDo9k_V-rfrl-CDP2nZPv5dtPtT0rGzcXqu-Hi4PVCgwGyJNE4XlpmjdCJRzT8kmUPW6tdtiTIM8R1SD4dYABrbBxltuU9_P5FKufBACb2lXPHlz1uTinE8ME5hzMJyKxRE6rY4Zcw9MCw6Nu0ecz_MqYUixiPQ9efU5ryh4B6iIOsSvsO4wOtpuFmmD4MfvB79AeVy-bhoyNvhcksd3hVIvdpR--QhvroKzfn72-6KQiX3zlTiGwcChUFoMTivZvgc4b-xjsnOqLkNySM_eE6lUTZkmZxcdCUOwB6Wvcn-TrG8Z85PFQqeH3ePfCD77M5FaWocw4CZskxzGAih76pGzrVKCO-g7eQilWcRVuqDaq-gHQEspbCKhTt9UwlT9oePY9e9VpSq5plJOX545N93n-5e1ckMcXu07zRELNUV1_vKfPEEb9qtMY-DRrCON01aR3gTQtSKQzIV0crcknY1gRqJ24_3LAPAIWjEGmUQI64KXKtvEQ6E-X0OKj_TlEKUfOxcq3WLcgJhjn7R41JBQwGaMeq_rbkdRvycszhafHRD6QYQAU50Wkvj53LDYN6tkITDs-djpjM1vWz9yZxgbA3cSz-cqdjCPs_s").getBytes());
            ControlFlags flags = new ControlFlags();
            for (ControlFlag flag : ControlFlag.values())
                flags.setFlag(flag, true);
        
            ctrl.setFlags(flags);
            ServerUtils.sendBytes(ctrl.getPacketBytes(),clientSocket);
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