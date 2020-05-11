package FTP_Client;

// Example: Client that receives and sends bytes
// ByteClient

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DataClient {

	 //Service ready for new user
	public static final String CMD_SERVICE_READY = "220. Service ready for new user.";
	
	 //Command okay
	public static final String CMD_OKAY = "200. Okay";
	
	 //Bad sequence of commands
	public static final String CMD_BAD_SEQUENCE = "503. Bad sequence of commands.";
	
	 //File status okay; about to open data connection
	public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";

	 //Closing data connection. Requested file action succesful
	public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";
	
	 //Can't open data connection
	public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";
	
	 //Requested action aborted: local error in processing
	public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";
	
	 //Requested file action not taken. File unavailable
	public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";
	
	 //Requested action not taken. File unavailable
	public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";
	
	 //Connection closed; transfer aborted
	public static final String CMD_TRANSFER_ABORTER = "426. Connection closed; transfer aborted.";
	
	 //Requested action not taken. Insufficient storage space in system
	public static final String CMD_INSUFFICIENT_STORAGE = "452. Requested action not taken. Insufficient storage space in system.";
	
	 //Requested action not taken. File name not allowed
	public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";
	
	 //Service closing control connection
	public static final String CMD_CLOSING = "221. Service closing control connection.";


	private static Socket connection;
	private static String command;
	
	
	public static void testClient() {

		try {
			int commandPort = 21;
			boolean connected = true;
			command = null;
			String response;

			// Connect to the server
			connection = new Socket("localhost", commandPort);

			// Get the input/output from the socket
			DataInputStream input = new DataInputStream(connection.getInputStream()); // Usado para recoger lo que nos mande el servidor
			DataOutputStream output = new DataOutputStream(connection.getOutputStream()); // Usado para mandar al servidor lo que escribamos

			// Read from keyboard
			BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in)); 
		
			while(connected) {
				System.out.print("Write command: ");
				// Command: PORT <SP> <host-port> <CRLF>
				command = inputKeyboard.readLine();
				
				
				// Send data to the server
				output.writeChars(command);
				
				// Read data form the server

				// Clean read/write
				output.flush();
				
				// Print response
				System.out.println("Data = " + command + " --- Response = " );		
				
			}

			// Close connection
			connection.close();
			System.out.println("Connection closed.");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void ManageResponses(String response, String command) throws IOException {
		
		switch(response) {
			case "LIST":
				// Command: LIST <SP> <pathname> <CRLF>
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
				// Command: RETR <SP> <pathname> <CRLF>
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
				// Command: STOR <SP> <pathname> <CRLF>
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
				// Command: QUIT <CRLF>
				if (command.equals(CMD_CLOSING)){
					connection.close();
					System.out.println("Conection closed.");
				}
				break;
		}
	}
}

