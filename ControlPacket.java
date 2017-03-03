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
    
    //----{GETERS}----

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
        return ServerUtils.getBit(getFlagArray()[(int)(i/8)], i) != 0;
    }

    public Integer getSource() {
        return java.nio.ByteBuffer.wrap(getSourceArray()).getInt();
    }

    public Integer getDestination() {
        return java.nio.ByteBuffer.wrap(getDestinationArray()).getInt();
    }

    public Integer getContentLength(){
        return java.nio.ByteBuffer.wrap(getContentLengthArray()).getInt();
    }

    
    /**------|Private methods|------**/

    /** Returns byte[] with flags **/
    private byte[] getFlagArray(){
        return Arrays.copyOfRange(data, 8, 9);
    }

    /** Returns byte[] with source address **/
    private byte[] getSourceArray(){
        return Arrays.copyOfRange(data, 0, 3);
    }

    /** Returns byte[] with destination address **/
    private byte[] getDestinationArray(){
        return Arrays.copyOfRange(data, 4, 7);
    }
    
    /** Returns byte[] with contetlength **/
    private byte[] getContentLengthArray(){
        return Arrays.copyOfRange(data, 10, 12);
    }



}