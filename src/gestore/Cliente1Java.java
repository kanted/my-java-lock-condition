package gestore;

public class Cliente1Java extends Thread {

	GestoreJava mb;

	public Cliente1Java(GestoreJava m) {
		mb = m;
	}

	public void run() {
			try {
				System.out.println(Thread.currentThread()
						+ ": Inizio richiesta singola.");
				int x = mb.richiesta1();
				System.out.println(Thread.currentThread() + ": Fine richiesta singola.");
				sleep(1500);
				System.out.println(Thread.currentThread()
						+ ": Inizio rilascio singolo.");
				mb.rilascio1(x);
				System.out.println(Thread.currentThread() + ": Fine rilascio singolo.");

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	