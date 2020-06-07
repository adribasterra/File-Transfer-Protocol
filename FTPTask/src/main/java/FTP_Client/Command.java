package FTP_Client;

public class Command {
    String clientCommand = null;
    String ftpCommand = null;
    String pathName = null;
    String fileName = null;

    String otherPathName = null;
    String otherFileName = null;

    public Command(String clientInput, String curDir){
        String[] command = clientInput.split(" ");
        clientCommand = command[0];
        ftpCommand = clientToFTPCommand(clientCommand);

        String param = command[1];
        setPathAndFileName(curDir, param);

        String dataTCP = "STOR" + " " + filename;						//STOR <SP> <pathname> <CRLF> 
    }

    public static String clientToFTPCommand(String clientCommand) {
        switch (clientCommand) {
            case "list":
                return "LIST";
        
            default:
                return null;
        }
    }

    public static Boolean setPathAndFileName(String curDir, String directory) {
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
				directory = directory + path + "\\";
			}
		}
        return true;
    }
}