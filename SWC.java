import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SWC {
    public static void main(String args[]) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte[] buffer;

        while (true) {
            String message = scanner.nextLine();
            buffer = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, ip, 1234);
            socket.send(sendPacket);

            if (message.equals("exit")) {
                break;
            }
            Thread.sleep(1000);
            byte[] ackBuffer = new byte[65535];
            DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length);
            socket.receive(ackPacket);
            String ackMessage = data(ackBuffer).toString();
            System.out.println("Received ACK: " + ackMessage);
            Thread.sleep(1000);
        }

        socket.close();
    }

    private static StringBuilder data(byte[] array) {
        if (array == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (array[i] != 0) {
            stringBuilder.append((char) array[i]);
            i++;
        }
        return stringBuilder;
    }
}
