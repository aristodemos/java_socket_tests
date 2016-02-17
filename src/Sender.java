import sun.plugin2.message.Conversation;
import sun.plugin2.message.Message;



import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by mariosp on 16/2/16.
 */
public class Sender extends Thread {
    boolean useBufferedIO;
    int id;

    public Sender(boolean b){
        this.useBufferedIO = b;
        start();
    }

    public void run(){
        try{
            Socket sender = new Socket("localhost", 5454);
            if (sender!= null && sender.isConnected()){
                ObjectOutputStream oos = null;
                if (useBufferedIO){
                    oos = new ObjectOutputStream(
                            new BufferedOutputStream(sender.getOutputStream())
                    );
                }
                else{
                    oos = new ObjectOutputStream(sender.getOutputStream());
                }

                // Send 100,000 messages
                int messages = 1;
                while ( messages < 500000 ) {
                    messages++;
                    String msg = UUID.randomUUID().toString();
                    //System.out.println("uuid = " + msg);
                    oos.writeObject(msg);
                    oos.flush();
                }

                // Send the terminate message
                oos.writeObject("EXIT_666");
                oos.flush();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
