package FTP_Client;
// Example: Client that receives and sends characters
// CharacterClient.java

import java.net.*;
import java.io.*;

public class TextClient {
	
	private static int controlPort = 21;

	public static void testClient() {

		try {

			//int port = 21;

			// Send & recover data
			String data = "";
			//String result = "";

			// Connect with the server
			Socket connection = new Socket("localhost", controlPort);

			// Recover input & output from connection
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter output = new PrintWriter(connection.getOutputStream(), true);

			//Boolean loggegIn = false;
			//while (!loggegIn) loggegIn = logIn(input, output);

			String curDir = "files\\";
			String baseDir = "files\\";

			while(data.compareTo("END") != 0) {

				// Input for reading from keyboard
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("FTP (END to close the server): "+curDir+"> ");
				data = inputKeyboard.readLine();


				if (data.startsWith("send")) {
					String[] command = data.split(" ");
					String filename = command[1];
					String dataTCP = "STOR" + " " + filename;						//STOR <SP> <pathname> <CRLF> 
					output.println(dataTCP);
					System.out.println("Attempting to send file: " + filename);
					//sendFile(filename);
					DataClient.sendFile(baseDir + filename);
				}
				else if (data.startsWith("get")) {
					String[] command = data.split(" ");
					String filename = command[1];
					String dataTCP = "RETR" + " " + filename;						//RETR <SP> <pathname> <CRLF> 
					output.println(dataTCP);
					System.out.println("Attempting to get file: " + filename);
					//receiveFile(filename);
					DataClient.receiveFile(baseDir + filename);
				}
				else if (data.startsWith("mkdir")) {
					String[] command = data.split(" ");
					String directory = curDir + command[1];
					output.println(data);
					System.out.println("Attempting to create directory: " + directory);
					//receiveFile(filename);
					//new File(directory).mkdir();
				}
				else if (data.startsWith("cd")) {
					output.println(data);
					String directory = input.readLine();
					
					if ( !directory.isEmpty() ) {
						curDir = directory;
					}else{
						System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
					}
				}
				else if (data.startsWith("list")) {
					String[] command = data.split(" ");
					String dataTCP;
					String pathDirectory = null;
					if (command.length>1){
						pathDirectory = command[1];
						dataTCP = "LIST" + " " + pathDirectory;				//LIST [<SP> <pathname>] <CRLF>
					}else{
						dataTCP = "LIST";
					}
					System.out.println(dataTCP);
					output.println(dataTCP);
					receiveListFiles(pathDirectory, input);
				}
				else if (data.startsWith("delete")) {
					String[] command = data.split(" ");
					String pathDirectory = curDir + command[1];
					String dataTCP = "DELE" + " " + pathDirectory;				//DELE <SP> <pathname> <CRLF>
						System.out.println(dataTCP);
					output.println(dataTCP);
				}
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					String pathDirectory = curDir + command[1];
					String dataTCP = "RNFR" + " " + pathDirectory;				//RNFR <SP> <pathname> <CRLF>
					output.println(dataTCP);
						System.out.println(dataTCP);
				}
				else if(data.startsWith("user")) {
					String[] command = data.split(" ");
					String user = curDir + command[1];
					String dataTCP = "USER" + " " + user;						//USER <SP> <username> <CRLF>
					output.println(dataTCP);
						System.out.println(dataTCP);
				}
				else if(data.startsWith("password")) {
					String[] command = data.split(" ");
					String password = curDir + command[1];
					String dataTCP = "PASS" + " " + password;					//PASS <SP> <password> <CRLF>
					output.println(dataTCP);
						System.out.println(dataTCP);
				}
				else if(data.compareTo("END")==0) {
					output.println(data);
				}
				else{
					System.out.println("Error: Command unrecognised");
				}
				//result = input.readLine();
				// Send data to server
				//output.println(data);
				// Read data from server
				//result = input.readLine();
				/* if (result.compareTo("ok") !=0) {
				
				sendFile(data);

				}
				if(data.compareTo("END") !=0) {
					System.out.println("Data = " + data + " --- Result = " + result);	
				}*/
				
			}


			// Close the connection
			connection.close();
			System.out.println("Client Closed");

		}  catch(IOException e) {
			System.out.println("Error: " + e);		
		}
	}

	public static boolean receiveListFiles(String directory, BufferedReader input) {
		try {
			if(directory==null) System.out.println("Here is the list of files on the server:");
			else System.out.println("Here is the list of files in "+directory+":");

			String s = input.readLine();
			while (s.compareTo("END")!=0) {
				System.out.println(" > " + s);
				s = input.readLine();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean logIn(BufferedReader input, PrintWriter output){
		String data = null;
		try {
			BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("To use this server you must first log in.");
			System.out.print("Username: ");
			
			data = inputKeyboard.readLine();
			output.println(data);
			String result = input.readLine();
			System.out.println(result);
			if (result.compareTo("User WRONG")==0) return false;

			System.out.print("Password: ");
			data = inputKeyboard.readLine();
			output.println(data);
			result = input.readLine();
			System.out.println(result);
			if (result.compareTo("Password WRONG")==0) return false;

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error : "+e);
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

