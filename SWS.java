import java.net.*;
import java.io.*;

public class SWS {
    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket(1234);
        byte[] receiveBuffer = new byte[65535];
        DatagramPacket receivePacket;

        while (true) {
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String clientMessage = data(receiveBuffer).toString();
            System.out.println("Received Frame: " + clientMessage);

            if (clientMessage.equals("exit")) {
                System.out.println("Client sent bye... EXITING");
                break;
            }
            Thread.sleep(1000);
            String ackMessage = "ACK " + clientMessage;
            byte[] ackBuffer = ackMessage.getBytes();
            DatagramPacket ackPacket = new DatagramPacket(ackBuffer, ackBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
            socket.send(ackPacket);
            Thread.sleep(1000);
            receiveBuffer = new byte[65535];
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
