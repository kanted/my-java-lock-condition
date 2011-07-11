package gestore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GestoreJava {
	private int length;
	boolean libera[];
	int singole;
	int doppie;
	int disp;
	private Lock lock = new ReentrantLock();
	private Condition singola = lock.newCondition();
	private Condition doppia = lock.newCondition();

	private void printUtility() {
		System.out.println("Libera = ");
		for (int i = 0; i < length; i++) {
			System.out.print(libera[i]);
		}
		System.out.println(" ");
	}

	public GestoreJava(int numElements) {
		length = numElements;
		libera = new boolean[length];
		for (int i = 0; i < length; i++) {
			libera[i] = true;
		}
		disp = length;
		singole = 0;
		doppie = 0;
	}

	public int richiesta1() throws InterruptedException {
		int i = 0;
		lock.lock();
		printUtility();
		try {
			if (disp == 0){
				singole ++;
				singola.await();
			}
			else { // Passaggio della condizione.
				disp--;
			}
			while (!libera[i])
				i++;
			libera[i] = false;
			System.out.println(Thread.currentThread() + ": assegno" + i + ".");
			printUtility();
		} finally {
			lock.unlock();
		}
		return i;
	}

	public void rilascio1(int x) throws InterruptedException {
		lock.lock();
		printUtility();
		try {
			System.out.println(Thread.currentThread() + ": rilascio " + x
					+ " .");
			libera[x] = true;
			if (doppie>0 && disp > 1){ // Prioritˆ.
				doppie --;
				doppia.signal();
				disp --;
			}
			else if(singole>0){
				singole --;
				singola.signal();
			}
			else
				disp ++;
		} finally {
			lock.unlock();
		}
	}

	public Coppia richiesta2() throws InterruptedException {
		Coppia c = new Coppia();
		int i = 0;
		lock.lock();
		printUtility();
		try {
			if (disp < 2){
				doppie ++;
				doppia.await();
			}
			else
				disp = disp - 2;
			while (!libera[i])
				i++;
			c.primo = i;
			libera[i] = false;
			System.out.println(Thread.currentThread() + ": assegno " + i + ".");
			while (!libera[i])
				i++;
			c.secondo = i;
			libera[i] = false;
			System.out.println(Thread.currentThread() + ": assegno " + i + ".");
			printUtility();
		} finally {
			lock.unlock();
		}
		return c;
	}

	public void rilascio2(Coppia x) throws InterruptedException {
		lock.lock();
		printUtility();
		try {
			System.out.println(Thread.currentThread() + ": rilascio " + x.primo
					+ " e " + x.secondo + " .");
			libera[x.primo] = true;
			libera[x.secondo] = true;
			disp = disp + 2;
			if (doppie > 0){
				doppie --;
				doppia.signal();
			}
			else if (singole > 0) { 
				singole --;
				singola.signal();
				if(singole > 0){
					singole --;
					singola.signal();
				}
				else
					disp ++;
			}
			else
				disp = disp+2;
		} finally {
			lock.unlock();
		}
	}
}
