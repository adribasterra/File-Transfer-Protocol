package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.

// CharacterServer.java

import java.net.*;
import java.util.Scanner;
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

			while (data.compareTo("END") != 0) {

				// Coger input / output de la conexi�n
				BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
				PrintWriter output = new PrintWriter(sCon.getOutputStream(), true);

				// Leer datos del cliente
				data = input.readLine();
				System.out.println(data);
				if (data.startsWith("send")) {
					String filename = data.substring(5).trim();
					//output.println("Attempting to receive file: " + filename);
					receiveFile(filename);
				}
				else if (data.startsWith("get")) {
					String filename = data.substring(4).trim();
					//output.println("Attempting to receive file: " + filename);
					sendFile(filename);
				}
				else if (data.startsWith("list")) {
					System.out.println("Attempting to list files.");
					listFiles(output);
				}
				else if (data.startsWith("delete")) {
					String filename = data.substring(7).trim();
					//output.println("Attempting to receive file: " + filename);
					deleteFile(filename);
				}
				else if (data.startsWith("rename")) {
					String[] command = data.split(" ");
					String oldFilename = command[1];
					String newFilename = command[2];
					//output.println("Attempting to receive file: " + filename);
					renameFile(oldFilename, newFilename);
				}
				else{
					//output.println("Error: Command unrecognised");
				}
				

				// Pasar a may�sculas
				// data = data.toUpperCase();

				// Enviar el texto en may�sculas
				// output.println(data);
			}

			// Cerrar la conexi�n

			sCon.close();

			// Cerrar el servidor
			sServ.close();
			System.out.println("Server closed");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Boolean addFilenameToList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()){
				sb.append(in.nextLine());
				sb.append(System.lineSeparator());
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.println(sb.toString()+filename);
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Boolean removeFilenameFromList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			String s;
			while (in.hasNextLine()){
				s = in.nextLine();
				if (!s.contains(filename)){
					sb.append(s);
					sb.append(System.lineSeparator());
				}
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.print(sb.toString());
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public static Boolean deleteFile(String filename) {
		try {
			File fileData = new File(filename);
			if (!fileData.exists()){
				System.out.println("ERROR: File "+filename+" does not exist here!");
				return false;
			}
			Boolean success = fileData.delete();
			if (success) removeFilenameFromList(filename);
			return success;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean renameFile(String oldFilename, String newFilename) {
		try {
			File oldFile = new File(oldFilename);
			File newFile = new File(newFilename);
			if (!oldFile.exists()){
				System.out.println("ERROR: File "+oldFilename+" does not exist here!");
				return false;
			}
			if (newFile.exists()){
				System.out.println("ERROR: File "+newFilename+" already exists here!");
				return false;
			}
			Boolean success = oldFile.renameTo(newFile);
			if (success) {
				removeFilenameFromList(oldFilename);
				addFilenameToList(newFilename);
			}
			return success;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean listFiles(PrintWriter output) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			String s = null;
			while (in.hasNextLine()) {
				s = in.nextLine();
				output.println(s);
			}
			in.close();
			if (s==null) output.println("(empty)");
			output.println("END");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}



	public static Boolean receiveFile(String filename){
		File fileData = null;
		try {
			int filePort = 13;
			Socket connection = new Socket("localhost", filePort);

			// ObjectInputStream fileInput = new ObjectInputStream(connection.getInputStream());
			// PrintWriter resOutput = new PrintWriter(connection.getOutputStream(), true);

			//fileData = (File) fileInput.readObject();
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
			return true;
		} catch (Exception e) {
			System.out.println("Error receiving file :" + e);
		}
		return false;
	}



	public static boolean sendFile(String filename) {
		File fileData = new File(filename);
		if (!fileData.exists()){
			System.out.println("ERROR: File "+filename+" does not exist here!");
			return false;
		}
		try {
			int filePort = 16;
			//String result;
	
			ServerSocket sServ = new ServerSocket(filePort);
			System.out.println("Server waiting for response before sending");
			
			Socket sCon = sServ.accept();
			System.out.println("File transfer Connection accepted");

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
}

