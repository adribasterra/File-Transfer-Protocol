package FTP_Client;

// Example: Client that receives and sends bytes
// ByteClient

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataClient {

	 //Service ready for new user
	public static final String CMD_SERVICE_READY = "220";
	
	 //Command okay
	public static final String CMD_OKAY = "200";
	
	 //Bad sequence of commands
	public static final String CMD_BAD_SEQUENCE = "503";
	
	 //File status okay; about to open data connection
	public static final String CMD_FILE_STATUS_OKAY = "150";

	 //Closing data connection. Requested file action succesful
	public static final String CMD_SUCCESS = "226";
	
	 //Can't open data connection
	public static final String CMD_CANT_OPEN_CONNECTION = "425";
	
	 //Requested action aborted: local error in processing
	public static final String CMD_ACTION_ABORTED = "451";
	
	 //Requested file action not taken. File unavailable
	public static final String CMD_FILE_ACTION_UNAVAILABLE= "450";
	
	 //Requested action not taken. File unavailable
	public static final String CMD_FILE_UNAVAILABLE = "550";
	
	 //Connection closed; transfer aborted
	public static final String CMD_TRANSFER_ABORTER = "426";
	
	 //Requested action not taken. Insufficient storage space in system
	public static final String CMD_INSUFFICIENT_STORAGE = "452";
	
	 //Requested action not taken. File name not allowed
	public static final String CMD_FILENAME_NOT_ALLOWED = "553";
	
	 //Service closing control connection
	public static final String CMD_CLOSING = "221";
	
	
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
	
	public static void ManageResponses(String response, String command) {
		
		switch(response) {
			case "LIST":
				switch(command) {
					case CMD_FILE_STATUS_OKAY:			//150
						// Possible commands: 226, 425, 550
						break;
						
					case CMD_FILE_ACTION_UNAVAILABLE:	//450
						break;
						
					case CMD_FILE_UNAVAILABLE:			//550
						break;
				}
				break;
				
			case "RETR":
				switch(command) {
					case CMD_FILE_STATUS_OKAY:			//150
						// Possible response commands: 226, 425, 426, 451
						break;
						
					case CMD_FILE_ACTION_UNAVAILABLE:	//450
						break;
						
					case CMD_FILE_UNAVAILABLE:			//550
						break;
				}
				break;
				
			case "STOR":
				switch(command) {
					case CMD_FILE_STATUS_OKAY:			//150
						// Possible response commands: 226, 425, 451
						break;
						
					case CMD_FILE_ACTION_UNAVAILABLE:	//450
						break;
						
					case CMD_INSUFFICIENT_STORAGE:		//452
						break;
						
					case CMD_FILENAME_NOT_ALLOWED:		//553
						break;
				}
				break;
			
			case "QUIT":
				if (response2.equals(CMD_CLOSING)){
					connection.close();
					System.out.println("Conection closed.");
				}
				break;
		}
	}
}

