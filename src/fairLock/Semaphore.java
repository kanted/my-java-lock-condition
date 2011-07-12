package fairLock;

public class Semaphore {

	 boolean notified = false;

	  public synchronized void P() throws InterruptedException {
	    while(!notified){ // Pu˜ succedere che qualcuno faccia la P sul semaforo prima che sia fatta la V.
	        this.wait();
	    }
	    this.notified = false;
	  }

	  public synchronized void V() {
	    this.notified = true;
	    this.notify();
	  }
	  
}
