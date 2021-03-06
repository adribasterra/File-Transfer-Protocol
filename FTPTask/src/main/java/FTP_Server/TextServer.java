package FTP_Server;
// Example: Server that receive and sends characters. It converts the text to upper case.

// CharacterServer.java
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//import org.graalvm.compiler.nodes.calc.IntegerTestNode;
import java.io.*;

public class TextServer {

    public static final String CMD_SERVICE_READY = "220. Service ready for new user.";

    public static final String CMD_OKAY = "200. Okay.";

    public static final String CMD_COMPLETED = "250. Requested file action okay, completed.";

    public static final String CMD_BAD_SEQUENCE = "503. Bad sequence of commands.";

    public static final String CMD_FILE_STATUS_OKAY = "150. File status okay; about to open data connection.";

    public static final String CMD_SUCCESS = "226. Closing data connection. Requested file action successful.";

    public static final String CMD_CANT_OPEN_CONNECTION = "425. Can't open data connection.";

    public static final String CMD_ACTION_ABORTED = "451. Requested action aborted: local error in processing.";

    public static final String CMD_FILE_ACTION_UNAVAILABLE = "450. Requested file action not taken. File unavailable.";

    public static final String CMD_FILE_UNAVAILABLE = "550. Requested action not taken. File unavailable.";

    public static final String CMD_TRANSFER_ABORTER = "426. Connection closed; transfer aborted.";

    public static final String CMD_INSUFFICIENT_STORAGE = "452. Requested action not taken. Insufficient storage space in system.";

    public static final String CMD_FILENAME_NOT_ALLOWED = "553. Requested action not taken. File name not allowed.";

    public final static String CMD_USER_OKAY = "331. User name okay, need password.";

    public final static String CMD_USER_ERROR = "530. User not logged, error";

    public final static String CMD_USER_LOGGED = "230. User logged in, proceed";

    public final static String CMD_GET_DIRECTORY = "257. "; //+ current path directory

    public final static String CMD_PASSIVE_MODE = "227. Entering Passive Mode "; //+ (h1,h2,h3,h4,p1,p2)

    public final static String CMD_CLOSING = "221. Service closing control connection.";

    private static int controlPort = 21;
    private static final String user = "user";
    private static final String password = "password";

    public static PrintWriter output;
    private static boolean hasPort = false;
    private static int dataPortClient;
    private static String currentDirectory = "src\\main\\java\\FTP_Server\\files\\";

    public static void testServer() {

        try {
            // Sending/receiving data
            String data = "";

            // Create server socket
            ServerSocket sServ = new ServerSocket(controlPort);
            System.out.println("Character Server waiting for requests");

            // Accept connection with client
            Socket sCon = sServ.accept();
            boolean connectionClosed = false;
            hasPort = false;

            // Take input/output from connection
            BufferedReader input = new BufferedReader(new InputStreamReader(sCon.getInputStream()));
            output = new PrintWriter(sCon.getOutputStream(), true);

            output.println(CMD_SERVICE_READY);
            System.out.println(CMD_SERVICE_READY);

            while (data.compareTo("END") != 0) {

                // Read data from client
                data = input.readLine();
                System.out.println(data);

                if (data.startsWith("STOR")) {
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        String filename = command[1];
                        File fileData = new File(currentDirectory + filename);
                        System.out.println(fileData);
                        if (fileData.exists()) {
                            System.out.println("ALREADY EXISTS IN SERVER");
                            output.println(CMD_FILENAME_NOT_ALLOWED);
                            System.out.println(CMD_FILENAME_NOT_ALLOWED);
                        } else {
                            output.println(CMD_FILE_STATUS_OKAY);
                            System.out.println(CMD_FILE_STATUS_OKAY);
                            if (hasPort) {
                                DataServer.receiveFile(currentDirectory + filename, dataPortClient, output);
                            } else {
                                output.println(CMD_CANT_OPEN_CONNECTION); //There is no dataPort
                                System.out.println(CMD_CANT_OPEN_CONNECTION);
                            }
                        }
                    } else {
                        output.print(CMD_BAD_SEQUENCE); //There is no path for file
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("PRT")) {
                    //Structure of type: h1,h2,h3,h4,p1,p2
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        dataPortClient = Integer.parseInt(command[1]);
                        hasPort = true;
                        output.println(CMD_OKAY);
                        System.out.println(CMD_OKAY);
                    } else {
                        output.print(CMD_BAD_SEQUENCE);
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("RETR")) {
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        String absolutePath = canonicalDir(currentDirectory, command[1]);
                        String filename = absolutePath;
                        File fileData = new File(filename);
                        if (!fileData.exists()) {
                            System.out.println("ERROR: File " + filename + " does not exist here!");
                            output.println(CMD_FILE_ACTION_UNAVAILABLE);
                            System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
                        } else {
                            output.println(CMD_FILE_STATUS_OKAY);
                            System.out.println(CMD_FILE_STATUS_OKAY);
                            if (hasPort) {
                                DataServer.sendFile(filename, dataPortClient, output);
                            } else {
                                output.println(CMD_CANT_OPEN_CONNECTION);
                                System.out.println(CMD_CANT_OPEN_CONNECTION);
                            }
                        }
                    } else {
                        output.print(CMD_BAD_SEQUENCE);
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("LIST")) {
                    String[] command = data.split(" ");
                    if (hasPort) {
                        if (command.length == 2) {
                            System.out.println("Directory " + currentDirectory+ command[1] + " !");
                            String path = canonicalDir(currentDirectory, command[1]);
                            
                            File fileData = new File(path);
                            if (fileData.exists()) {
                                output.println(CMD_FILE_STATUS_OKAY);
                                System.out.println(CMD_FILE_STATUS_OKAY);
                                System.out.println("Path: " + path);
                                DataServer.listFiles(dataPortClient, path);
                                output.println(CMD_OKAY);
                            } else {
                                System.out.println("ERROR: Directory " + path + " does not exist here!");
                                output.println(CMD_FILE_ACTION_UNAVAILABLE);
                                System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
                            }
                        } else if (command.length == 1) {
                            DataServer.listFiles(dataPortClient, currentDirectory);
                            output.println(CMD_OKAY);
                        }
                    } else {
                        output.println(CMD_CANT_OPEN_CONNECTION);
                        System.out.println(CMD_CANT_OPEN_CONNECTION);
                    }
                } else if (data.startsWith("DELE")) {
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        String filename = canonicalDir(currentDirectory, command[1]);
                        boolean result = deleteFile(filename);
                        if (result) {
                            output.println(CMD_COMPLETED);
                            System.out.println(CMD_COMPLETED);
                        }
                    } else {
                        output.print(CMD_BAD_SEQUENCE);
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("RNFR")) {
                    String[] command = data.split(" ");
                    if (command.length == 3) {
                        String oldFilename = canonicalDir(currentDirectory, command[1]);
                        String newFilename = canonicalDir(currentDirectory, command[2]);
                        System.out.println(oldFilename);
                        System.out.println(newFilename);
                        if (newFilename != currentDirectory) {
                            renameFile(oldFilename, newFilename);
                        } else {
                            output.println(CMD_FILENAME_NOT_ALLOWED);
                            System.out.println(CMD_FILENAME_NOT_ALLOWED);
                        }
                    } else {
                        output.println(CMD_BAD_SEQUENCE);
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("PWD")) {
                    output.println(CMD_GET_DIRECTORY + currentDirectory);
                    System.out.println(CMD_GET_DIRECTORY + currentDirectory);
                } else if (data.startsWith("CWD")) {
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        String directory = canonicalDir(currentDirectory, command[1]);
                        output.println(directory);
                        File dir = new File(directory);

                        if (dir.exists()) {
                            if (dir.isDirectory()) {
                                currentDirectory = directory;
                                output.println(CMD_COMPLETED);
                            } else if (directory.isEmpty()) {
                                System.out.println("ERROR: Access forbidden outside the \"files\\\" folder!");
                            }
                        } else {
                            output.println(CMD_FILE_UNAVAILABLE);
                            System.out.println("ERROR: Directory : " + directory + " does not exist!");
                        }
                    } else {
                        output.print(CMD_BAD_SEQUENCE);
                        System.out.println(CMD_BAD_SEQUENCE);
                    }
                } else if (data.startsWith("MKD")) {
                    String[] command = data.split(" ");
                    String fileDir = canonicalDir(currentDirectory, command[1]);
                    if (new File(fileDir).mkdir()) {
                        output.println(CMD_GET_DIRECTORY + fileDir + " directory created.");
                    } else {
                        output.println(CMD_FILE_UNAVAILABLE);
                    }
                } else if (data.startsWith("RMD")) {
                    //Remove directory
                    String[] command = data.split(" ");
                    if (command.length == 2) {
                        String directory = canonicalDir(currentDirectory, command[1]);
                        File directoryToDelete = new File(directory);
                        String[] entries = directoryToDelete.list();
                        for (String s : entries) {
                            File currentDir = new File(directoryToDelete.getPath(), s);
                            currentDir.delete();
                            System.out.println("Directory deleted");
                            System.out.println("Deleted file from fileList");
                        }
                        directoryToDelete.delete();
                        System.out.println("Se supone que lo he borrado");
                        output.println(CMD_COMPLETED);
                    } else {
                        System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
                        output.println(CMD_FILE_UNAVAILABLE); 
                    }
                } else if (data.startsWith("USER")) {
                    String userData = data.substring(5).trim();
                    if (userData.compareTo(user) == 0) {
                        output.println(CMD_USER_OKAY);
                    }
                } else if (data.startsWith("PASS")) {
                    String passwordData = data.substring(5).trim();
                    if (passwordData.compareTo(password) == 0) {
                        output.println(CMD_USER_LOGGED);
                    } else {
                        output.println(CMD_USER_ERROR);
                    }
                } else if(data.startsWith("END")){
                    data = "END";
                }
                else {
                    output.println(CMD_BAD_SEQUENCE);
                }
            }
            output.println(CMD_CLOSING);
            System.out.println(CMD_CLOSING);

            // Close connection
            sCon.close();

            hasPort = false;
            // Close server socket
            sServ.close();

        } catch (Exception e) {
            e.printStackTrace();
            output.println(CMD_ACTION_ABORTED);
            System.out.println(CMD_ACTION_ABORTED);
        }
    }

    public static boolean logIn(BufferedReader input, PrintWriter output) {
        try {
            String userData = input.readLine();
            if (userData.compareTo(user) == 0) {
                output.println(CMD_USER_OKAY);
                System.out.println(CMD_USER_OKAY);
                output.println("User OK");
            } else {
                output.println("User WRONG");
                return false;
            }

            String passwordData = input.readLine();
            if (passwordData.compareTo(password) == 0) {
                output.println(CMD_USER_LOGGED);
                System.out.println(CMD_USER_LOGGED);
                output.println("Password OK");
            } else {
                output.println(CMD_USER_ERROR);
                System.out.println(CMD_USER_ERROR);
                output.println("Password WRONG");
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            output.println(CMD_ACTION_ABORTED);
            System.out.println(CMD_ACTION_ABORTED);
        }
        return false;
    }

    public static String canonicalDir(String curDir, String directory) {
		String[] paths;
		if (directory.contains("/")) {
			paths = directory.split("/");
		} else if (directory.contains("\\")) {
			paths = directory.split("\\");
		} else {
			paths = new String[]{directory};
		}
		directory = curDir;
		for (String path : paths){
			if (path.compareTo("..")==0){
				int i = directory.lastIndexOf("\\", directory.length()-2);
				if (i==-1) return directory;
				directory = directory.substring(0, i+1);
			}
			else if (path.compareTo(".")!=0){
				directory = directory + path;
				if (path.contains(".")==false) {
					directory = directory + "\\";
				}
			}
		}
		return directory;
	}

    public static Boolean deleteFile(String filename) {
        System.out.println("deleteFile called");
        try {
            File fileData = new File(filename);
            //if (!fileData.exists() || !removeFilenameFromList(filename)) {
            if (!fileData.exists()) {
                output.println(CMD_FILE_UNAVAILABLE);
                System.out.println(CMD_FILE_UNAVAILABLE);
                return false;
            }
            return fileData.delete();

        } catch (Exception e) {
            e.printStackTrace();
            output.println(CMD_ACTION_ABORTED);
            System.out.println(CMD_ACTION_ABORTED);
        }
        return false;
    }

    public static Boolean renameFile(String oldFilename, String newFilename) {
        try {
            File oldFile = new File(oldFilename);
            File newFile = new File(newFilename);

            if (!oldFile.exists()) {
                output.println(CMD_FILE_ACTION_UNAVAILABLE);
                System.out.println(CMD_FILE_ACTION_UNAVAILABLE);
                return false;
            }
            if (newFile.exists()) {
                output.println(CMD_FILENAME_NOT_ALLOWED);
                System.out.println(CMD_FILENAME_NOT_ALLOWED);
                return false;
            }
            Boolean success = oldFile.renameTo(newFile);
            output.println(CMD_COMPLETED);
            System.out.println(CMD_COMPLETED);
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            output.println(CMD_ACTION_ABORTED);
            System.out.println(CMD_ACTION_ABORTED);
        }
        return false;
    }

    //INTERFACE
    public static ArrayList<String> listFiles() {
        try {

            String path = "files/";
            File filesDir = new File(path);
            System.out.println(new File(path).getAbsolutePath());
            ArrayList<String> ListFiles = listFiles(filesDir);
            return ListFiles;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(CMD_ACTION_ABORTED);
        }
        return null;

    }

    public static ArrayList<String> listFiles(File fileData) {
		try {
            ArrayList<String> listFiles = new ArrayList<String>();
			String[] entries = fileData.list();
			for (String s : entries) {
				File currentDir = new File(fileData.getPath(), s);
				if (currentDir.isDirectory()) {
                    if (currentDir.list().length==0) {
                        listFiles.add(currentDir.getPath());
                    }else{
                        ArrayList<String> dirFiles = listFiles(currentDir);
                        for (String fileString : dirFiles) {
                            listFiles.add(fileString);
                        }
                    }
				}else{
					listFiles.add(currentDir.getPath());
				}
			}
			return listFiles;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(CMD_ACTION_ABORTED);
		}
		return new ArrayList<String>();
	}
}
