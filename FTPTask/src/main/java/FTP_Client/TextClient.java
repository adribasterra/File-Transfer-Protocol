package FTP_Client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class TextClient {

    private static int controlPort = 21;
    private static String hostDirection = "localhost";
    private static String dataTCP = "";
    public static int dataPortClient = -1;
    private static boolean hasPort = false;

    public static PrintWriter output;

    public static void testClient() {

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

			ShowGuideline();

			String currentDirectory = "files\\";
			String baseDirectory = "files\\";

			while (data.compareTo("END") != 0) {

				// Input for reading from keyboard
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("FTP : "+currentDirectory+"> ");
				data = inputKeyboard.readLine();

				if (data.startsWith("send")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						String filename = command[1];
						dataTCP = "STOR" + " " + filename; 						// STOR <SP> <pathname> <CRLF>
						output.println(dataTCP);
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
								DataClient.sendFile(baseDirectory + filename, dataPortClient);
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
								DataClient.receiveFile(baseDirectory + filename, dataPortClient);
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
				}
				else if (data.startsWith("list")) {
					String[] command = data.split(" "); //For directory
					if(command.length == 2){
						String path = command[1];
						dataTCP = "LIST" + " " + path;								// LIST [<SP> <pathname>] <CRLF>
						output.println(dataTCP);
						if(hasPort)	DataClient.receiveListFiles(dataPortClient);
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
						}
						
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
					else {
						dataTCP = "RNFR";
					}
					output.println(dataTCP);
					System.out.println(dataTCP);
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
						try{
							String response = input.readLine();
							System.out.println(response);
						} catch (IOException e){
							System.out.println(e);
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
				}
				else if (data.startsWith("mkdir")) {
					String[] command = data.split(" ");
					String directory = command[1];
					dataTCP = "MKD" + " " + directory; 							// MKD <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to create directory: " + directory);
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
				else if (data.startsWith("help")) {
					ShowGuideline();
				}
				else {
					System.out.println("Error: Command unrecognised");
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
		System.out.println("\t11. Indicate port for file transfer.  \tCOMMAND: 'prt + number'\n");
		System.out.println("'Quit' or 'END' for finishing connection.\n");
	}
}
