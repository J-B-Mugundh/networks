import java.io.*;
import java.net.*;

public class FTPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        Socket socket = serverSocket.accept();
        
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

        FTPServer ftp = new FTPServer();
        
        while (true) {
            String option = dataIn.readUTF();
            if (option.equals("SEND")) {
                System.out.println("SEND Command Received..");
                ftp.sendFile(socket);
            } else if (option.equals("RECEIVE")) {
                System.out.println("RECEIVE Command Received..");
                ftp.receiveFile(socket);
            }
        }
    }

    public void sendFile(Socket socket) throws Exception {
        Socket dataSocket = socket;
        
        DataInputStream dataIn = new DataInputStream(dataSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(dataSocket.getOutputStream());

        String filename = dataIn.readUTF();
        System.out.println("Reading File: " + filename);
        dataOut.writeUTF(filename);

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                dataOut.writeUTF(line);
            }
        }
        
        dataOut.writeUTF("EOF");
        System.out.println("File Sent");
    }

    public void receiveFile(Socket socket) throws Exception {
        Socket dataSocket = socket;
        
        DataInputStream dataIn = new DataInputStream(dataSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(dataSocket.getOutputStream());

        String filename = dataIn.readUTF();
        System.out.println("Receiving File: " + filename);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filename))) {
            String line;
            while (!(line = dataIn.readUTF()).equals("EOF")) {
                fileWriter.write(line);
                fileWriter.newLine();
            }
        }
        
        System.out.println("Received File...");
    }
}
