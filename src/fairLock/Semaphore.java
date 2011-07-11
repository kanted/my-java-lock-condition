package fairLock;

public class Semaphore {

	 boolean notified = false;

	  public synchronized void P() throws InterruptedException {
	    while(!notified){ // Non � mai possibile che uno faccia la notify prima della wait volontariamente
	    	              // perch� il semaforo non sarebbe nella coda.
	    	// Pu� succedere che qualcuno faccia la P sul semaforo prima ancora che quello che doveva farci la v.
	    	// While perch� � signal and continue quindi pu� succedere di tutto finch� non mi risveglio e 
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
