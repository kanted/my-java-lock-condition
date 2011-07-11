package mailbox;

public class Test {
	
	static int simulationLength = 1;
	static int numElements = 3;
	
	public static void main(String[] args) {
	try {
			Mailbox m = new Mailbox(numElements);
			Sender[] s = new Sender[simulationLength];
			Receiver[] r = new Receiver[simulationLength];
			System.out.println("Inizio della simulazione.");
			for (int i = 0; i < simulationLength; i++) {
				
				Receiver r2 = new Receiver(m);
				r[i] = r2;
				r2.start();

				Sender s1 = new Sender(m);
				s[i] = s1;
				s1.start();
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
