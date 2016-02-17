import java.util.Vector;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by mariosp on 16/2/16.
 */
public class Driver {
    Vector listeners = new Vector();
    boolean flag;

    public Driver(boolean fl){
        //initialize
        this.flag=fl;

        //Create a server socket in its own thread
        new Thread(){
            public void run(){
                System.out.println("Driver Thread Running");
                try{
                    ServerSocket clientConnect = new ServerSocket(5454);
                    while(true){
                        Socket client = clientConnect.accept();

                        //A new client connected, create a listener for it
                        Listener listener = new Listener(client, flag);
                        listeners.add(listener);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

        try{
            Thread.sleep(1000);
        }catch(Exception e){e.printStackTrace();}
        //Create a message sender
        //Sender sender = new Sender(flag);

        System.out.println("End of constructor");
    }

    public static void main(String[] args){
        Driver d = new Driver(true);
        //Driver c = new Driver(false);
    }
}
