package mailbox;

import exception.IllegalUseOfConditionException;

public class Sender extends Thread {

	Mailbox mb;

	public Sender(Mailbox m) {
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
			} catch (IllegalUseOfConditionException s) {
				System.out.println(s.getMessage());
			} 
		}
		System.out.println(Thread.currentThread()+" ha terminato.");
	}

}
	