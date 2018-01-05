package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
class CopyWorker extends SwingWorker<Object, Integer> {

    private InputStream in;
    private OutputStream out;
    private JProgressBar pb;
    final private static int BUF_SIZE = 1024 * 64;

    public CopyWorker(JProgressBar pb, InputStream in, OutputStream out) {
        this.in = in;
        this.pb = pb;
        this.out = out;
    }

    @Override
    protected Object doInBackground() throws Exception {
        byte[] b = new byte[BUF_SIZE];
	    int len, cv = 0;
	    while ((len = in.read(b)) >= 0) {
	    	out.write(b, 0, len);
	    	cv = cv + len;
	    	publish(cv);
	    }
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks){
       for (Integer chunk : chunks) {
    	   pb.setValue(chunk);
       }
    }
    
    @Override
    protected void done() {
        try {
        	in.close();
        	out.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
