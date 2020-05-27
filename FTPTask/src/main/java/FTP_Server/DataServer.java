package FTP_Server;
// Server that receives and sends bytes.
// ByteServer

import java.net.*;
import java.io.*;

public class DataServer {

		public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";
	
		public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";
		
		public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";
		
		public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";
		
		public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";
		
	private static int dataPort = 20;
		
	public static boolean receiveFile(String filename){
		File fileData = null;
		try {
			//int filePort = 20;
			System.out.println(CMD_FILE_STATUS_OKAY);
			Socket connection = new Socket("localhost", dataPort);

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
			

			BufferedInputStream receiveBuffer = new BufferedInputStream(connection.getInputStream());
			
			FileOutputStream  file = new FileOutputStream (fileData);
			BufferedOutputStream fileBuffer = new BufferedOutputStream(file);
			
			// Loop to read a file and write in another
			byte [] array = new byte[1000];
			int n_bytes = receiveBuffer.read(array);

			while (n_bytes > 0)
			{
				fileBuffer.write(array,0,n_bytes);
				n_bytes=receiveBuffer.read(array);
			}

			// Close the files
			receiveBuffer.close();
			fileBuffer.close();
			
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
			//int filePort = 20;
			//String result;
	
			ServerSocket sServ = new ServerSocket(dataPort);
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
} 