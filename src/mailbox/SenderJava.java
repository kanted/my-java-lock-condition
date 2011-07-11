package mailbox;

public class SenderJava extends Thread {

	MailboxJava mb;

	public SenderJava(MailboxJava m) {
		mb = m;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				String element = "X";
				System.out.println(Thread.currentThread()
						+ ": Inizio deposito.");
				mb.deposita(element);
				System.out.println(Thread.currentThread() + ": Fine deposito.");

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println(Thread.currentThread()+" ha terminato.");
	}

}
	