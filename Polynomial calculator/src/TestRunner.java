import org.junit.runner.*;
import org.junit.runner.notification.*;

public class TestRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Result rezultat = JUnitCore.runClasses(JUnitTest.class);
		System.out.println("Toate testele au trecut cu succes? " + rezultat.wasSuccessful());
		if(!rezultat.wasSuccessful()){
			System.out.println("Teste esuate:");
			for (Failure esec : rezultat.getFailures()) {
				System.out.println(esec.toString());

			}
		}
	}
}
