package gestore;

public class Cliente1 extends Thread {

	Gestore mb;

	public Cliente1(Gestore m) {
		mb = m;
	}

	public void run() {
			try {
				System.out.println(Thread.currentThread()
						+ ": Inizio richiesta singola.");
				int x = mb.richiesta1();
				sleep(1500);
				mb.rilascio1(x);
				System.out.println(Thread.currentThread() + ": Fine richiesta singola.");

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	