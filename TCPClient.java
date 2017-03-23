import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
            }
        } };
        context.init(null, trustAllCerts, null);
        System.out.println("Enter source:");
        int src = scan.nextInt();        
        System.out.println("Enter destination:");
        int dest = scan.nextInt();
        String ip = "localhost";
        int port = 1337;

        SSLSocketFactory factory = context.getSocketFactory();
        Socket clientSocket = factory.createSocket(ip, port);//new Socket(ip, port);
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