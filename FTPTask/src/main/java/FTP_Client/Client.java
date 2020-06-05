/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


//WAIT ! THIS CLASS MIGHT NOT FOR THE PROJECT IS JUST FOR TEST PUPOSEES, BY JUAN

/**
 *
 * @author jojoj
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        String Host = "localhost";
        int Puerto = 21;
        Socket Client = new Socket(Host, Puerto);

        BufferedReader input = new BufferedReader(new InputStreamReader(Client.getInputStream()));
        PrintWriter output = new PrintWriter(Client.getOutputStream(), true);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        String cad, eco = "";
        System.out.println("introduce la cadena: ");
        cad = in.readLine();
        
        ////////////////////////////////////////
        
        while(!cad.equals("*")){
        
        output.println(cad);
        eco = input.readLine();
        System.out.println(eco);
            
        System.out.println("introducir otra cadena: ");
        cad = in.readLine();
        
        }
        output.close();
        input.close();
        System.out.println("fin de prueba");
        in.close();
        Client.close();

    }

}
