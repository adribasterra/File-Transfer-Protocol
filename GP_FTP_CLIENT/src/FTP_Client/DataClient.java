package FTP_Client;

// Example: Client that receives and sends bytes
// ByteClient

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataClient {

	public static void testClient() {

		try {

			int port = 1300;

			// Prueba - datos que mandaremos/recogeremos del servidor
			int data = -1;
			int result;

			// Conectarse al servidor
			Socket connection = new Socket("localhost", port);

			// Conseguir input/output de la conexión
			DataInputStream input = new DataInputStream(connection.getInputStream()); // Usado para recoger lo que nos mande el servidor
			DataOutputStream output = new DataOutputStream(connection.getOutputStream()); // Usado para mandar al servidor lo que escribamos

			// Recogemos un int de teclado
			BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in)); // Usado para leer lo que escribamos desde teclado
			
			
			while(data != 0) {
				System.out.print("Write a number (0 to close the server): ");
				data = Integer.parseInt(inputKeyboard.readLine());
				
				// Mandamos el int al servidor
				output.writeInt(data);
				
				// Leemos un int que nos manda el servidor
				result = input.readInt();

				// Limpiar lectura / escritura
				output.flush();
				
				// Imprimimos
				System.out.println("Data = " + data + " --- Result = " + result);		
				
				

			}

			// Cerramos la conexión
			connection.close();
			System.out.println("Conection closed.");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

