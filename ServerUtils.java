import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ServerUtils
 */
public class ServerUtils{
    public static byte[] inputStreamToByte(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1)
            buffer.write(data, 0, nRead);
        buffer.flush();
        
        return buffer.toByteArray();
    }

    /**
     * Return the i:th bit from byte
     */
    public static Integer getBit(byte input, int position){
        return ((input >> position) & 1);
    }

    /**
     * Sets range of array
     */
    public static byte[] setRange(byte[] source, byte[] destination, int start, int end ){
        for(int i=start; i <= end; i++)
            destination[i]=source[i-start];
        return destination;
    }

    /**
     * Sets range of array
     */
    public static byte[] setRange(byte[] source, byte[] destination, int start){
        for(int i=start; i < start+source.length; i++)            
            destination[i]=source[i-start];
            
        return destination;
    }
}