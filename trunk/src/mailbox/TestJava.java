package mailbox;

public class TestJava {
	
	static int simulationLength = 1;
	static int numElements = 3;
	
	public static void main(String[] args) {
	try {
			MailboxJava m = new MailboxJava(numElements);
			SenderJava[] s = new SenderJava[simulationLength];
			ReceiverJava[] r = new ReceiverJava[simulationLength];
			System.out.println("Inizio della simulazione.");
			for (int i = 0; i < simulationLength; i++) {
				
				ReceiverJava r2 = new ReceiverJava(m);
				r[i] = r2;
				r2.start();

				SenderJava s1 = new SenderJava(m);
				s[i] = s1;
				s1.start();
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
