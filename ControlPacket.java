import java.nio.ByteBuffer;
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
    public byte[] getPacketBytes() {
        return this.data;
    }

    /**
     * Returns the value of flag i
     */
    public boolean getFlag(int i) {
        return ServerUtils.getBit(getFlagArray()[(int)(i/8)], i) != 0;
    }

    //Returns source address
    public Integer getSource() {
        return java.nio.ByteBuffer.wrap(getSourceArray()).getInt();
    }

    //Returns source address
    public Integer getDestination() {
        return java.nio.ByteBuffer.wrap(getDestinationArray()).getInt();
    }

    public String getKey() {
        return new String(Arrays.copyOfRange(data, 12, 44));
    }

    //Returns content-length
    public Integer getContentLength(){
        return java.nio.ByteBuffer.wrap(getContentLengthArray()).getInt();
    }

    //Returns payload content
    public byte[] getPayload() {
        return Arrays.copyOfRange(data, 44, getContentLength());
    }
    //Setters
    /**
     * Sets the value of flag i
     */
    public void setFlag(Flags flags) {
        data = ServerUtils.setRange(flags.getBytes(), data, 8);
    }

    //Sets source address
    public void setSource(int source) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(source).array();
        data = ServerUtils.setRange(bytes,data , 0);
    }

    //Sets destination address
    public void setDestination(int destination) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(destination).array();
        data = ServerUtils.setRange(bytes, data, 4);
    }

    public void setKey(String key) {
        data = ServerUtils.setRange(key.getBytes(), data ,12);
    }

    //Sets payload content and sets the Content-length
    public void setPayload(byte[] content) {
        data = ServerUtils.setRange(content, data, 44);
        data = ServerUtils.setRange(ByteBuffer.allocate(2).putInt(content.length).array(), data, 10);
    }

    /**------|Private methods|------**/

    /** Returns byte[] with flags **/
    private byte[] getFlagArray(){
        return Arrays.copyOfRange(data, 8, 10);
    }

    /** Returns byte[] with source address **/
    private byte[] getSourceArray(){
        return Arrays.copyOfRange(data, 0, 4);
    }

    /** Returns byte[] with destination address **/
    private byte[] getDestinationArray(){
        return Arrays.copyOfRange(data, 4, 8);
    }
    
    /** Returns byte[] with destination address **/
    private byte[] getKeyArray(){
        return Arrays.copyOfRange(data, 12, 44);
    }
    
    /** Returns byte[] with contetlength **/
    private byte[] getContentLengthArray(){
        return Arrays.copyOfRange(data, 10, 12);
    }


}