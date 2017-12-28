/*************************************************************
 * Scans the input, character by character, until the collection
 * formed matches a terminal symbol of the target language. Only
 * performs its analysis once called upon (with get_token()).
 * 
 * LA(G)
 * LA(V) = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, (, ), +, -, *, /}
 * LA(C) = {symbol, number}
 * LA(S) = symbol
 * LA(P) = {
 * 	symbol ::= +|-|*|/|(|)|number
 * 	number ::= {0|1|...|9}+[.]{0|1|...|9}* | {0|1|...|9}*[.]{0|1|...|9}+
 * 
 * @author Phillip McPherson
 *
 */

public class LexicalAnalyzer {
	private String expression;
	int i = 0;
	/**********************************************
	 * Lexical Analyzer constructor
	 *  
	 * @param expression Input string
	 *********************************************/
	public LexicalAnalyzer(String expression) {
		this.expression = expression;
	}
	
	/**********************************************
	 * Method to retrieve tokens from input string. 
	 * To be used for syntax analyzing.
	 * 
	 * @return Token token object
	 *********************************************/
	public boolean get_token(Token token) {
		if(i == expression.length())
			return false;
		
		/**If any whitespace is encountered, count then move along**/
		while(Character.isWhitespace(expression.charAt(i))) {
			i++;
		}
		
		char currChar = expression.charAt(i);
		switch(currChar) {
		case '(':
			i++;
			token.setToken(TokenClass.PAREN, Character.toString(currChar), Token.LPAREN);
			break;
		case ')':
			i++;
			token.setToken(TokenClass.PAREN, Character.toString(currChar), Token.RPAREN);
			break;
		case '/':
			i++;
			token.setToken(TokenClass.OPERATOR, Character.toString(currChar), Token.DIVIDE);
			break;
		case '*':
			i++;
			token.setToken(TokenClass.OPERATOR, Character.toString(currChar), Token.TIMES);
			break;
		case '+':
			i++;
			token.setToken(TokenClass.OPERATOR, Character.toString(currChar), Token.ADD);
			break;
		case '-':
			i++;
			token.setToken(TokenClass.OPERATOR, Character.toString(currChar), Token.SUB);
			break;
		default:
			int currPos = i;
			String real;

			while(currPos < expression.length() && 
					(Character.isDigit(expression.charAt(currPos)) || expression.charAt(currPos) == '.')) {
				currPos++;
			}

			/**check if there's a letter in the token and report position & letter**/
			if(currPos < expression.length() && Character.isLetter(expression.charAt(currPos))) {
				System.out.println("Lexical error at this location: "+currPos +". The offending"
						+ " character: " + expression.charAt(currPos));
				break;
			}

			real = expression.substring(i, currPos);
			i += real.length();		//Update counter

			if(scan_number(real)) 
				token.setToken(TokenClass.NUMBER, real, Double.parseDouble(real)); //Built-in function for conversion
			break;
		}
		return true;
	}
	
	/******************************************************
	 * Confirms the format of a number is correct
	 * 
	 * @param str the number in its original string form
	 * @return true if it's a real number
	 ******************************************************/
	private boolean scan_number(String str) {
	    int initial = 0;
	    int lookahead = 0;
	    
	    if(Character.isDigit(str.charAt(initial))) {
	      
	      while(Character.isDigit(str.charAt(lookahead)) && hasMoreChars(str, lookahead))
	    	  lookahead++;
	      
	      if (str.charAt(lookahead) == '.'){
	        lookahead++;
	        while (Character.isDigit(str.charAt(lookahead++)) && hasMoreChars(str, lookahead) )
	        	;
	      }
	      return true;
	    }
	    
	    else if (str.charAt(initial) == '.'){
	    	
	      if (Character.isDigit(str.charAt(++lookahead))) {
	    	  
	        while (Character.isDigit(str.charAt(lookahead)) && hasMoreChars(str, lookahead))
	          lookahead++;
	      }
	      return true;  
	    }
	    
	    else
	      return false;
	  }
	
	/****************************************************
	 * Checks if the end of the string has been reached.
	 * Mainly used in scan_number.
	 * 
	 * @param str Input string
	 * @param pos Current character position in the string
	 * @return true if position < string's length
	 ****************************************************/
	private boolean hasMoreChars(String str, int pos) {
		return pos < str.length() - 1;
	}
}
