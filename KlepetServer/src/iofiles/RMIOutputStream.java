package iofiles;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import interfaces.*;
public class RMIOutputStream extends OutputStream implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RMIOutputStreamInterf out;
    
    public RMIOutputStream(RMIOutputStreamImpl out) {
        this.out = out;
    }
    
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(byte[] b, int off, int len) throws 
            IOException {
        out.write(b, off, len);
    }
    
    public void close() throws IOException {
        out.close();
    }   
}
