package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.

// CharacterServer.java

import java.net.*;
import java.util.Scanner;

import org.graalvm.compiler.nodes.calc.IntegerTestNode;

import java.io.*;

public class TextServer {

		public static final String CMD_SERVICE_READY = "220. Service ready for new user.";

		public static final String CMD_OKAY = "200. Okay.";

		public static final String CMD_COMPLETED = "250. Requested file action okay, completed.";

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

		public static final String CMD_FURTHER_INFO = "350. Requested file action pending further information.";

		public static final String CMD_CLOSING = "221. Service closing control connection.";

		public final static String CMD_USER_OKAY = "331. User name okay, need password.";

		public final static String CMD_USER_ERROR = "530. User not logged, error";

		public final static String CMD_USER_LOGGED = "230. User logged in, proceed";
		
		public final static String CMD_GET_DIRECTORY = "257. "; //+ current path directory
		
		public final static String CMD_PASSIVE_MODE = "227. Entering Passive Mode "; //+ (h1,h2,h3,h4,p1,p2)
		

	private static int controlPort = 21;
	private static final String user = "user";
	private static final String password = "password";

	public static PrintWriter output;
	private static boolean hasPort = false;
	private static int dataPortClient;
	private static String currentDirectory = "files\\";

	public static void testServer() {

		try {

			//int port = 21;

			// Sending/receiving data
			String data = "";

			// Create server socket
			ServerSocket sServ = new ServerSocket(controlPort);
			System.out.println("Character Server waiting for requests");

			// Accept connection with client
			Socket sCon = sServ.accept();
			boolean connectionClosed = false;
			//System.out.println("Connection accepted");
			System.out.println(CMD_SERVICE_READY);

			// Take input/output from connection
			BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			output = new PrintWriter(sCon.getOutputStream(), true);

            output.println(CMD_SERVICE_READY);
			Boolean loggedIn = false;
			while (!loggedIn) loggedIn = logIn(input, output);

			//ShowGuideline();

			// Read data from client
			//data = input.readLine();

			while (data.compareTo("END") != 0) {

				// Read data from client
				data = input.readLine();

				System.out.println(data);

				if (data.startsWith("STOR")) {
					String[] command = data.split(" ");
					String filename = command[1];
					//output.println("Attempting to receive file: " + filename);
					boolean response = DataServer.receiveFile(filename, dataPortClient);
					if(hasPort && response) {
						addFilenameToList(filename);
					}
					else{
						System.out.println("Missing PORT command");
						output.println(CMD_CANT_OPEN_CONNECTION);
					}
					//receiveFile(filename);
				}
				else if (data.startsWith("PRT")) {
					//Structure of type: h1,h2,h3,h4,p1,p2
					String[] command = data.split(" ");
					if(command.length == 2){
						dataPortClient = Integer.parseInt(command[1]);
						hasPort = true;
					}
					else{
						output.print(CMD_BAD_SEQUENCE);
						System.out.println("Bad structure of PORT");
					}
				}
				else if (data.startsWith("RETR")) {
					String[] command = data.split(" ");
					String filename = command[1];
					//output.println("Attempting to receive file: " + filename);
					if(hasPort){
						DataServer.sendFile(filename, dataPortClient);
					}
					else{
						System.out.println("Missing PORT");
						output.println(CMD_CANT_OPEN_CONNECTION);
					}
					//sendFile(filename);
				}
				else if (data.startsWith("MKD")) { 
					String[] command = data.split(" ");
					String fileDir = canonicalDir(currentDirectory, command[1]);
					if(new File(fileDir).mkdir()) {
						System.out.println(CMD_GET_DIRECTORY + fileDir + " directory created.");
						output.println(CMD_GET_DIRECTORY + fileDir + " directory created.");
					}
					else { System.out.println(CMD_FILE_UNAVAILABLE); output.println(CMD_FILE_UNAVAILABLE); }
				}
				else if (data.startsWith("CWD")) {
					String[] command = data.split(" ");
					String directory = canonicalDir(currentDirectory, command[1]);
					output.println(directory);
					
					if ( !directory.isEmpty() && new File(directory).isDirectory() ) {
						currentDirectory = directory;
						System.out.println(CMD_COMPLETED);
						output.println(CMD_COMPLETED);
					}else if ( directory.isEmpty() ) {
						System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
					}else{
						System.out.println(CMD_FILE_UNAVAILABLE);
						output.println(CMD_FILE_UNAVAILABLE);
						System.out.println("ERROR: Directory : "+directory+" does not exist!");
					}
				}
				else if(data.startsWith("PWD")) {
					output.println(currentDirectory);
					System.out.println(CMD_GET_DIRECTORY + currentDirectory);
					output.println(CMD_GET_DIRECTORY + currentDirectory);
				}
				
				else if(data.startsWith("RMD")) {
					//Remove directory
					String[] command = data.split(" ");
					String directory = canonicalDir(currentDirectory, command[1]);
					
					/* if(madeIt) { System.out.println(CMD_COMPLETED); }
					 * else { System.out.println(CMD_FILE_UNAVAILABLE); }
					 */
				}
				
				else if (data.startsWith("LIST")) {
					System.out.println("Attempting to list files.");
					listFiles(output);
				}
				else if (data.startsWith("DELE")) {
					String[] command = data.split(" ");
					String filename = currentDirectory + command[1];
					//output.println("Attempting to receive file: " + filename);
					deleteFile(filename);
				}
				else if (data.startsWith("RNFR")) {
					String[] command = data.split(" ");
					if(command.length == 3){
						String oldFilename = currentDirectory + command[1];
						String newFilename = currentDirectory + command[2];
						System.out.println(oldFilename + " " + newFilename);
						if(newFilename != currentDirectory) {
							renameFile(oldFilename, newFilename);
						}
						else System.out.println("Names are the same!");
					}
					else {
						//output.println("Attempting to receive file: " + filename);
						System.out.println(CMD_FURTHER_INFO);
						output.println(CMD_FURTHER_INFO);
					}
				}
				else if(data.startsWith("USER")) {
					String userData = data.substring(5).trim();
					if(userData.compareTo(user)==0) {
						System.out.println(CMD_USER_OKAY);
						output.println(CMD_USER_OKAY);
					}
				}
				else if(data.startsWith("PASS")) {
					String passwordData = data.substring(5).trim();
					if(passwordData.compareTo(password)==0) {
						System.out.println(CMD_USER_LOGGED);
						output.println(CMD_USER_LOGGED);
					}
					else {
						System.out.println(CMD_USER_ERROR);
						output.println(CMD_USER_ERROR);
					}
				}
				else if(data.startsWith("QUIT")){
					sCon.close();
					System.out.println(CMD_CLOSING);
					output.println(CMD_CLOSING);
					connectionClosed = true;
				}
				else{
					//output.println("Error: Command unrecognised");
					System.out.println(CMD_BAD_SEQUENCE);
					output.println(CMD_BAD_SEQUENCE);
				}
			}

			// Close connection
			if(!connectionClosed){
				sCon.close();
				System.out.println(CMD_CLOSING);
				output.println(CMD_CLOSING);
			}

			hasPort = false;
			// Close server socket
			sServ.close();
			//System.out.println("Server closed");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
	}

	public static boolean logIn(BufferedReader input, PrintWriter output){
		try {
			String userData = input.readLine();
			if(userData.compareTo(user)==0) {
				System.out.println(CMD_USER_OKAY);
				output.println(CMD_USER_OKAY);
				output.println("User OK");
			}else{
				output.println("User WRONG");
				return false;
			}

			String passwordData = input.readLine();
			if(passwordData.compareTo(password)==0) {
				System.out.println(CMD_USER_LOGGED);
				output.println(CMD_USER_LOGGED);
				output.println("Password OK");
			}else{
				System.out.println(CMD_USER_ERROR);
				output.println(CMD_USER_ERROR);
				output.println("Password WRONG");
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return false;
	}
	
	public static String canonicalDir(String curDir, String directory) {
		String[] paths;
		if (directory.contains("/")) {
			paths = directory.split("/");
		} else {
			paths = new String[]{directory};
		}
		directory = curDir;
		for (String path : paths){
			if (path.compareTo("..")==0){
				int i = directory.lastIndexOf("\\", directory.length()-2);
				if (i==-1) return "";
				directory = directory.substring(0, i+1);
			}
			else if (path.compareTo(".")!=0){
				directory = directory + path + "\\";
			}
		}
		return directory;
	}
	
	public static boolean addFilenameToList(String filename) {
		System.out.println("addFilenameToList called");
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()){
				sb.append(in.nextLine());
				sb.append(System.lineSeparator());
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.println(sb.toString()+filename.substring(6));
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	private static Boolean removeFilenameFromList(String filename) {
		System.out.println("removeFilenameFromList called");
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			String s;
			Boolean isInList = false;
			while (in.hasNextLine()){
				s = in.nextLine();
				if (!s.contains(filename.substring(6))){
					sb.append(s);
					sb.append(System.lineSeparator());
				}else{
					isInList = true;
				}
			}
			in.close();
			if (!isInList) return false;

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.print(sb.toString());
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean deleteFile(String filename) {
		System.out.println("deleteFile called");
		try {
			File fileData = new File(filename);
			if (!fileData.exists() || !removeFilenameFromList(filename)){
				//System.out.println("ERROR: File "+filename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				output.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			return fileData.delete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean renameFile(String oldFilename, String newFilename) {
		System.out.println("renameFile called");
		try {
			File oldFile = new File(oldFilename);
			File newFile = new File(newFilename);
			if (!oldFile.exists() || !removeFilenameFromList(oldFilename)){
				//System.out.println("ERROR: File "+oldFilename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				output.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			if (newFile.exists()){
				//System.out.println("ERROR: File "+newFilename+" already exists here!");
				System.out.println(CMD_FILENAME_NOT_ALLOWED);
				output.println(CMD_FILENAME_NOT_ALLOWED);
				return false;
			}
			Boolean success = oldFile.renameTo(newFile);
			if (success) {;
				addFilenameToList(newFilename);
				System.out.println(CMD_COMPLETED);
				output.println(CMD_COMPLETED);
			}
			return success;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean listFiles(PrintWriter output) {
		System.out.println("listFiles called");
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			String s = null;
			while (in.hasNextLine()) {
				s = in.nextLine();
				output.println(s);
			}
			in.close();
			if (s==null) System.out.println("Is empty");
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return false;
	}


        
          //INTERFACE
        
        public static String listFiles() {
		try {
                    
                String path = "fileList.txt";
                System.out.println(new File(path).getAbsolutePath());
				Scanner in = new Scanner(new FileReader("fileList.txt"));
				String s = null;
				StringBuilder sb = new StringBuilder();
				while (in.hasNextLine()) {
					s = in.nextLine();
					sb.append(s);
				}
				in.close();
				return sb.toString();

			} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
			output.println(CMD_ACTION_ABORTED);
		}
		return null;
                
	}

	public static void ShowGuideline() {
		output.println("Possible actions:\n");
		output.println("\t1. List files in directory. \t\tCOMMAND: 'list + path'");
		output.println("\t2. Download file. \t\t\tCOMMAND: 'get + file'");
		output.println("\t3. Upload file. \t\t\tCOMMAND: 'send + file'");
		output.println("\t4. Close connection. \t\t\tCOMMAND: 'quit'");
		output.println("\t5. Delete file. \t\t\tCOMMAND: 'delete + path'\n");
		output.println("\t6. Get path of working directory. \tCOMMAND: 'get path'");
		output.println("\t7. Change current directory. \t\tCOMMAND: 'cd + path'");
		output.println("\t8. Create directory. \t\t\tCOMMAND: 'mkdir + path'");
		output.println("\t9. Remove directory. \t\t\tCOMMAND: 'remove + path'");
		output.println("\t10. Rename file or directory. \t\tCOMMAND: 'rename + path'\n");
		output.println("\t11. Indicate port for file transfer.  \t\tCOMMAND: 'prt + number'\n");
		output.println("'Quit' or 'END' for finishing connection.\n");
	}

/*
	public static boolean receiveFile(String filename){
		File fileData = null;
		try {
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
		return false;
	}
	public static boolean sendFile(String filename) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
			return false;
		}
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
		return false;
	}
*/
}
