package mailbox;

import exception.IllegalUseOfConditionException;

public class Receiver extends Thread {

	Mailbox mb;

	public Receiver(Mailbox m) {
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
			} catch (IllegalUseOfConditionException s) {
				System.out.println(s.getMessage());
			} 
		}
		System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	