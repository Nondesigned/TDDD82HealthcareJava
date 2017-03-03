import java.io.IOException;
import java.net.ServerSocket;

/**
 * CallListener
 */
public class CallListener {
    boolean running = true;
    public CallListener (int port) {
        ServerSocket server = ServerSocket(port);
        while(running){
            
            try {
                new Thread( new call(server.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        server.close();
 
    }

}