
public class TestDriver {

	public static void main(String[] args) {
		SyntaxAnalyzer sa1 = new SyntaxAnalyzer();
		System.out.println("First test");
		sa1.evaluate("3.14+6/1.87*0.35");
		System.out.println("\nSecond test");
		sa1.evaluate("5*(35-22) + (17+5)/11");
		
		System.out.println("\nError test 1");
		sa1.evaluate("3.14++6/1.87*0.35");
		
		/**
		 * Parentheses test doesn't work. Debugging tests show that the 
		 * last call to expression() returns false; thus, ending the program
		 * before the final parenthetical check occurs.
		 */
		System.out.println("\nError test 2");    
		sa1.evaluate("3+(4-4");		
		
	}

}
