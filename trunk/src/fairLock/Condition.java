package fairLock;

import java.util.ArrayList;
import java.util.List;

import exception.IllegalUseOfConditionException;

public class Condition {
	private List<Semaphore> queue; // Coda FIFO di semafori, uno per ogni thread bloccato sulla condizione.
	private Lock lock;

	public void await() throws InterruptedException, IllegalUseOfConditionException {
		if(lock.libero==true)
			throw new IllegalUseOfConditionException();
		Semaphore element = new Semaphore();
		System.out.println(Thread.currentThread() + ": bloccato sulla condition");
		queue.add(element);
		lock.unlock(); // Rilascio il lock.
		element.P();
		queue.remove(0);
		System.out.println(Thread.currentThread() + ": sbloccato sulla condition.");
		//Passaggio del testimone ,senza rilasciare il lock, quindi non serve che lo riacquisisco, 
		//qualcuno me lo avrˆ passato.
	}

	public void signal() throws InterruptedException, IllegalUseOfConditionException {
		if(lock.libero==true)
			throw new IllegalUseOfConditionException();
		if (queue.isEmpty()) {
			return;
		}
		Semaphore urgent_element = new Semaphore();
		Semaphore element = queue.get(0);
		element.V(); // signal & continue
		lock.urgent_queue.add(urgent_element);
		System.out.println(Thread.currentThread()
				+ ": bloccato per signal & urgent.");
		urgent_element.P(); // passaggio del testimone, senza rilasciare il lock
		lock.urgent_queue.remove(0);
		System.out.println(Thread.currentThread() + ": sbloccato.");

	}
	
	public boolean empty(){
		return queue.isEmpty();
	}

	public Condition(Lock l) {
		queue = new ArrayList<Semaphore>();
		lock = l;
	}

}
