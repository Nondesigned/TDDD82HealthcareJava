import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * BufferUtils
 */
public class BufferUtils{
    public static byte[] inputStreamToByte(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1)
            buffer.write(data, 0, nRead);
        buffer.flush();
        
        return buffer.toByteArray();
    }

}