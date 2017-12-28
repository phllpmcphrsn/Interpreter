/********************************************************************************
 * A syntactic analyzer of math expressions. A given expression is sent to the
 * lexical analyzer which, then, breaks the expression in to tokens for
 * analysis. The purpose of a syntax analyzer is to ensure the elements of a
 * language are structured to form grammatically correct sentences (or
 * expressions, in the case of a BNF).
 * 
 * LS(G) 
 * LS(V) = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, (, ), +, -, *, /}
 * LS(C) = {expr, term, factor}
 * LS(S) = factor
 * LS(P) = {
 * 	expr   ::= term[{+|-}term]*
 * 	term   ::= factor[{*|/}factor]*
 * 	factor ::= (expr) | number
 * }
 * 
 * 
 * @author Phillip McPherson
 *
 ********************************************************************************/
public class SyntaxAnalyzer {
	private LexicalAnalyzer lex;
	private Token token;
	private int paren_count = 0;
	
	public SyntaxAnalyzer() {	}
	
	public void evaluate(String expression) {
		lex = new LexicalAnalyzer(expression);
		token = new Token();
		lex.get_token(token);
		expression(token);
	}
	
	/**** expr ::= term[{+|-}term]* ****/
	private boolean expression(Token token) {
		System.out.println(token);
		
		if(!term(token)) {   //term
			return false;
		}
		
		while( (token.getTokenClass() == TokenClass.OPERATOR) &&  // {+|-}
				((token.getValue() == Token.ADD) || (token.getValue() == Token.SUB)) ) { 

			if((lex.get_token(token)) == false)     //have LS(V) consume token
				return false;

			System.out.println(token);

			if(!term(token))       //term
				return false;
		}
		
		return true;
	}
	
	/******* term ::= factor[{*|/}factor]* *******/
	private boolean term(Token token) {
		if(!factor(token)) {   //factor
			return false;
		}
		
		while( (token.getTokenClass() == TokenClass.OPERATOR) &&  //{*|/}
				((token.getValue() == Token.TIMES) || (token.getValue() == Token.DIVIDE)) ) {

			if((lex.get_token(token)) == false)     //have LS(V) consume token
				return false;

			System.out.println(token);

			if(!factor(token))		//factor
				return false;
		}
		return true;
	}
	
	/******* factor ::= (expr) | number *******/
	private boolean factor(Token token) {
		if ((token.getTokenClass() == TokenClass.PAREN) && 
				(token.getValue() == Token.LPAREN)) {		// (
						
			if ((lex.get_token(token)) == false)		//have LS(V) consume token
				return false;
			
			if(!expression(token))		//expr
				return false;
			
			if(	((token.getTokenClass() != TokenClass.PAREN) || (token.getValue() != Token.RPAREN)) )
				return syntax_error("Mismatched parantheses");
		}
		
		else if(token.getTokenClass() != TokenClass.NUMBER)
			return syntax_error("Please double-check input.");
		
 		if((lex.get_token(token)) == false)		//have LS(V) consume token
			return false;
 		
		System.out.println(token);
		
		return true;
	}
	
	private boolean syntax_error(String message) {
		System.out.println(message);
		return false;
	}
}
