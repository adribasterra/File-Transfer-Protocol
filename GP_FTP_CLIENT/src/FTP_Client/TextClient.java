package FTP_Client;
// Example: Client that receives and sends characters
// CharacterClient.java

import java.net.*;
import java.io.*;

public class TextClient {

	public static void testClient() {

		try {

			int port = 21;

			// Datos que mandaremos y recuperaremos
			String data = "";
			//String result = "";

			// Conectar con el servidor
			Socket connection = new Socket("localhost", port);

			// Recuperar input / output de la conexi�n
			BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter output = new PrintWriter(connection.getOutputStream(), true);

			while(data.compareTo("END") != 0) {

				// Input para leer desde teclado
				BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Write text (END to close the server): ");
				data = inputKeyboard.readLine();


				if (data.startsWith("send")) {
					String filename = data.substring(5).trim();
					output.println(data);
					System.out.println("Attempting to send file: " + filename);
					sendFile(filename);
				}
				else if (data.startsWith("get")) {
					String filename = data.substring(4).trim();
					output.println(data);
					System.out.println("Attempting to get file: " + filename);
					receiveFile(filename);
				}
				else if (data.startsWith("list")) {
					output.println(data);
					receiveListFiles(input);
				}
				else if (data.startsWith("delete")) {
					output.println(data);
					System.out.println(data);
				}
				else if (data.startsWith("rename")) {
					output.println(data);
					System.out.println(data);
				}
				else{
					System.out.println("Error: Command unrecognised");
				}
				//result = input.readLine();
				// Mandar datos al servidor
				//output.println(data);
				// Leer datos del servidor
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
	
	public static boolean sendFile(String filename) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			return false;
		}
		try {
			int filePort = 20;
			//String result;
	
			ServerSocket sServ = new ServerSocket(filePort);
			System.out.println("Client waiting for response before sending");
			
			Socket sCon = sServ.accept();
			System.out.println("File transfer Connection accepted");
/*
			BufferedReader resInput = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
			ObjectOutputStream fileOutput = new ObjectOutputStream(sCon.getOutputStream());
			
			//FileInputStream file = new FileInputStream(data);
			
			fileOutput.writeObject(fileData);
			result = resInput.readLine();
			System.out.println(result);

			System.out.println("Finished transferring file info");
*/
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

	public static Boolean receiveListFiles(BufferedReader input) {
		try {
			System.out.println("Here is the list of files on the server:");

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



	public static Boolean receiveFile(String filename){
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
}

