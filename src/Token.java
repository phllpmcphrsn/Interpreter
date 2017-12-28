/*****************************************************
 * Token Class provides a means of token creation for
 * analysis (i.e. lexical, syntactic). A token consists
 * of three parts: symbol, class, and value. A token's 
 * value should be realized after it is retrieved by the
 * get_token() method. 
 * 
 * @author Phillip McPherson
 */
public class Token {
	public static final int LPAREN = 0;
	public static final int RPAREN = 1;
	public static final int TIMES = 2;
	public static final int DIVIDE = 3;
	public static final int ADD = 4;
	public static final int SUB = 5;
	public static final int NUM = 6;
	
	private String symbol;    	//Original state of token
	private TokenClass tc;		//Enumerated class value
	private double value;		//Actual value of a given number
	
	public Token() { }
	
	public Token(TokenClass tc, String symbol) {
		this.tc = tc;
		this.symbol = symbol;
	}
	
	public Token(TokenClass tc, String symbol, double value) {
		this.tc = tc;
		this.symbol = symbol;
		this.value = value;
	}
	
	/*******************************************************
	 * Used to to see if tokens were created properly
	 *******************************************************/
	@Override
	public String toString() {
		return "symbol: \"" + getSymbol() + "\", class: " + getTokenClass()+ ", value: " + getValue();
	}
	
	public double getValue() {
		return value;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public TokenClass getTokenClass() {
		return tc;
	}

	public void setToken(TokenClass tc, String symbol, double value) {
		// TODO Auto-generated method stub
		this.tc = tc;
		this.symbol = symbol;
		this.value = value;
	}
}
