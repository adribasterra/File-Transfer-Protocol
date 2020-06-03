package FTP_Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

// Example: Client that receives and sends bytes
// ByteClient

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataClient {
	
	private static int dataPort = 20;
	
	public static boolean sendFile(final String filename) {
		final File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			return false;
		}
		System.out.println(fileData.toURI());
		
		try {
			final ServerSocket sServ = new ServerSocket(dataPort);
			System.out.println("Client waiting for response before sending");
			
			final Socket sCon = sServ.accept();
			System.out.println("File transfer Connection accepted");

			final FileInputStream file = new FileInputStream(filename);
			final BufferedInputStream fileBuffer = new BufferedInputStream(file);
			
			final BufferedOutputStream sendBuffer = new BufferedOutputStream(sCon.getOutputStream());
			
			// Loop to read a file and write in another
			final byte [] array = new byte[1000];
			int n_bytes = fileBuffer.read(array);
			while (n_bytes > 0)
			{
				sendBuffer.write(array,0,n_bytes);
				n_bytes=fileBuffer.read(array);
			}

			// Close the files
			fileBuffer.close();
			sendBuffer.close();

			sCon.close();
			sServ.close();
			System.out.println("File transfer Server closed");
			return true;
		}
		catch (final Exception e) {
			System.out.println("Error writing byte to text :" + e);
		}
		return false;
	}
	
	public static boolean receiveFile(final String filename){
		File fileData = null;
		try {
			final Socket connection = new Socket("localhost", dataPort);

			fileData = new File(filename);
			System.out.println(fileData.toURI());
			//resOutput.println("ok");
			if (!fileData.createNewFile()){
				final String msg = "ERROR: A file named "+fileData.getName()+" already exists on the server.\n";
				System.out.println(msg);
				//resOutput.println(msg);
				connection.close();
				return false;
			}

			final BufferedInputStream originalBuffer = new BufferedInputStream(connection.getInputStream());
			
			final FileOutputStream  copy = new FileOutputStream (fileData);
			final BufferedOutputStream copyBuffer = new BufferedOutputStream(copy);
			
			// Loop to read a file and write in another
			final byte [] array = new byte[1000];
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
		} catch (final Exception e) {
			System.out.println("Error receiving file :" + e);
		}
		return false;
	}
}

