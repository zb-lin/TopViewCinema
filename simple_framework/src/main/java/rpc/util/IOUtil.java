package rpc.util;


import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

    public static byte[] readStream(InputStream inputStream) throws IOException {
        int firstByte = inputStream.read();
        int length = inputStream.available();
        byte[] bytes = new byte[length + 1];
        bytes[0] = (byte) firstByte;
        int read = inputStream.read(bytes, 1, length);
        return bytes;
    }

}
