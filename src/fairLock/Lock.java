package fairLock;

import java.util.ArrayList;
import java.util.List;

public class Lock {
	List<Semaphore> entry_queue; // Coda di Semaphore, uno per ogni thread in attesa del lock.
	List<Semaphore> urgent_queue; 
	boolean libero;

	public Lock(){
		entry_queue = new ArrayList<Semaphore>();
		urgent_queue = new ArrayList<Semaphore>();
		libero = true;
	}

	public void lock() throws InterruptedException {
		Semaphore element;
		synchronized (this) {
			if(libero == true){
				libero = false; 
				return;
			}
			element = new Semaphore();
			entry_queue.add(element);
			System.out.println(Thread.currentThread()
						+ ": bloccato sul lock.");
		}
		element.P();
		synchronized (this) {
			entry_queue.remove(0);
			System.out.println(Thread.currentThread() + ": acquisisce il lock.");
		}
	}

	/*public void lock() throws InterruptedException {
		boolean currentLibero;
		Semaphore element = new Semaphore();
		synchronized (this) {
			currentLibero  = libero;
			libero = false; // In caso libero fosse giˆ stato a false non cambio nulla. In caso invece
							// potessi acquisire il lock, lo prendo.
			if (currentLibero == false) { // Se mi devo bloccare.
				entry_queue.add(element);
				System.out.println(Thread.currentThread() + ": bloccato sul lock");
			}
		}
		if (currentLibero == false) {
			element.P();
		}
		synchronized (this) {
			if (currentLibero == false) { // Se mi ero bloccato.
				entry_queue.remove(0);
			}
			//TODO prima libero stava qu“
			System.out.println(Thread.currentThread() + ": acquisisce il lock");
		}
	}*/

	public synchronized void unlock() throws InterruptedException {	
		if (urgent_queue.isEmpty() && entry_queue.isEmpty()) {
			System.out.println(Thread.currentThread() + ": rilascia il lock");
			libero = true;
		}
		else if (!urgent_queue.isEmpty()) { // Prima vengono risvegliati gli urgent, passaggio del testimone.
			Semaphore element = urgent_queue.get(0);
			element.V();
		} else { // Politica FIFO, passaggio del testimone.
			Semaphore element = entry_queue.get(0);
			element.V();
		}
	}

	public Condition newCondition() {
		Condition cond = new Condition(this);
		return cond;
	}

}
