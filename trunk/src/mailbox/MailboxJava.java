package mailbox;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class MailboxJava {
	private int length;
	private String[] contenuto;
	private int contatore, testa, coda;
	private Lock lock = new ReentrantLock();
	private Condition non_pieno = lock.newCondition();
	private Condition non_vuoto = lock.newCondition();
	
	private void printUtility(){
		System.out.println("Contenuto mailbox = ");
		for (int i = 0 ; i< length ; i++){
			System.out.print(contenuto[i]);
		}
		System.out.println(" ");
	}

	public MailboxJava(int numElements) {
		length = numElements;
		contenuto = new String[length];
		for (int i = 0; i<length; i++){
			contenuto[i]= "O";
		}
		contatore = 0;
		testa = 0;
		coda = 0;
	}

	public String preleva() throws InterruptedException {	
		String elemento;
		lock.lock();
		printUtility();
		try {
			while (contatore == 0)
				non_vuoto.await();	
			System.out.println(Thread.currentThread()
					+ ": prelevando.");	
			elemento = contenuto[testa];
			contenuto[testa] = "O";
			testa = (testa + 1) % length;
			--contatore;	
			printUtility();
			non_pieno.signal();	
		} finally {
			lock.unlock();
		}	
		return elemento;
	}

	public void deposita(String valore) throws InterruptedException {
		lock.lock();
		printUtility();
		try {
			while (contatore == length)
				non_pieno.await();
			System.out.println(Thread.currentThread()
					+ ": depositando.");
			contenuto[coda] = valore;
			coda = (coda + 1) % length;
			++contatore;
			printUtility();
			non_vuoto.signal();
		} finally {
			lock.unlock();
		}
	}
}
