package FTP_Client;
// Example: Client that receives and sends characters
// CharacterClient.java

import java.net.*;
import java.io.*;

public class TextClient {
	
	private static int controlPort = 21;
	private static String hostDirection = "localhost";
	private static String dataTCP = "";
	public static int dataPortClient = -1;

	public static PrintWriter output;

	public static void testClient() {

		try {

			//int port = 21;

			// Send & recover data
			String data = "";
			//String result = "";

			// Connect with the server
			Socket connection = new Socket(hostDirection, controlPort);

			// Recover input & output from connection
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = new PrintWriter(connection.getOutputStream(), true);

			Boolean loggegIn = false;
			while (!loggegIn) loggegIn = logIn(input, output);

			String currentDirectory = "files\\";
			String baseDirectory = "files\\";

			while (data.compareTo("END") != 0) {

				// Input for reading from keyboard
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("FTP (END to close the server): " + currentDirectory + "> ");
				data = inputKeyboard.readLine();

				if (data.startsWith("send")) {
					String[] command = data.split(" ");
					String filename = baseDirectory + command[1];
					dataTCP = "STOR" + " " + filename; 						// STOR <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to send file: " + filename);
					// sendFile(filename);
					DataClient.sendFile(filename, dataPortClient);
				}
				else if (data.startsWith("prt")) {
					String[] command = data.split(" ");
					if(command.length == 2){
						dataPortClient = Integer.parseInt(command[1]);
						dataTCP = "PRT" + " " + dataPortClient;				// PORT <SP> <host-port> <CRLF>
						System.out.println("dataTCP: " + dataTCP);
						output.println(dataTCP);
					}
					else{
						System.out.println("Bad structure of PORT");
					}
				}
				else if (data.startsWith("get")) {
					String[] command = data.split(" ");
					String filename = currentDirectory + command[1];
					dataTCP = "RETR" + " " + filename; 						// RETR <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to get file: " + filename);
					// receiveFile(filename);
					DataClient.receiveFile(filename, dataPortClient);
				} 
				else if (data.startsWith("mkdir")) {
					String[] command = data.split(" ");
					String directory = currentDirectory + command[1];
					dataTCP = "MKD" + " " + directory; 						// MKD <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println("Attempting to create directory: " + directory);
					// receiveFile(filename);
					// new File(directory).mkdir();
				} 
				else if (data.startsWith("cd")) {
					String[] command = data.split(" ");
					dataTCP = "CWD" + " " + command[1]; 					// CWD <SP> <pathname> <CRLF>
					output.println(dataTCP);
					String directory = input.readLine();

					if (!directory.isEmpty()) {
						currentDirectory = directory;
					} else {
						System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
					}
				}
				else if (data.startsWith("get path")) {
					// String[] command = data.split(" ");
					dataTCP = "PWD"; 										// PWD <CRLF>
					output.println(dataTCP);
				} 
				else if (data.startsWith("remove")) {
					String[] command = data.split(" ");
					dataTCP = "RMD" + command[1];							// RMD <SP> <pathname> <CRLF>
					output.println(dataTCP);
				} 
				else if (data.startsWith("list")) {
					String[] command = data.split(" ");
					dataTCP = "LIST";
					//dataTCP = "LIST" + " " + command[1]; 					// LIST [<SP> <pathname>] <CRLF>
					System.out.println(dataTCP);
					output.println(dataTCP);
					receiveListFiles(input);
				} 
				else if (data.startsWith("delete")) {
					String[] command = data.split(" ");
					String pathDirectory = command[1];
					dataTCP = "DELE" + " " + pathDirectory; 				// DELE <SP> <pathname> <CRLF>
					System.out.println(dataTCP);
					output.println(dataTCP);
				} 
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					dataTCP = "RNFR" + " " + command[1] + " " + command[2];	// RNFR <SP> <pathname> <CRLF>
					output.println(dataTCP);
					System.out.println(dataTCP);
				} 
				else if (data.startsWith("user")) {
					String[] command = data.split(" ");
					String user = command[1];
					dataTCP = "USER" + " " + user; 							// USER <SP> <username> <CRLF>
					output.println(dataTCP);
					// System.out.println(dataTCP);
				} 
				else if (data.startsWith("password")) {
					String[] command = data.split(" ");
					String password = command[1];
					dataTCP = "PASS" + " " + password; 						// PASS <SP> <password> <CRLF>
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

/*	
	public static boolean sendFile(String filename) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			return false;
		}
		
		try {
			int filePort = 20;
			String result;
	
			ServerSocket sServ = new ServerSocket(filePort);
			System.out.println("Client waiting for response before sending");
			
			Socket sCon = sServ.accept();
			System.out.println("File transfer Connection accepted");

			BufferedReader resInput = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			ObjectOutputStream fileOutput = new ObjectOutputStream(sCon.getOutputStream());
			
			//FileInputStream file = new FileInputStream(data);
			
			fileOutput.writeObject(fileData);
			result = resInput.readLine();
			System.out.println(result);

			System.out.println("Finished transferring file info");

			FileInputStream original = new FileInputStream(filename);
			BufferedInputStream originalBuffer = new BufferedInputStream(original);
			
			BufferedOutputStream copyBuffer = new BufferedOutputStream(sCon.getOutputStream());
			
			// Loop to read a file and write in another
			byte [] array = new byte[1000];
			int n_bytes = originalBuffer.read(array);
			while (n_bytes > 0)
			{
				copyBuffer.write(array,0,n_bytes);
				n_bytes=originalBuffer.read(array);
			}

			// Close the files
			originalBuffer.close();
			copyBuffer.close();

			sCon.close();
			sServ.close();
			System.out.println("File transfer Server closed");
			return true;
		}
		catch (Exception e) {
			System.out.println("Error writing byte to text :" + e);
		}
		return false;
	}



	public static boolean receiveFile(String filename){
		File fileData = null;
		try {
			int filePort = 20;
			Socket connection = new Socket("localhost", filePort);

			fileData = new File(filename);
			System.out.println(fileData.toURI());
			//resOutput.println("ok");
			if (!fileData.createNewFile()){
				String msg = "ERROR: A file named "+fileData.getName()+" already exists on the server.\n";
				System.out.println(msg);
				//resOutput.println(msg);
				connection.close();
				return false;
			}

			BufferedInputStream originalBuffer = new BufferedInputStream(connection.getInputStream());
			
			FileOutputStream  copy = new FileOutputStream (fileData);
			BufferedOutputStream copyBuffer = new BufferedOutputStream(copy);
			
			// Loop to read a file and write in another
			byte [] array = new byte[1000];
			int n_bytes = originalBuffer.read(array);

			while (n_bytes > 0)
			{
				copyBuffer.write(array,0,n_bytes);
				n_bytes=originalBuffer.read(array);
			}

			// Close the files
			originalBuffer.close();
			copyBuffer.close();

			connection.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error receiving file :" + e);
		}
		return false;
	}
*/
}

