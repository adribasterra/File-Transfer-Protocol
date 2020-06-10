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

			System.out.println(input.readLine()); //Get the 220. command
			hasPort = false;
			//Boolean loggegIn = false;
			//while (!loggegIn) loggegIn = logIn(input, output);

			ShowGuideline();

			String currentDirectory = "files\\";
			//String baseDirectory = System.getProperty("user.dir");

			while (data.compareTo("END") != 0) {

				// Input for reading from keyboard
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("FTP (END to close the server): > ");
				data = inputKeyboard.readLine();

				if (data.startsWith("send")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String filename = command[1];
						dataTCP = "STOR" + " " + filename; 						// STOR <SP> <pathname> <CRLF>
						output.println(dataTCP);
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
						// if (response != not ok) || response equals OK
						System.out.println("Attempting to send file: " + filename);
						if(hasPort)	{
							boolean doIt = false;
							try{
								String response = input.readLine();
								System.out.println(response);
								if(response.startsWith("150")) doIt = true;
							} catch (IOException e){
								System.out.println(e);
							}
							if(doIt) {
								DataClient.sendFile(filename, dataPortClient);
								try{
									String response = input.readLine();
									System.out.println(response);
								} catch (IOException e){
									System.out.println(e);
								}
							}
						}
					}
					else {
						dataTCP = "STOR";
						output.println(dataTCP);
						System.out.println("Format is not correct.");
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
					}
					//System.out.println(input.readLine());
				}
				else if (data.startsWith("prt")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						try{
							dataPortClient = Integer.parseInt(command[1]);
							if(dataPortClient != 21){
								dataTCP = "PRT" + " " + dataPortClient;				// PORT <SP> <host-port> <CRLF>
								output.println(dataTCP);
								hasPort = true;
								try{
									String response = input.readLine();
									System.out.println(response);
								} catch (IOException e){
									System.out.println(e);
								}
							}
							else { System.out.println("The use of contorlPort as dataPort is not allowed"); }
						} catch(NumberFormatException e){
							System.out.println("It is not a number.");
						}
					}
					else{
						System.out.println("Format is not correct");
					}
					//System.out.println(input.readLine());
				}
				else if (data.startsWith("get")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String filename = command[1];
						dataTCP = "RETR" + " " + filename; 						// RETR <SP> <pathname> <CRLF>
						output.println(dataTCP);
						System.out.println("Attempting to get file: " + filename);
						if(hasPort) {
							boolean doIt = false;
							try{
								String response = input.readLine();
								System.out.println(response);
								if(response.startsWith("150")) doIt = true;
							} catch (IOException e){
								System.out.println(e);
							}
							if(doIt) {
								DataClient.receiveFile(filename, dataPortClient);
								try{
									String response = input.readLine();
									System.out.println(response);
								} catch (IOException e){
									System.out.println(e);
								}
							}
						}
					}
					else{
						System.out.println("Format is not correct");
						dataTCP = "RETR";
						output.println(dataTCP);
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
					}
					//System.out.println(input.readLine());
				}
				else if (data.startsWith("list")) {
					String[] command = data.split(" "); //For directory
					if(command.length == 2){
						String path = currentDirectory + command[1];
						dataTCP = "LIST" + " " + path;
						output.println(dataTCP);
						//dataTCP = "LIST" + " " + command[1]; 						// LIST [<SP> <pathname>] <CRLF>
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
						if(hasPort)	DataClient.receiveListFiles(dataPortClient);
					}
					else {
						dataTCP = "LIST";
						output.println(dataTCP);
						if(hasPort)	DataClient.receiveListFiles(dataPortClient);
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
					}
				}
				else if (data.startsWith("delete")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String pathDirectory = command[1];
						dataTCP = "DELE" + " " + pathDirectory; 				// DELE <SP> <pathname> <CRLF>
					}
					else { dataTCP = "DELE"; }
					System.out.println(dataTCP);
					//System.out.println(input.readLine());
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					if(command.length == 3){
						dataTCP = "RNFR" + " " + command[1] + " " + command[2];	// RNFR <SP> <pathname> <CRLF>
					}
					else { dataTCP = "RNFR"; }
					//System.out.println(input.readLine());
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if (data.startsWith("wdir")) {
					dataTCP = "PWD"; 											// PWD <CRLF>
					output.println(dataTCP);
					//System.out.println(input.readLine());
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if (data.startsWith("cd")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						dataTCP = "CWD" + " " + command[1]; 					// CWD <SP> <pathname> <CRLF>
						output.println(dataTCP);
						System.out.println(dataTCP);
						String directory = input.readLine();
						
						if (!directory.isEmpty()) {
							currentDirectory = directory;
						} else {
							System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
						}
					}
					else { 
						dataTCP = "CWD";
						output.println(dataTCP);
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
					}
					//System.out.println(input.readLine());
				}
				else if (data.startsWith("mkdir")) {
					String[] command = data.split(" ");
					String directory = command[1];
					dataTCP = "MKD" + " " + directory; 							// MKD <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to create directory: " + directory);
					//System.out.println(input.readLine());
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if(data.startsWith("wdir")){
					dataTCP = "PWD"; 											// PWD <CRLF>
				}
				else if (data.startsWith("remove")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						dataTCP = "RMD" + " " + command[1];						// RMD <SP> <pathname> <CRLF>
					}
					else dataTCP = "RMD";
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
					//System.out.println(input.readLine());
				}
				else if (data.startsWith("user")) {
					String[] command = data.split(" ");
					String user = command[1];
					dataTCP = "USER" + " " + user; 								// USER <SP> <username> <CRLF>
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if (data.startsWith("password")) {
					String[] command = data.split(" ");
					String password = command[1];
					dataTCP = "PASS" + " " + password; 							// PASS <SP> <password> <CRLF>
					output.println(dataTCP);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if(data.startsWith("quit")){
					data = "END";
					output.println(data);
					try{
						String response = input.readLine();
						System.out.println(response);
					} catch (IOException e){
						System.out.println(e);
					}
				}
				else if (data.startsWith("end") || data.startsWith("END")) {
					data = "END";
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
					//System.out.println(input.readLine());
				}
			}

			try{
				String response = input.readLine();
				System.out.println(response);
			} catch (IOException e){
				System.out.println(e);
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
