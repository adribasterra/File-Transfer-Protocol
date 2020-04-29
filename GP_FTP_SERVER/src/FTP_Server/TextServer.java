package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.
// CharacterServer.java

import java.net.*;
import java.io.*;

public class TextServer {

	public static void testServer() {

		try {
			
			int port = 1400;

			// Datos que recibiremos / mandaremos
			String data = "";

			// Crear el socket del servidor
			ServerSocket sServ = new ServerSocket(port);
			System.out.println("Character Server waiting for requests");
			
			// Aceptar conexi�n con cliente
			Socket sCon = sServ.accept();
			System.out.println("Connection accepted");

			while(data.compareTo("END") != 0) {


				// Coger input / output de la conexi�n
				BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);

				// Leer datos del cliente
				data =  input.readLine();
				System.out.println("Server receives: " + data);
				
				// Pasar a may�sculas
				data = data.toUpperCase();
				
				// Enviar el texto en may�sculas
				output.println(data);
		
			}
			
			// Cerrar la conexi�n
			sCon.close();

			// Cerrar el servidor
			sServ.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
}

