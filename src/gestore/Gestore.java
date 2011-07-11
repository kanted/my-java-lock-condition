package gestore;
import exception.IllegalUseOfConditionException;
import fairLock.Condition;
import fairLock.Lock;

public class Gestore {
	private int length;
	boolean libera[];
	int disp;
	private Lock lock = new Lock();
	private Condition singola = lock.newCondition();
	private Condition doppia = lock.newCondition();
	
	private void printUtility(){
		System.out.println("Libera = ");
		for (int i = 0 ; i< length ; i++){
			System.out.print(libera[i]);
		}
		System.out.println(" ");
	}

	public Gestore(int numElements) {
		length = numElements;
		libera = new boolean[length];
		for (int i = 0; i<length; i++){
			libera[i]= true;
		}
		disp = length;
	}

	public int richiesta1() throws InterruptedException, IllegalUseOfConditionException {		
		int i = 0;
		lock.lock();
		printUtility();
		try {
			if (disp == 0)
				singola.await();	
			disp --;
			while(!libera[i])
				i++;
			libera[i]=false;
			System.out.println(Thread.currentThread()
					+ ": assegno"+i+".");		
			printUtility();
		} finally {
			lock.unlock();
		}	
		return i;
	}
	
	public void rilascio1(int x) throws InterruptedException, IllegalUseOfConditionException {		
		lock.lock();
		printUtility();
		try {
			System.out.println(Thread.currentThread()
					+ ": rilascio "+x+" .");
			libera[x] = true;
			disp ++;
			if (!doppia.empty()&&disp>1) // Prioritˆ.
				doppia.signal();
			else
				singola.signal();
		} finally {
			lock.unlock();
		}	
	}

	public Coppia richiesta2() throws InterruptedException, IllegalUseOfConditionException {		
		Coppia c = new Coppia();
		int i = 0;
		lock.lock();
		printUtility();
		try {
			if (disp < 2)
				doppia.await();	
			disp = disp - 2;
			while(!libera[i])
				i++;
			c.primo = i;
			libera[i]=false;
			System.out.println(Thread.currentThread()
					+ ": assegno "+i+".");	
			while(!libera[i])
				i++;
			c.secondo = i;
			libera[i]=false;
			System.out.println(Thread.currentThread()
					+ ": assegno "+i+".");		
			printUtility();
		} finally {
			lock.unlock();
		}	
		return c;
	}
	
	public void rilascio2(Coppia x) throws InterruptedException, IllegalUseOfConditionException {		
		lock.lock();
		printUtility();
		try {
			System.out.println(Thread.currentThread()
					+ ": rilascio "+x.primo+" e "+x.secondo+" .");	
			libera[x.primo] = true;
			libera[x.secondo] = true;
			disp = disp + 2;
			if (!doppia.empty()) // Prioritˆ.
				doppia.signal();
			else
				if (!singola.empty()){ // Ne sveglio uno e mi blocco. Quando mi risveglio ne sveglio un altro.
					singola.signal();
					singola.signal();
				}
		} finally {
			lock.unlock();
		}	
	}
}
