import sun.plugin2.message.Message;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by mariosp on 16/2/16.
 */
public class Listener extends Thread{
    boolean useBufferedIO;
    Socket client = null;
    long start = -1;
    int messagesRx = 0;

    public Listener(Socket s, boolean useBufferedIO){
        this.client = s;
        this.useBufferedIO = useBufferedIO;
        start();//start the thread
    }

    public void run(){
        //wait for messages from Client
        try{
            ObjectInputStream ois = null;
            if (useBufferedIO){
                ois = new ObjectInputStream(
                        new BufferedInputStream( client.getInputStream() ));
            }
            else{
                ois = new ObjectInputStream( client.getInputStream() );
            }

            System.out.println(this + " listening for messages");
            boolean signal = true;
            while(signal){
                String msg = (String)ois.readObject();
                //System.out.println(msg);
                messagesRx++;
                if (start == -1){start = System.currentTimeMillis();}
                if (msg.equalsIgnoreCase("EXIT_666")){signal=false;}
            }
            long end = System.currentTimeMillis();
            double duration = ((double)(end - start)/1000.0);
            double rate     = (double)(messagesRx/duration);
            System.out.println("Received "+messagesRx+" objects at "+rate+" per sec.");

        }
        catch(Exception e){
            e.printStackTrace();
        }

        System.out.println(this+" terminating");
        System.exit(0);
    }
}
