package FTP_Server;
// Server that receives and sends bytes.
// ByteServer

import java.net.*;
import java.io.*;

public class DataServer {

	public static void testServer() {

		try {

			int port = 1300;
			int data = -1;

			// Creamos el socket del servidor
			ServerSocket sServ = new ServerSocket(port);
			System.out.println("Server waiting for requests");
			
			// Aceptamos conexión y creamos conexión con cliente
			Socket sCon = sServ.accept();
			System.out.println("Connection accepted");

			while(data != 0) {
				
				// Input y output de datos
				DataInputStream input = new DataInputStream(sCon.getInputStream());	   
				DataOutputStream output = new DataOutputStream(sCon.getOutputStream());
				
				// Leemos lo que nos mandan
				data =  input.readInt();
				System.out.println("Received: " + data);
				int mult = data * 2;
				
				// Mandamos el resultado
				output.writeInt(mult);

			}    

			// Cerramos la conexión
			sCon.close();        
			
			// Cerramos el servidor
			sServ.close();
			System.out.println("Server closed");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
} 