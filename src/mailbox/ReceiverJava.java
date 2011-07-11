package mailbox;

public class ReceiverJava extends Thread {

	MailboxJava mb;

	public ReceiverJava(MailboxJava m) {
		mb = m;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				System.out.println(Thread.currentThread()
						+ ": Inizio prelievo.");
				mb.preleva();
				System.out.println(Thread.currentThread() + ": Fine prelievo.");

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	