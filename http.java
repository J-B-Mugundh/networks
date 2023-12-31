import java.net.*;
import java.io.*;
import java.util.Scanner;

class http {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter port : ");
        Scanner sin = new Scanner(System.in);
        int port = sin.nextInt();
        ServerSocket ss = new ServerSocket(port);
        System.err.println("Local Host Server running at : " + port);
        while (true) {
            Socket sk = ss.accept();
            System.err.println("Server connected!");
            BufferedReader in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                if (s.isEmpty()) {
                    break;
                }
            }
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: 59\r\n");
            out.write("\r\n");
            out.write("<TITLE>Mug Site</TITLE>");
            out.write("<P>Hello, This is Mug here!!</P>");
            out.write("<P>" + s + "</P>");
            System.err.println("Server Connection Closed\n");
            out.close();
            in.close();
            sk.close();
        }
    }
}
