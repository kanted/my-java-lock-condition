package fairLock;

import java.util.ArrayList;
import java.util.List;

import exception.IllegalUseOfConditionException;

public class Condition {
	private List<Semaphore> queue; // Coda FIFO di semafori, uno per ogni thread bloccato sulla condizione.
	private Lock lock;

	public void await() throws InterruptedException, IllegalUseOfConditionException {
		if(lock.isUnlocked())
			throw new IllegalUseOfConditionException();
		Semaphore element = new Semaphore();
		System.out.println(Thread.currentThread() + ": bloccato sulla condition");
		queue.add(element);
		lock.unlock(); // Rilascio il lock.
		element.P();
		queue.remove(0);
		System.out.println(Thread.currentThread() + ": sbloccato sulla condition.");
		// Passaggio del testimone, quindi lo svegliante mi avrˆ passato il lock. 
	}

	public void signal() throws InterruptedException, IllegalUseOfConditionException {
		if(lock.isUnlocked())
			throw new IllegalUseOfConditionException();
		if (queue.isEmpty()) {
			return;
		}
		Semaphore urgent_element = new Semaphore();
		Semaphore element = queue.get(0);
		lock.addUrgentElement(urgent_element); // L'inserimento DEVE essere fatto prima della V in modo che se 
		// il thread associato ad element parte ed arriva a fare la unlock, 
		// quando termina mi ripassa la mutua esclusione.
		element.V(); // signal & continue
		System.out.println(Thread.currentThread()
				+ ": bloccato per signal & urgent.");
		urgent_element.P(); // Passaggio del testimone, quindi non devo rilasciare/riacquisire il lock.
		lock.removeUrgentElement(0);
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
