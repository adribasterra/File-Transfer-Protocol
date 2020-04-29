package FTP_Client;
// Example: Client that receives and sends characters
// CharacterClient.java

import java.net.*;
import java.io.*;

public class TextClient {

	public static void testClient() {

		try {

			int port = 1400;

			// Datos que mandaremos y recuperaremos
			String data = "";
			String result = "";

			// Conectar con el servidor
			Socket connection = new Socket("localhost", port);

			// Recuperar input / output de la conexión
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter output = new PrintWriter(connection.getOutputStream(), true);

			while(data.compareTo("END") != 0) {

				// Input para leer desde teclado
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Write text (END to close the server): ");
				data = inputKeyboard.readLine();

				// Mandar datos al servidor
				output.println(data);
				// Leer datos del servidor
				result = input.readLine();

				System.out.println("Data = " + data + " --- Result = " + result);	
			}


			// Close the connection
			connection.close();

		}  catch(IOException e) {
			System.out.println("Error: " + e);		
		}
	}   
}

