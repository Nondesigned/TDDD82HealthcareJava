/**
 * Java server 
 */
public class Server {

    public static void main(String[] args) throws Exception{
        new DataListener(1338).start();
        CallListener listener = new CallListener(1337);
    }
}