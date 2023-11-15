import java.net.*;
import java.io.*;
public class SRC {
    static Socket connection;
    public static void main(String a[]) {
        try {
            int v[] = new int[10];
            int n = 0;
            InetAddress addr = InetAddress.getLocalHost(); // Use getLocalHost() to get the localhost address
            System.out.println(addr);
            connection = new Socket(addr, 8011);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(connection.getInputStream());
            int p = in.read(); // Read the number of frames to expect
            System.out.println("No of frame is: " + p);
            for (int i = 0; i < p; i++) {
                v[i] = in.read();
                System.out.println(v[i]);
            }
            int rand = (int) (Math.random() * p); // Generate a random frame number
            v[rand] = -1;
            System.out.println("Marked frame " + rand + " for retransmission.");
            for (int i = 0; i < p; i++) {
                System.out.println("Received frame is: " + v[i]);
            }
            for (int i = 0; i < p; i++) {
                if (v[i] == -1) {
                    System.out.println("Request to retransmit from packet no " + (i + 1) + " again!!");
                    n = i;
                    out.write(n);
                    out.flush();
                }
            }
            System.out.println();
            v[n] = in.read();
            System.out.println("Received frame is: " + v[n]);
            System.out.println("Quitting");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
