import java.io.*;
import java.net.*;

public class FTPClient {
    public static void main(String[] args) throws Exception {
        String option;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("localhost", Integer.parseInt(args[0]));
        
        System.out.println("MENU");
        System.out.println("1.SEND");
        System.out.println("2.RECEIVE");
        
        FTPClient ftp = new FTPClient();
        
        while (true) {
            option = userInput.readLine();
            if (option.equals("1")) {
                System.out.println("SEND Command Received..");
                ftp.sendFile(socket);
            } else if (option.equals("2")) {
                System.out.println("RECEIVE Command Received..");
                ftp.receiveFile(socket);
            }
        }
    }

    public void sendFile(Socket socket) throws Exception {
        Socket dataSocket = socket;
        
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream dataIn = new DataInputStream(dataSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(dataSocket.getOutputStream());

        dataOut.writeUTF("RECEIVE");

        System.out.print("Enter the filename to send: ");
        String filename = userInput.readLine();
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
        
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream dataIn = new DataInputStream(dataSocket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(dataSocket.getOutputStream());

        dataOut.writeUTF("SEND");

        System.out.print("Enter the filename to receive: ");
        String filename = userInput.readLine();
        dataOut.writeUTF(filename);
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
