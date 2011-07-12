package gestore;

import exception.IllegalUseOfConditionException;

public class Cliente2 extends Thread {

	Gestore mb;

	public Cliente2(Gestore m) {
		mb = m;
	}

	public void run() {
			try {
				System.out.println(Thread.currentThread()
						+ ": Inizio richiesta doppia.");
				Coppia x = mb.richiesta2();	
				System.out.println(Thread.currentThread() + ": Fine richiesta doppia.");
			    sleep(1550);
			    System.out.println(Thread.currentThread()
						+ ": Inizio rilascio doppio.");
				mb.rilascio2(x);		
				System.out.println(Thread.currentThread() + ": Fine rilascio doppio.");
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			} catch (IllegalUseOfConditionException s) {
				System.out.println(s.getMessage());
			} 
			System.out.println(Thread.currentThread() +" ha terminato.");
	}

}
	