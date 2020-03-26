import static org.junit.Assert.*;
import org.junit.*;

public class JUnitTest {

	private static CalculatorView v = new CalculatorView();
	private static CalculatorController c = new CalculatorController(v);
	private static int nrTesteExecutate = 0;
	private static int nrTesteCuSucces = 0;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("S-au executat " + nrTesteExecutate + " teste din care "+ nrTesteCuSucces + " au avut succes!");
	}

	@Before
	public void setUp() throws Exception {
		nrTesteExecutate++;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testResetGetValue() {
		v.reset();
		String userfx = v.getfxInput();
		String usergx = v.getgxInput();
		String result = v.getResult();
		assertEquals(userfx, ""); 
		assertEquals(usergx, ""); 
		assertEquals(result, "0"); 
		nrTesteCuSucces++;
	}	
	
	@Test
	public void testAdd() {
		v.reset();
		String userfx = "1+2+x^2+5x^3-4x^6";
		String usergx = "-2+3x^2-2x^3+4x^6";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = new Polinom(usergx);
		Polinom p3 = p1.add(p2);
		String result = p3.getPolinom().toString();
		assertNotNull(result); 
		assertEquals(result,"1+4x^2+3x^3"); 
		nrTesteCuSucces++;		
	}
	
	@Test
	public void testSub() {
		v.reset();
		String userfx = "1+2+x^2+5x^3-4x^6";
		String usergx = "-2+3x^2-2x^3-4x^6";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = new Polinom(usergx);
		Polinom p3 = p1.sub(p2);
		String result = p3.getPolinom().toString();
		assertNotNull(result); // 
		assertEquals(result,"5-2x^2+7x^3"); 
		nrTesteCuSucces++;			
	}	
	
	@Test
	public void testMultiply() {
		v.reset();
		String userfx = "x^3-2x+1";
		String usergx = "x+2";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = new Polinom(usergx);
		Polinom p3 = p1.multiply(p2);
		String result = p3.getPolinom().toString();
		assertNotNull(result); // 
		assertEquals(result,"2-3x-2x^2+2x^3+x^4"); 
		nrTesteCuSucces++;			
	}
	
	@Test
	public void testDivide() {
		v.reset();
		String userfx = "x^3-2x+1";
		String usergx = "x+2";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = new Polinom(usergx);
		Polinom []p3 = p1.divide(p2);
		String quotient = p3[0].getPolinom().toString();
		String remainder = p3[1].getPolinom().toString();
		assertNotNull(quotient); 
		assertNotNull(remainder); 
		assertEquals(quotient,"2-2x+x^2"); 
		assertEquals(remainder,"-3"); 
		nrTesteCuSucces++;			
	}
	
	@Test
	public void testDifferentiate() {
		v.reset();
		String userfx = "1+2+x^2+5x^3-4x^6";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = p1.differentiate();
		String result = p2.getPolinom().toString();
		assertNotNull(result); // 
		assertEquals(result,"2x+15x^2-24x^5"); 
		nrTesteCuSucces++;			
	}
	
	@Test
	public void testIntegrate() {
		v.reset();
		String userfx = "2+3x^2+5x^3-7x^6";
		Polinom p1 = new Polinom(userfx);
		Polinom p2 = p1.integrate();
		String result = p2.getPolinom().toString();
		assertNotNull(result);
		assertEquals(result,"2x+x^3+1.25x^4-x^7"); 
		nrTesteCuSucces++;			
	}
}
