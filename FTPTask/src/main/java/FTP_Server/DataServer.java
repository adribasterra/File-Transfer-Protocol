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

	public static PrintWriter output;
	private static int dataPortServer = 20;
	
	public static boolean receiveFile(String filename, int dataPortClient){
		File fileData = new File(filename);
		if(fileData.exists()){
			System.out.println("ALREADY EXISTS IN SERVER");
			return false;
		}
		try {
			//int filePort = 20;
			//output.println(CMD_FILE_STATUS_OKAY);
			
			ServerSocket sServ = new ServerSocket(dataPortClient);
			System.out.println("Server waiting for response before receiving");

			Socket sCon = sServ.accept();

			InputStream inputStream = sCon.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(filename);

			byte[] bytes = new byte[1000];
			int count;
			while((count = inputStream.read(bytes)) > 0){
				fileOutputStream.write(bytes, 0, count);
			}
			
			fileOutputStream.close();
			inputStream.close();
			sCon.close();
			sServ.close();
			//output.println(CMD_SUCCESS);
			
			return true;
		} catch (Exception e) {
			//System.out.println("Error receiving file :" + e);
			//output.println(CMD_CANT_OPEN_CONNECTION);
		}
		return false;
	}

	public static boolean sendFile(String filename, int dataPortClient) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			//output.println(CMD_FILE_ACTION_UNAVAILABLE);
			return false;
		}
		try {
			
			System.out.println("dataPort in DataServer " + dataPortServer);
			ServerSocket sServ = new ServerSocket(dataPortClient);
			System.out.println("Server waiting for response before sending");
			
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
			output.println(CMD_SUCCESS);
			sServ.close();
			//System.out.println("File transfer Server closed");
			return true;
		}
		catch (Exception e) {
			System.out.println("Error writing byte to text :" + e);
			//output.println(CMD_CANT_OPEN_CONNECTION);
		}
		return false;
	}
} 