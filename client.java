import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String args[]) throws IOException {
        Scanner in = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;

        while (true) {
            String s = in.nextLine();
            buf = s.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);
            ds.send(DpSend);
            if (s.equals("exit")) {
                break;
            }
        }

        ds.close();
    }
}
