import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SRS {
    static ServerSocket serverSocket;
    static DataInputStream dis;
    static DataOutputStream dos;

    public static void main(String[] args) {
        try {
            int a[] = {30, 40, 50, 60, 70, 80, 90, 100};
            serverSocket = new ServerSocket(8011);
            System.out.println("Waiting for connection");
            Socket client = serverSocket.accept();
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            System.out.println("The number of packets sent is: " + a.length);
            int y = a.length;
            dos.write(y);
            dos.flush();
            for (int i = 0; i < a.length; i++) {
                dos.write(a[i]);
                dos.flush();
            }
            int k = dis.read();
            dos.write(a[k]);
            dos.flush();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

