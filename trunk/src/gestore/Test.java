package gestore;

public class Test {
	
	static int simulationLength = 3;
	static int numElements = 5;
	
	public static void main(String[] args) {
	try {
			Gestore m = new Gestore(numElements);
			Cliente1[] r = new Cliente1[simulationLength];
			Cliente2[] s = new Cliente2[simulationLength];
			System.out.println("Inizio della simulazione.");
			for (int i = 0; i < simulationLength; i++) {
				
				Cliente1 r2 = new Cliente1(m);
				r[i] = r2;
				r2.start();

				Cliente2 s1 = new Cliente2(m);
				s[i] = s1;
				s1.start();
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
