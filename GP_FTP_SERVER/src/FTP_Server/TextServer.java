package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.

// CharacterServer.java

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TextServer {
	
		public static final String CMD_SERVICE_READY = "220. Service ready for new user.";
		
		public static final String CMD_OKAY = "200. Okay";
		
		public static final String CMD_BAD_SEQUENCE = "503. Bad sequence of commands.";
		
		public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";

		public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";
		
		public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";
		
		public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";
		
		public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";
		
		public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";
		
		public static final String CMD_TRANSFER_ABORTER = "426. Connection closed; transfer aborted.";
		
		public static final String CMD_INSUFFICIENT_STORAGE = "452. Requested action not taken. Insufficient storage space in system.";
		
		public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";
		
		public static final String CMD_CLOSING = "221. Service closing control connection.";

	private static int commandPort = 21;

	public static void testServer() {

		try {

			//int port = 21;

			// Sending/receiving data
			String data = "";

			// Create server socket
			ServerSocket sServ = new ServerSocket(commandPort);
			System.out.println("Character Server waiting for requests");

			// Accept connection with client
			Socket sCon = sServ.accept();
			//System.out.println("Connection accepted");
			System.out.println(CMD_SERVICE_READY);

			while (data.compareTo("END") != 0) {

				// Take input/output from connection
				BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);

				// Read data from client
				data = input.readLine();
				System.out.println(data);
				if (data.startsWith("send")) {
					String filename = data.substring(5).trim();
					//output.println("Attempting to receive file: " + filename);
					receiveFile(filename);
				}
				else if (data.startsWith("get")) {
					String filename = data.substring(4).trim();
					//output.println("Attempting to receive file: " + filename);
					sendFile(filename);
				}
				else if (data.startsWith("list")) {
					System.out.println("Attempting to list files.");
					listFiles(output);
				}
				else if (data.startsWith("delete")) {
					String filename = data.substring(7).trim();
					//output.println("Attempting to receive file: " + filename);
					deleteFile(filename);
				}
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					String oldFilename = command[1];
					String newFilename = command[2];
					//output.println("Attempting to receive file: " + filename);
					renameFile(oldFilename, newFilename);
				}
				else{
					//output.println("Error: Command unrecognised");
					output.println(CMD_BAD_SEQUENCE);
				}

				// UpperCase
				// data = data.toUpperCase();

				// Send data with UpperCase
				// output.println(data);
			}

			// Close connection
			sCon.close();
			System.out.println(CMD_CLOSING);

			// Close server socket
			sServ.close();
			//System.out.println("Server closed");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
	}

	public static boolean addFilenameToList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()){
				sb.append(in.nextLine());
				sb.append(System.lineSeparator());
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.println(sb.toString()+filename);
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	private static Boolean removeFilenameFromList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			String s;
			while (in.hasNextLine()){
				s = in.nextLine();
				if (!s.contains(filename)){
					sb.append(s);
					sb.append(System.lineSeparator());
				}
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.print(sb.toString());
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}


	public static Boolean deleteFile(String filename) {
		try {
			File fileData = new File(filename);
			if (!fileData.exists()){
				//System.out.println("ERROR: File "+filename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			Boolean success = fileData.delete();
			if (success) removeFilenameFromList(filename);
			return success;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean renameFile(String oldFilename, String newFilename) {
		try {
			File oldFile = new File(oldFilename);
			File newFile = new File(newFilename);
			if (!oldFile.exists()){
				//System.out.println("ERROR: File "+oldFilename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			if (newFile.exists()){
				//System.out.println("ERROR: File "+newFilename+" already exists here!");
				System.out.println(CMD_FILENAME_NOT_ALLOWED);
				return false;
			}
			Boolean success = oldFile.renameTo(newFile);
			if (success) {
				removeFilenameFromList(oldFilename);
				addFilenameToList(newFilename);
			}
			return success;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean listFiles(PrintWriter output) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			String s = null;
			while (in.hasNextLine()) {
				s = in.nextLine();
				output.println(s);
			}
			in.close();
			if (s==null) output.println("(empty)");
			output.println("END");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}



	public static boolean receiveFile(String filename){
		//File fileData = null;
		if(DataServer.receive(filename)) {
			addFilenameToList(filename);
			return true;
		}
		/*try {
			int filePort = 20;
			System.out.println(CMD_FILE_STATUS_OKAY);
			Socket connection = new Socket("localhost", filePort);

			// ObjectInputStream fileInput = new ObjectInputStream(connection.getInputStream());
			// PrintWriter resOutput = new PrintWriter(connection.getOutputStream(), true);

			//fileData = (File) fileInput.readObject();
			fileData = new File(filename);
			System.out.println(fileData.toURI());
			//resOutput.println("ok");
			if (!fileData.createNewFile()){
				//String msg = "ERROR: A file named "+fileData.getName()+" already exists on the server.\n";
				System.out.println(CMD_FILENAME_NOT_ALLOWED);
				//System.out.println(msg);
				//resOutput.println(msg);
				connection.close();
				System.out.println(CMD_SUCCESS);
				return false;
			}
			
			System.out.println(CMD_FILE_STATUS_OKAY);
			addFilenameToList(fileData.getName());
			

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
			System.out.println(CMD_SUCCESS);
			
			return true;
		} catch (Exception e) {
			//System.out.println("Error receiving file :" + e);
			System.out.println(CMD_CANT_OPEN_CONNECTION);
		}
		*/
		return false;
	}

	public static boolean sendFile(String filename) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
			return false;
		}
		if(DataServer.sendFile(filename)) {
			return true;
		}
		/*
		try {
			int filePort = 20;
			//String result;
	
			ServerSocket sServ = new ServerSocket(filePort);
			System.out.println("Server waiting for response before sending");
			
			System.out.println(CMD_FILE_STATUS_OKAY);
			Socket sCon = sServ.accept();
			//System.out.println("File transfer Connection accepted");

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
			System.out.println(CMD_SUCCESS);
			sServ.close();
			//System.out.println("File transfer Server closed");
			System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
			return true;
		}
		catch (Exception e) {
			//System.out.println("Error writing byte to text :" + e);
			System.out.println(CMD_CANT_OPEN_CONNECTION);
		}
		*/
		return false;
	}
}

