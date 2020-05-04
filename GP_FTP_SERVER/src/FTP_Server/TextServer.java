package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.
// CharacterServer.java

import java.net.*;
import java.io.*;

public class TextServer {

	public static void testServer() {

		try {
			
			int port = 1400;

			// Datos que recibiremos / mandaremos
			String data = "";

			// Crear el socket del servidor
			ServerSocket sServ = new ServerSocket(port);
			System.out.println("Character Server waiting for requests");
			
			// Aceptar conexi�n con cliente
			Socket sCon = sServ.accept();
			System.out.println("Connection accepted");

			while(data.compareTo("END") != 0) {


				// Coger input / output de la conexi�n
				BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);

				// Leer datos del cliente
				data =  input.readLine();
				System.out.println("Attempting to receive file: " + data);
				
				// Pasar a may�sculas
				//data = data.toUpperCase();
				
				// Enviar el texto en may�sculas
				//output.println(data);
				receiveFile();
			}
			
			// Cerrar la conexi�n
			
			sCon.close();

			// Cerrar el servidor
			sServ.close();
			System.out.println("Server closed");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 



	public static Boolean receiveFile(){
	File fileData = null;
	try {
		int filePort = 13;
		Socket connection = new Socket("localhost", filePort);

		ObjectInputStream fileInput = new ObjectInputStream(connection.getInputStream());
		PrintWriter resOutput = new PrintWriter(connection.getOutputStream(), true);

		fileData = (File) fileInput.readObject();
		System.out.println(fileData.toURI());
		resOutput.println("ok");
		if (!fileData.createNewFile()){
			String msg = "ERROR: A file named "+fileData.getName()+" already exists on the server.\n";
			System.out.println(msg);
			resOutput.println(msg);
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

