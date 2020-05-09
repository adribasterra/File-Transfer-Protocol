package FTP_Server;
// Server that receives and sends bytes.
// ByteServer

import java.net.*;
import java.io.*;

public class DataServer {

	
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


	private static Socket serverConnection;
	private static ServerSocket serverSocket;
	
	private static String command;
	private static String action;
	private static String pathname;
	
	
	public static void testServer() {

		try {

			int commandPort = 21;
			command = null;

			// Create the server socket
			serverSocket = new ServerSocket(commandPort);
			System.out.println("Server waiting for requests");
			
			// Accept and create connection with client
			serverConnection = serverSocket.accept();
			System.out.println("Connection accepted");
			ShowOptions();
			
			//Once connected, show command
			System.out.println(CMD_SERVICE_READY);

			while(command != null) {
				
				// Data input & output
				DataInputStream input = new DataInputStream(serverConnection.getInputStream());	   
				DataOutputStream output = new DataOutputStream(serverConnection.getOutputStream());
				
				// Read received data
				command =  input.readLine();
				DivideCommand(command);
				String response = "Received: " + command;
				
				
				// Send the response
				output.writeChars(response);

			}    

			// Close connection
			serverConnection.close();        
			
			// Close server
			serverSocket.close();
			System.out.println("Server closed");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void ShowOptions() {
		System.out.println("Available options: ");
		System.out.println("1. LIST <pathname> - List files in specified directory.");
		System.out.println("2. RETR <pathname> - Download specified file from server.");
		System.out.println("3. STOR <pathname> - Upload specified file to server.");
		System.out.println("4. QUIT - Close connection.");
	}
	
	private static void DivideCommand(String command) {
		action = null;
		pathname = null;
		int i = 0;
		
		if (command.length() > 3) {
			if(command.contains(" ")) {
				while(command.substring(i) != " ") {
					action += command.substring(i);
					i++;
				}
			}
			i++;
			if(Character.isLetter(command.substring(i).toCharArray()[0])) {
				while (command.substring(i) != 	"\n") {
					pathname += command.substring(i);
				}
			}
		}
		else{
			System.out.println("ERROR. Bad format of command");
		}
	}
	
	public static String ManageResponses(String action, String pathname) throws IOException {
		String returned = null;
		switch(action) {
			case "LIST":
				File file_LIST = new File(pathname);
				if(file_LIST.exists()) {
					returned = CMD_FILE_STATUS_OKAY;			//150
					// Possible commands: 226, 425, 550
				}
				else if(!file_LIST.exists()) {
					returned = CMD_FILE_ACTION_UNAVAILABLE;		//450
				}
				else if(!file_LIST.canRead()) {
					returned = CMD_FILE_UNAVAILABLE;			//550
				}
				
			case "RETR":
				File file_RETR = new File(pathname);
				if(file_RETR.exists()) {
					returned = CMD_FILE_STATUS_OKAY;			//150
					// Possible commands: 226, 425, 426, 451
				}
				else if(!file_RETR.exists()) {
					returned = CMD_FILE_ACTION_UNAVAILABLE;		//450
				}
				else if(!file_RETR.canRead()) {
					returned = CMD_FILE_UNAVAILABLE;			//550
				}
				
			case "STOR":
				File file_STOR = new File(pathname);
				if(file_STOR.exists()) {
					returned = CMD_FILE_STATUS_OKAY;			//150
					// Possible commands: 226, 425, 451
				}
				else if(!file_STOR.exists()) {
					returned = CMD_FILE_ACTION_UNAVAILABLE;		//450
				}
				
				switch(command) {
					case CMD_INSUFFICIENT_STORAGE:				//452
						break;
				}
				
				if(!file_STOR.canRead()) {
					returned = CMD_FILENAME_NOT_ALLOWED;		//553
				}
				break;
			
			case "QUIT":
				returned = CMD_CLOSING;							//221
				break;
		}
		return returned;
	}
} 