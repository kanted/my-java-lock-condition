package fairLock;

import java.util.ArrayList;
import java.util.List;

public class Lock {
	private List<Semaphore> entry_queue;
	private List<Semaphore> urgent_queue; 
	private boolean libero;

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

	public synchronized void unlock() throws InterruptedException {	
		if (urgent_queue.isEmpty() && entry_queue.isEmpty()) {
			System.out.println(Thread.currentThread() + ": rilascia il lock.");
			libero = true;
		} else if (!urgent_queue.isEmpty()) { // Prioritˆ agli urgent, passaggio del testimone.
			Semaphore element = urgent_queue.get(0);
			element.V();
		} else { // Politica FIFO, passaggio del testimone.
			Semaphore element = entry_queue.get(0);
			element.V();
		}
	}
	
	public synchronized void addUrgentElement (Semaphore urgent_element){
		urgent_queue.add(urgent_element);
	}
	
	public synchronized void removeUrgentElement (int index){
		urgent_queue.remove(0);
	}
	
	public synchronized boolean isUnlocked(){
		return libero;
	}

	public Condition newCondition() {
		Condition cond = new Condition(this);
		return cond;
	}

}
