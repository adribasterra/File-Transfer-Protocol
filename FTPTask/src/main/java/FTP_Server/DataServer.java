package FTP_Server;
// Server that receives and sends bytes.
// ByteServer

import java.net.*;
import java.io.*;

public class DataServer {

		public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";
	
		public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";
		
		public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";
		
		public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";
		
		public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";
		
		public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";

		public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";


	public static boolean receiveFile(String filename, int dataPortClient, PrintWriter output){
		try {
			ServerSocket sServ = new ServerSocket(dataPortClient);
			System.out.println("Server waiting for response before receiving");

			Socket sCon = sServ.accept();

			InputStream inputStream = sCon.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(filename);

			//Insufficent storage if bytes.length < file.length
			byte[] bytes = new byte[1000];
			int count;
			while((count = inputStream.read(bytes)) > 0){
				fileOutputStream.write(bytes, 0, count);
			}
			
			fileOutputStream.close();
			inputStream.close();
			sCon.close();
			sServ.close();
			output.println(CMD_SUCCESS);
			System.out.println(CMD_SUCCESS);
			
			return true;
		} catch (Exception e) {
			//System.out.println("Error receiving file :" + e);
			output.println(CMD_ACTION_ABORTED);
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static boolean sendFile(String filename, int dataPortClient, PrintWriter output) {
		try {
			ServerSocket sServ = new ServerSocket(dataPortClient);
			System.out.println("Server waiting for response before sending");
			
			Socket sCon = sServ.accept();

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
			output.println(CMD_SUCCESS);
			System.out.println(CMD_SUCCESS);
			sServ.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("Error writing byte to text :" + e);
			output.println(CMD_ACTION_ABORTED);
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}
} 