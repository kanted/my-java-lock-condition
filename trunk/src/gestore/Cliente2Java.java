package gestore;

public class Cliente2Java extends Thread {

	GestoreJava mb;

	public Cliente2Java(GestoreJava m) {
		mb = m;
	}

	public void run() {
			try {
				System.out.println(Thread.currentThread()
						+ ": Inizio richiesta doppia.");
				Coppia x = mb.richiesta2();	
			    sleep(1550);
				mb.rilascio2(x);		
				System.out.println(Thread.currentThread() + ": Fine richiesta doppia.");

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	