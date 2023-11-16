import java.util.*;

class Packet {
    int sequenceNumber;
    String data;

    public Packet(int sequenceNumber, String data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}

class GoBackN {
    private static final int WINDOW_SIZE = 4;
    private static final int TOTAL_PACKETS = 10;

    private List<Packet> sentPackets;
    private List<Boolean> ackReceived;
    private int base;
    private int nextSeqNumber;

    public GoBackN() {
        sentPackets = new ArrayList<>();
        ackReceived = new ArrayList<>(Collections.nCopies(TOTAL_PACKETS, false));
        base = 0;
        nextSeqNumber = 0;
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
            for (int i = 0; i < WINDOW_SIZE && (base + i) < TOTAL_PACKETS; i++) {
                Packet receivedPacket = sentPackets.get(base + i);

                // Simulate the acknowledgment process
                boolean isAckReceived = random.nextBoolean();

                if (isAckReceived) {
                    if (!ackReceived.get(receivedPacket.sequenceNumber)) {
                        ackReceived.set(receivedPacket.sequenceNumber, true);
                        System.out.println("Receiver: Acknowledgment received for packet " + receivedPacket.sequenceNumber);
                    }
                } else if (!ackReceived.get(receivedPacket.sequenceNumber)){
                    System.out.println("Receiver: Acknowledgment not received for packet " + receivedPacket.sequenceNumber);
                }
            }

            while (base < TOTAL_PACKETS && ackReceived.get(base)) {
                base++;
                nextSeqNumber++;
            }

            if (base < TOTAL_PACKETS) {
                System.out.println("Sender: Resending packets starting from " + base);
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
        GoBackN goBackN = new GoBackN();
        goBackN.sender();
    }
}
