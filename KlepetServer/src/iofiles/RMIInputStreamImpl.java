package iofiles;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import interfaces.*;
public class RMIInputStreamImpl implements RMIInputStreamInterf {
	private InputStream in;
    private byte[] b;

    public RMIInputStreamImpl(InputStream in) throws IOException {
        this.in = in;
        UnicastRemoteObject.exportObject(this, 5004);
    }

    public void close() throws IOException, RemoteException {
        in.close();
    }

    public int read() throws IOException, RemoteException {
        return in.read();
    }

    public byte[] readBytes(int len) throws IOException, 
            RemoteException {
        if (b == null || b.length != len)
            b = new byte[len];
            
        int len2 = in.read(b);
        if (len2 < 0)
            return null; // EOF reached
        
        if (len2 != len) {
            // copy bytes to byte[] of correct length and return it
            byte[] b2 = new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        }
        else
            return b;
    }
}
