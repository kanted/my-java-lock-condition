package gestore;

public class TestJava {
	
	static int simulationLength = 3;
	static int numElements = 5;
	
	public static void main(String[] args) {
	try {
			GestoreJava m = new GestoreJava(numElements);
			Cliente1Java[] r = new Cliente1Java[simulationLength];
			Cliente2Java[] s = new Cliente2Java[simulationLength];
			System.out.println("Inizio della simulazione.");
			for (int i = 0; i < simulationLength; i++) {
				
				Cliente1Java r2 = new Cliente1Java(m);
				r[i] = r2;
				r2.start();

				Cliente2Java s1 = new Cliente2Java(m);
				s[i] = s1;
				s1.start();
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
