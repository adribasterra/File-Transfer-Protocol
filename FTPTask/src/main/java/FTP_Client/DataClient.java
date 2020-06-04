package FTP_Client;

import java.net.*;
import java.io.*;

// Example: Client that receives and sends bytes
// ByteClient

public class DataClient {
	
	public static PrintWriter output;
	
	public static boolean sendFile(String filename, int dataPortClient) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			return false;
		}
		//System.out.println(fileData.toURI());
		
		try {
			Socket sCon = new Socket("localhost", dataPortClient);
			System.out.println("sCon opened");

			InputStream inputStream = new FileInputStream(filename);
			OutputStream outputStream = sCon.getOutputStream();

			int count;
			byte[] bytes = new byte[4096];
			while((count = inputStream.read(bytes)) > 0){
				outputStream.write(bytes, 0, count);
			}
			
			outputStream.close();
			inputStream.close();
			sCon.close();
			return true;
		}
		catch (Exception e) {
			System.out.println("Error writing byte to text :" + e);
		}
		return false;
	}
	
	public static  boolean receiveFile(String filename, int dataPortClient){
		System.out.println("He entrado");
		File fileData = null;
		try {
			System.out.println("dataPort in DataClient: " + dataPortClient);

			Socket connection = new Socket("localhost", dataPortClient);

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

			System.out.println("Hola");

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
}

