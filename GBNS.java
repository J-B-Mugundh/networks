import java.io.*;
import java.net.*;
import java.util.Random;

public class GBNS {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(55004);
        System.out.println("Server established");
        Socket client = server.accept();
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        System.out.println("Client is now connected");
        int x = (Integer) ois.readObject();
        int k = 0; // Initialize k to 0
        int j = 0;
        boolean flag = true;
        Random r = new Random(); // Corrected the instantiation of Random
        int mod = r.nextInt(6);
        while (mod == 1 || mod == 0)
            mod = r.nextInt(6);

        while (true) {
            int c = k;
            for (int h = 0; h < x; h++) { // Changed h <= x to h < x
                System.out.print("|" + c + "|");
                c = (c + 1) % x;
            }
            System.out.println();
            System.out.println();

            if (k == j) {
                System.out.println("Frame " + k + " received" + "\n" + "Data: " + j);
                j++;
                System.out.println();
            } else
                System.out.println(
                        "Frames received not in the correct order" +
                                "\n" +
                                "Expected frame: " + j +
                                "\n" +
                                "Received frame no: " + k);
            System.out.println();
            if (j % mod == 0 && flag) {
                System.out.println("Error found. Acknowledgment not sent. ");
                flag = !flag;
                j--;
            } else if (k == j - 1) {
                oos.writeObject(k);
                System.out.println("Acknowledgment sent");
            }
            System.out.println();

            if (j % mod == 0)
                flag = !flag;
            k = (Integer) ois.readObject();
            if (k == -1)
                break;
        }
        System.out.println("Client finished sending data. Exiting");
        oos.writeObject(-1);
    }
}
