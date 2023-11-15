import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        while (true) {
            DpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(DpReceive);
            System.out.println("Client :- " + data(receive));
            if (data(receive).toString().equals("exit")) {
                System.out.println("Client sent bye.....EXITING");
                break;
            }
            receive = new byte[65535];
        }
    }

    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder s = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            s.append((char) a[i]);
            i++;
        }
        return s;
    }
}