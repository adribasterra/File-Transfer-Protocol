package FTP_Client;

import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    ServerSocket sServ = null;
    Socket sCon = null;

    public Connection(int port) {
        try {
            this.sServ = new ServerSocket(port);
            System.out.println("Client waiting for response before sending");
            
            this.sCon = sServ.accept();
            System.out.println("File transfer Connection accepted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void close(){
        try { 
            this.sCon.close();
            this.sCon=null;

            this.sServ.close();
            this.sServ = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}