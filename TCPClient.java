import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPClient
{   
    /**
     * TCPClient for testing. (Some code found on the web...)
     */
    public static void main(String argv[]) throws Exception{    
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter IP:");
        String ip = scan.nextLine();
        System.out.println("Enter port:");
        int port = scan.nextInt();
        Socket clientSocket = new Socket(ip, port);
        while(scan.nextLine().equals("y")){ 
            String sentence = "asdasd";
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(sentence);
        }
        clientSocket.close();
    }
}