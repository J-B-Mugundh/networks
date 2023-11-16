import java.util.*;

class Packet {
    int sequenceNumber;
    String data;

    public Packet(int sequenceNumber, String data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}

class SelectiveRepeat {
    private static final int WINDOW_SIZE = 4;
    private static final int TOTAL_PACKETS = 10;

    private List<Packet> sentPackets;
    private List<Boolean> ackReceived;

    public SelectiveRepeat() {
        sentPackets = new ArrayList<>();
        ackReceived = new ArrayList<>(Collections.nCopies(TOTAL_PACKETS, false));
    }

    public void sender() {
        for (int i = 0; i < TOTAL_PACKETS; i++) {
            Packet packet = new Packet(i, "Data for packet " + i);
            sentPackets.add(packet);
            System.out.println("Sender: Sending packet " + i);
        }

        receiver();
    }

    public void receiver() {
        Random random = new Random();

        while (!allPacketsAcked()) {
            for (int i = 0; i < sentPackets.size(); i++) {
                Packet receivedPacket = sentPackets.get(i);
                boolean isAckReceived = random.nextBoolean();

                if (isAckReceived) {
                    if (!ackReceived.get(receivedPacket.sequenceNumber)) {
                        ackReceived.set(receivedPacket.sequenceNumber, true);
                        System.out.println("Receiver: Acknowledgment received for packet " + receivedPacket.sequenceNumber);
                        
                    }
                } else if(!ackReceived.get(receivedPacket.sequenceNumber)){
                    System.out.println("Receiver: Acknowledgment not received for packet " + receivedPacket.sequenceNumber);
                    System.out.println(receivedPacket.sequenceNumber + " will be resent");
                }
            }
        }
    }

    private boolean allPacketsAcked() {
        for (boolean ack : ackReceived) {
            if (!ack) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SelectiveRepeat selectiveRepeat = new SelectiveRepeat();
        selectiveRepeat.sender();
    }
}
