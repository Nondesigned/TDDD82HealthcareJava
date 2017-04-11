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
        System.out.println("Enter IP:");
        String ip = scan.nextLine();
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
        //String ip = "localhost";
        int port = 1337;
        SSLSocketFactory factory = context.getSocketFactory();
        Socket clientSocket = factory.createSocket(ip, port);//new Socket(ip, port);

        //Socket clientSocket = new Socket(ip, port);

        while(true){ 
            byte[] bytes = new byte[2044];
            ControlPacket ctrl = new ControlPacket(bytes);
            int val = 111;
            String val2 = "KeyForEncryption";
            ctrl.setSource(src);
            ctrl.setDestination(dest);
            ctrl.setKey(val2);
            if(src == 111)
                ctrl.setPayload(new String("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtb2JpbGUiLCJleHAiOjE1MjA1MTcxNTMsImlhdCI6MTQ4ODk4MTE1MywiaXNzIjoiU2p1a3bDpXJkc2dydXBwZW4iLCJzdWIiOiIxMTEifQ.nEzzywquyCcwkdseTKiF9WMlWcQxJZBoD0z5zOF6Xla-Qjl7RHX6EuQMC6k7jL5FFQajn_-pndokrv5kKGY1ZJU7ejSU324xSaaz5ci46FC6teBFgk57iFL191nmFz4I9tqzQ90QzgOHE4hf3fG2WBhB5WxR27-OaYw7MLVjoGVMoqWfzKGAYfpbP3xq16G3ppDJ1QkxJAw4IotPYWP4yws_bQVjqgLabkYOQbnRb4RHZ-pHxtcXnnhewfpmfmt05JnT18ufjVf3l4hRdJ3RfMSANiwU9mgHDSucchrtWhSyScGcgMjZDn3iN4wd9O7H7VFc2mNDxnN2FceERP3URQ7YtK26_ugTEwm9cNOvE4mSZvyhGldIwVy2IkUW2wJ75Q-NYqvwIavF1ND1U49csqWNIK63ifkUMJ8jfCFFxi-0NoFIieE4GmId1HtctBkAsbGYneX4OZnBTNv_Z1adoGoWHNkSs1np75sjzFiQFOkKIYbRXa3ptR-rB2MbfQNz8z6WJQeByvBAwvokQ903kbAMnszmeGUk7CwZwY6_HSypuqU9wZpaDvyN0YJFkGJjBzMrrA98GPrKT5i8-w5qjoBjdwQdO3Aiju_G1Zh0DY99TJAp1e7MLz4zF0JGa1KQXqwql0ZFs71fhCLtTBHfHyDBXikhLufWTx2eh4oJAHY").getBytes());
            else if(src == 222)
                ctrl.setPayload(new String("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtb2JpbGUiLCJleHAiOjE1MjA1MTcwNzEsImlhdCI6MTQ4ODk4MTA3MSwiaXNzIjoiU2p1a3bDpXJkc2dydXBwZW4iLCJzdWIiOiIyMjIifQ.19uGx7zwBSWyFrd-Q478V9uv-5mE_372Jq2uB3KePDzokcz89CyePTHimUWBulqkgllCRhSSUQIpnHjCwGQuI_C870u67HBNHvu-wuwc3hMOn_0P94zI_vFxrc4QR_1XMWuYI6R10gCiNitDFpJKMcReS2W7Jotmy9CT1pMpozDNZ-RMU2_sfjgLaL3EZ7cGyU32eK2K2_t3jqUonPEe2x7DaZ2_opntw8c2bSm3uLO8lpdyMSayoVKhpAPxw95aoBVkW6T144DmNFx33jBBVdy4qNzxkmsZG30PFHxJjWKkDiEZNcygg2KyAxEND7Ejz2JY-vQXcsdIIzA6M49bk1JI6AkAaliDbEjLLi2O1xMH_Y5ExEGOa3M9RP-e-p464EZ95fqLjzCi-jZod_H2HqHjFHIBnWAdDSq_JMtwP3h7YNwZvrT9hbEU1EfAJS8dIjNfjDCl8BqejBNkveJ_rRMHzDdIIY4a8EheZNiJuIRMSoow9MOD29A5IMeNufhM2KtkAeGzU64SXqZRa2UsO8qIl0ArRBkxAcugTRcTjzJQVHIdwQ1_Eo2UfIi1AddjS0LA0EvQHgsO8PktcWy9uSI-PYaStZKpEER2CGdUL5pnpMvfJNzuaJ9o3czvAplf0xkb7_yjgZ-iXUNPSBKfg9hjmTmp_YUAfU3PhxBDopc").getBytes());
            else
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