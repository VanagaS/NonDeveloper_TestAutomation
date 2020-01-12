package cc.iggy.api;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

/**
 * Created by VanagaS on 20-07-2017.
 *
 * Reads given text considering their BOM to identify the encoding to be used.
 *
 */
public class BufferedUnicodeReader extends Reader implements AutoCloseable {

    private BufferedReader bufferedReader;

    public BufferedUnicodeReader(InputStream inputStream) throws IOException {

        int numReadAheadBytes = 4; /* max BOM size */
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, numReadAheadBytes);
        byte bom[] = new byte[numReadAheadBytes];
        int numBytesRead;
        numBytesRead = pushbackInputStream.read(bom, 0, bom.length);
        Charset encoding;

        /* Detect the encoding of the stream based on the Byte Order Position (if present) of the stream */
        if( (bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF) ) {
            // UTF-8
            encoding = Charset.forName("UTF-8");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 3);
        } else if( (bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
            // UTF-16 Little Endian: FF FE
            encoding = Charset.forName("UTF-16LE");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 2);
        } else if( (bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
            // UTF-16 Big Endian: FE FF
            encoding = Charset.forName("UTF-16BE");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 2);
        } else if( (bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) && (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
            // UTF-32 Little Endian: FF FE 00 00
            encoding = Charset.forName("UTF-32LE");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 4);
        } else if( (bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
            // UTF-32 Big Endian: 00 00 FE FF
            encoding = Charset.forName("UTF-32BE");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 4);
        } else {
            // BOM is not present, default to utf-8 and rewind back to starting position
            encoding = Charset.forName("UTF-8");
            rewind(pushbackInputStream, bom, numBytesRead, numBytesRead - 4);
        }

        // Start using the detected encoding and create a decoder.
        CharsetDecoder charsetDecoder = encoding
                                            .newDecoder()
                                            .onUnmappableCharacter(CodingErrorAction.REPORT);

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetDecoder));
    }

    private void rewind(PushbackInputStream pushbackInputStream, byte bom[], int numBytesRead, int numReturnBytes)
            throws IOException {
        pushbackInputStream.unread(bom, (numBytesRead - numReturnBytes), numReturnBytes);
    }

    public void close() throws IOException {
        bufferedReader.close();
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        return bufferedReader.read(cbuf, off, len);
    }
}
