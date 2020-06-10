package FTP_Server;
// Server that receives and sends bytes.
// ByteServer

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class DataServer {

		public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";
	
		public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";
		
		public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";
		
		public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";
		
		public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";
		
		public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";

		public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";


	public static boolean receiveFile(String filename, int dataPort, PrintWriter output){
		try {
			ServerSocket sServ = new ServerSocket(dataPort);
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

	public static boolean sendFile(String filename, int dataPort, PrintWriter output) {
		try {
			ServerSocket sServ = new ServerSocket(dataPort);
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

	public static Boolean listFiles(int dataPort, String path) {
		System.out.println("listFiles in DataServer called");
		try {
			ServerSocket sServ = new ServerSocket(dataPort);
			System.out.println("Server waiting for response before sending");
			
			Socket sCon = sServ.accept();

			PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);
			
			path = path.replace('/', '\\');

			System.out.println(path);

			Scanner input = new Scanner(new FileReader("fileList.txt"));
			// new output in the data connection

			String line = null;
			while (input.hasNextLine()) {
				line = input.nextLine();
				if(line.startsWith(path)) { output.println(line); }
			}
			input.close();
			if (line == null) System.out.println("Is empty");
			output.println("END");
			output.println(CMD_SUCCESS);
			output.close();
			sCon.close();
			System.out.println(CMD_SUCCESS);
			sServ.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//output.println(CMD_ACTION_ABORTED);
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}
} 