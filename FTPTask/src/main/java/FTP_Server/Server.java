/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//WAIT ! THIS CLASS MIGHT NOT FOR THE PROJECT IS JUST FOR TEST PUPOSEES, BY JUAN


/**
 *
 * @author jojoj
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        ServerSocket server;
        server = new ServerSocket(21);
        System.out.println("Character Server waiting for requests");
        while(true){
        
        Socket Client = new Socket();
        Client = server.accept();
        ServerThread Thread = new ServerThread(Client);
        Thread.start();
        System.out.println("An anonymous user is conecting to the server...");
                
        
        }
        
    }
    
}
