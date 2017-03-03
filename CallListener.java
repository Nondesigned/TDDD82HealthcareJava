import java.io.IOException;
import java.net.ServerSocket;

/**
 * CallListener
 * Listens for calls and creates new call objects if
 * one is detected
 */
public class CallListener {
    boolean running = true;
    public CallListener (int port) {
        try{
            ServerSocket server = new ServerSocket(port);
        
            while(running){
                
                try {
                    new Thread( new Call(server.accept())).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            server.close();
        }catch(Exception e){

        }        
        
 
    }

}