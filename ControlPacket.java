import java.util.Arrays;

/**
 * ControlPacket
 */
public class ControlPacket {
    byte[] data;
    public ControlPacket (byte[] data) {
        this.data = data;
    }

    /**-----|Public Methods|------**/

    /**
     * Returns the byte-data
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Returns the value of flag i
     */
    public boolean getFlag(int i) {
        
    }

    /**------|Private methods|------**/
    /** Returns byte[] with flags **/
    private byte[] getFlagArray(){
        return Arrays.copyOfRange(data, 8, 10);
    }


}