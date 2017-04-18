/**
 * Flags
 */
public interface Flags {
    public void setFlag(ControlFlag flag, boolean value);
    public boolean getFlag(int i);
    public boolean getFlag(ControlFlag flag);
    public byte[] getBytes();
}