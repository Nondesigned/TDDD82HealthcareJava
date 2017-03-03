/**
 * CommFlags
 */
public class CommFlags implements Flags{
    
    byte[] bytes = new byte[2];
    public CommFlags () {
        
    }

    public byte[] getBytes(){
        return new byte[2];
    }

    public boolean getFlag(int i) {
        return ServerUtils.getBit(bytes[(int)(i/8)], i) != 0;
    }
    public void setFlag(CommFlag flag, boolean value) {
        switch (flag) {
            case INITCALL:
                ServerUtils.setBit(bytes[0], 0, value);
                break;
            case ENDCALL:
                ServerUtils.setBit(bytes[0], 1, value);
                break;
            case ACCEPTCALL:
                ServerUtils.setBit(bytes[0], 2, value);
                break;
            case DECLINECALL:
                ServerUtils.setBit(bytes[0], 3, value);
                break;
            case ASKFORENC:
                ServerUtils.setBit(bytes[0], 4, value);
                break;
            case ENC1 :
                ServerUtils.setBit(bytes[0], 5, value);
                break;
            case ENC2:
                ServerUtils.setBit(bytes[0], 6, value);
                break; 
            case ENC3:
                ServerUtils.setBit(bytes[0], 7, value);
                break;
            case INITVID:
                ServerUtils.setBit(bytes[1], 0, value);
                break;
            case ENDVID:
                ServerUtils.setBit(bytes[1], 1, value);
                break;
            default:
                break;
        }
    }
    
}