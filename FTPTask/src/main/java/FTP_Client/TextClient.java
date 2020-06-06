package FTP_Client;
// Example: Client that receives and sends characters
// CharacterClient.java

import java.net.*;
import java.io.*;
import java.util.*;

public class TextClient {

    private static int controlPort = 21;
    private static String hostDirection = "localhost";
    private static String dataTCP = "";
    public static int dataPortClient = -1;
    private static boolean hasPort = false;

    public static PrintWriter output;

    public static void testClient() {

        /* 	Need this for reading commands from server
		 * 		String result = input.readLine();
		 * 		System.out.println(result);
		 */

		try {
			// Send & recover data
			String data = "";

			// Connect with the server
			Socket connection = new Socket(hostDirection, controlPort);

			// Recover input & output from connection
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = new PrintWriter(connection.getOutputStream(), true);

			hasPort = false;
			//Boolean loggegIn = false;
			//while (!loggegIn) loggegIn = logIn(input, output);

			ShowGuideline();

			String currentDirectory = "files\\";
			String baseDirectory = "files\\";

			while (data.compareTo("END") != 0) {

				// Input for reading from keyboard
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("FTP (END to close the server): " + currentDirectory + "> ");
				data = inputKeyboard.readLine();

				if (data.startsWith("send")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String filename = baseDirectory + command[1];
						dataTCP = "STOR" + " " + filename; 						// STOR <SP> <pathname> <CRLF>
						System.out.println("Attempting to send file: " + filename);
						if(hasPort)	DataClient.sendFile(filename, dataPortClient);
						input.readLine();
					}
					else {
						dataTCP = "STOR";
						System.out.println("Format is not correct.");
					}
					output.println(dataTCP);
				}
				else if (data.startsWith("prt")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						dataPortClient = Integer.parseInt(command[1]);
						dataTCP = "PRT" + " " + dataPortClient;					// PORT <SP> <host-port> <CRLF>
						System.out.println("dataTCP: " + dataTCP);
						output.println(dataTCP);
						hasPort = true;
					}
					else{
						System.out.println("Format is not correct");
					}
				}
				else if (data.startsWith("get")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String filename = command[1];
						dataTCP = "RETR" + " " + filename; 						// RETR <SP> <pathname> <CRLF>
						System.out.println("Attempting to get file: " + filename);
						if(hasPort)	DataClient.receiveFile(currentDirectory + filename, dataPortClient);
					}
					else{
						System.out.println("Format is not correct");
						dataTCP = "RETR";
					}
					output.println(dataTCP);
				}
				else if (data.startsWith("delete")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String pathDirectory = command[1];
						dataTCP = "DELE" + " " + pathDirectory; 				// DELE <SP> <pathname> <CRLF>
					}
					else dataTCP = "DELE";
					System.out.println(dataTCP);
					output.println(dataTCP);
				}
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					if(command.length == 3){
						dataTCP = "RNFR" + " " + command[1] + " " + command[2];	// RNFR <SP> <pathname> <CRLF>
					}
					else dataTCP = "RNFR";
					output.println(dataTCP);
					System.out.println(dataTCP);
				}
				else if (data.startsWith("list")) {
					String[] command = data.split(" "); //For directory
					dataTCP = "LIST";
					//dataTCP = "LIST" + " " + command[1]; 						// LIST [<SP> <pathname>] <CRLF>
					System.out.println(dataTCP);
					output.println(dataTCP);
					receiveListFiles(input);
				}
				else if (data.startsWith("get path")) {
					dataTCP = "PWD"; 											// PWD <CRLF>
					output.println(dataTCP);
				}
				else if (data.startsWith("cd")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						dataTCP = "CWD" + " " + command[1]; 						// CWD <SP> <pathname> <CRLF>
						String directory = input.readLine();
	
						if (!directory.isEmpty()) {
							currentDirectory = directory;
						} else {
							System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
						}
					}
					else { dataTCP = "CWD"; }
					output.println(dataTCP);
				}
				else if (data.startsWith("mkdir")) {
					String[] command = data.split(" ");
					String directory = currentDirectory + command[1];
					dataTCP = "MKD" + " " + directory; 							// MKD <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to create directory: " + directory);
					// receiveFile(filename);
					// new File(directory).mkdir();
				}
				else if (data.startsWith("remove")) {
					String[] command = data.split(" ");
					dataTCP = "RMD" + command[1];								// RMD <SP> <pathname> <CRLF>
					output.println(dataTCP);
				}
				else if (data.startsWith("user")) {
					String[] command = data.split(" ");
					String user = command[1];
					dataTCP = "USER" + " " + user; 								// USER <SP> <username> <CRLF>
					output.println(dataTCP);
					// System.out.println(dataTCP);
				}
				else if (data.startsWith("password")) {
					String[] command = data.split(" ");
					String password = command[1];
					dataTCP = "PASS" + " " + password; 							// PASS <SP> <password> <CRLF>
					output.println(dataTCP);
					// System.out.println(dataTCP);
				}
				else if(data.startsWith("quit")){
					dataTCP = "QUIT";
					output.println(dataTCP);

				}
				else if (data.compareTo("END") == 0) {
					output.println(data);
				}
				else if(data.startsWith("path")){
					System.out.println(System.getProperty("user.dir"));
					
					//First part: divides the path in parts
					String[] command = data.split(" ");
					String path = System.getProperty("user.dir");

					Stack<String> stack = new Stack<String>();
	 
					while(path.length()> 0 && (path.charAt(path.length()-1) =='\\' || path.charAt(path.length()-1) =='/')){
						path = path.substring(0, path.length()-1);
					}
					
					int start = 0;
					
					for(int i=1; i<path.length(); i++){
						if(path.charAt(i) == '\\' || path.charAt(i) == '/'){
							stack.push(path.substring(start, i));
							start = i;
						}else if(i==path.length()-1){
							stack.push(path.substring(start));
						}
					}
					System.out.println("The end " + stack.size());
					int tam = stack.size();
					for(int j = 0; j<tam; j++){
						System.out.println(j + " " + stack.pop() + ", ");
					}
				}
				else {
					System.out.println("Error: Command unrecognised");
				}
				// result = input.readLine();
				// Send data to server
				// output.println(data);
				// Read data from server
				// result = input.readLine();
				/*
				 * if (result.compareTo("ok") !=0) {
				 *
				 * sendFile(data);
				 *
				 * } if(data.compareTo("END") !=0) { System.out.println("Data = " + data +
				 * " --- Result = " + result); }
				 */

			}

			// Close the connection
			connection.close();
			System.out.println("Client Closed");

		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	public static boolean receiveListFiles(BufferedReader input) {
		try {
			System.out.println("Here is the list of files on the server:");

			String s = input.readLine();
			while (s.compareTo("END") != 0) {
				System.out.println(" > " + s);
				s = input.readLine();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean logIn(BufferedReader input, PrintWriter output) {
		String data = null;
		try {
			BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("To use this server you must first log in.");
			System.out.print("Username: ");

			data = inputKeyboard.readLine();
			output.println(data);
			String result = input.readLine();
			System.out.println(result);
			if (result.compareTo("User WRONG") == 0)
				return false;

			System.out.print("Password: ");
			data = inputKeyboard.readLine();
			output.println(data);
			result = input.readLine();
			System.out.println(result);
			if (result.compareTo("Password WRONG") == 0)
				return false;

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error : " + e);
		}

		return false;
	}

	public static void ShowGuideline() {
		System.out.println("Possible actions:\n");
		System.out.println("\t1. List files in directory. \t\tCOMMAND: 'list + path'");
		System.out.println("\t2. Download file. \t\t\tCOMMAND: 'get + file'");
		System.out.println("\t3. Upload file. \t\t\tCOMMAND: 'send + file'");
		System.out.println("\t4. Close connection. \t\t\tCOMMAND: 'quit'");
		System.out.println("\t5. Delete file. \t\t\tCOMMAND: 'delete + path'\n");
		System.out.println("\t6. Get path of working directory. \tCOMMAND: 'get path'");
		System.out.println("\t7. Change current directory. \t\tCOMMAND: 'cd + path'");
		System.out.println("\t8. Create directory. \t\t\tCOMMAND: 'mkdir + path'");
		System.out.println("\t9. Remove directory. \t\t\tCOMMAND: 'remove + path'");
		System.out.println("\t10. Rename file or directory. \t\tCOMMAND: 'rename + path'\n");
		System.out.println("\t11. Indicate port for file transfer.  \t\tCOMMAND: 'prt + number'\n");
		System.out.println("'Quit' or 'END' for finishing connection.\n");
	}
}
