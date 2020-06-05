/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP_Server;

import static FTP_Client.TextClient.logIn;
import static FTP_Server.TextServer.CMD_ACTION_ABORTED;
import static FTP_Server.TextServer.CMD_COMPLETED;
import static FTP_Server.TextServer.CMD_FILENAME_NOT_ALLOWED;
import static FTP_Server.TextServer.CMD_FILE_ACTION_UNAVAILABLE;
import static FTP_Server.TextServer.CMD_USER_ERROR;
import static FTP_Server.TextServer.CMD_USER_LOGGED;
import static FTP_Server.TextServer.CMD_USER_OKAY;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


//WAIT ! THIS CLASS MIGHT NOT FOR THE PROJECT IS JUST FOR TEST PUPOSEES, BY JUAN


/**
 *
 * @author jojoj
 */
public class ServerThread extends Thread {
    
    public static final String CMD_SERVICE_READY = "220. Service ready for new user.";

		public static final String CMD_OKAY = "200. Okay.";

		public static final String CMD_COMPLETED = "250. Requested file action okay, completed.";

		public static final String CMD_BAD_SEQUENCE = "503. Bad sequence of commands.";

		public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";

		public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";

		public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";

		public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";

		public static final String CMD_FILE_ACTION_UNAVAILABLE= "450. Requested file action not taken. File unavailable.";

		public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";

		public static final String CMD_TRANSFER_ABORTER = "426. Connection closed; transfer aborted.";

		public static final String CMD_INSUFFICIENT_STORAGE = "452. Requested action not taken. Insufficient storage space in system.";

		public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";

		public static final String CMD_FURTHER_INFO = "350. Requested file action pending further information.";

		public static final String CMD_CLOSING = "221. Service closing control connection.";

		public final static String CMD_USER_OKAY = "331. User name okay, need password.";

		public final static String CMD_USER_ERROR = "530. User not logged, error";

		public final static String CMD_USER_LOGGED = "230. User logged in, proceed";
		
		public final static String CMD_GET_DIRECTORY = "257. "; //+ current path directory
		
		public final static String CMD_PASSIVE_MODE = "227. Entering Passive Mode "; //+ (h1,h2,h3,h4,p1,p2)
		
		

	private static int controlPort = 21;
	private static final String user = "user";
	private static final String password = "password";

    BufferedReader input;
    PrintWriter output;
    Socket socket = null;

    public ServerThread(Socket r) {
        socket = r;

        try {

            // Recover input & output from connection
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            Logger.getLogger(FTP_Interface.main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {

        String cad = "";

        while (!cad.trim().equals("*")) {

            System.out.println("conectado con: " + socket.toString());
            try {

                cad = input.readLine();
                if (cad != null) {
                    output.println(cad.trim().toUpperCase());
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(socket.toString()+"SE HA DESCONECTADO ");

        try {

            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
    public static boolean logIn(BufferedReader input, PrintWriter output){
		try {
			String userData = input.readLine();
			if(userData.compareTo(user)==0) {
				System.out.println(CMD_USER_OKAY);
				output.println("User OK");
			}else{
				output.println("User WRONG");
				return false;
			}

			String passwordData = input.readLine();
			if(passwordData.compareTo(password)==0) {
				System.out.println(CMD_USER_LOGGED);
				output.println("Password OK");
			}else{
				System.out.println(CMD_USER_ERROR);
				output.println("Password WRONG");
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}
	
	public static String canonicalDir(String curDir, String directory) {
		String[] paths;
		if (directory.contains("/")) {
			paths = directory.split("/");
		} else {
			paths = new String[]{directory};
		}
		directory = curDir;
		for (String path : paths){
			if (path.compareTo("..")==0){
				int i = directory.lastIndexOf("\\", directory.length()-2);
				if (i==-1) return "";
				directory = directory.substring(0, i+1);
			}
			else if (path.compareTo(".")!=0){
				directory = directory + path + "\\";
			}
		}
		return directory;
	}
	
	public static boolean addFilenameToList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()){
				sb.append(in.nextLine());
				sb.append(System.lineSeparator());
			}
			in.close();

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.println(sb.toString()+filename.substring(6));
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	private static Boolean removeFilenameFromList(String filename) {
		try {
			Scanner in = new Scanner(new FileReader("fileList.txt"));
			StringBuilder sb = new StringBuilder();
			String s;
			Boolean isInList = false;
			while (in.hasNextLine()){
				s = in.nextLine();
				if (!s.contains(filename.substring(6))){
					sb.append(s);
					sb.append(System.lineSeparator());
				}else{
					isInList = true;
				}
			}
			in.close();
			if (!isInList) return false;

			PrintWriter listWriter = new PrintWriter(new FileOutputStream("fileList.txt"));
			listWriter.print(sb.toString());
			listWriter.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean deleteFile(String filename) {
		try {
			File fileData = new File(filename);
			if (!fileData.exists() || !removeFilenameFromList(filename)){
				//System.out.println("ERROR: File "+filename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			return fileData.delete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}

	public static Boolean renameFile(String oldFilename, String newFilename) {
		try {
			File oldFile = new File(oldFilename);
			File newFile = new File(newFilename);
			if (!oldFile.exists() || !removeFilenameFromList(oldFilename)){
				//System.out.println("ERROR: File "+oldFilename+" does not exist here!");
				System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
				return false;
			}
			if (newFile.exists()){
				//System.out.println("ERROR: File "+newFilename+" already exists here!");
				System.out.println(CMD_FILENAME_NOT_ALLOWED);
				return false;
			}
			Boolean success = oldFile.renameTo(newFile);
			if (success) {;
				addFilenameToList(newFilename);
				System.out.println(CMD_COMPLETED);
			}
			return success;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
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
			System.out.println(CMD_ACTION_ABORTED);
		}
		return false;
	}


        
          //INTERFACE
        
        public static String listFiles() {
		try {
                    
                     String path = "fileList.txt";

                     System.out.println(new File(path).getAbsolutePath());
                    
			Scanner in = new Scanner(new FileReader("fileList.txt"));

                        String s = null;
                    
                        StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()) {
				s = in.nextLine();
                                sb.append(s);
	
			}
			in.close();
                        
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return null;
                
	}

}
