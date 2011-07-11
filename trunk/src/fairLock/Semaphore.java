package fairLock;

public class Semaphore {

	 boolean notified = false;

	  public synchronized void P() throws InterruptedException {
	    while(!notified){ // Non è mai possibile che uno faccia la notify prima della wait volontariamente
	    	              // perchè il semaforo non sarebbe nella coda.
	    	// Può succedere che qualcuno faccia la P sul semaforo prima ancora che quello che doveva farci la v.
	    	// While perché è signal and continue quindi può succedere di tutto finché non mi risveglio e 
	    	// quindi mi cambia la condizione.
	        this.wait();
	    }
	    this.notified = false;
	  }

	  public synchronized void V() {
	    this.notified = true;
	    this.notify();
	  }
	  
}
