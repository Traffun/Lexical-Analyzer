package assignment2and3;
import java.util.*;
import java.io.*;
public class LexicaalAnalyzer {
	/*Global Declarations*/
	/*Variables*/
	static int charClass;
	static StringBuilder lexeme= new StringBuilder();
	static char nextChar;
	static int lexLen=0;
	int token;
	static int nextToken;
	static String line;
	static Scanner strScan;
	
	/*Character Classes*/
	static final int LETTER=0;
	static final int DIGIT=1;
	static final int UNKNOWN=99;
	
	/* Token Codes */
	static final int INT_LIT=10;
	static final int IDENT=11;
	static final int ASSIGN_OP=20;
	static final int ADD_OP=21;
	static final int SUB_OP=22;
	static final int MULT_OP=23;
	static final int DIV_OP=24;
	static final int LEFT_PAREN=25;
	static final int RIGHT_PAREN=26;
	static final int EOF=-1;
	public static void main(String[] args) {
		try {
			File file= new File("front.in");
			Scanner fileScan= new Scanner(file);
			do {
				line=fileScan.nextLine();
				line=line.replaceAll("\\s", "");
				strScan= new Scanner(line);
				getChar();
				while(charClass !=EOF) {
					lex();
				}
				System.out.print("Next token is: "+EOF+" Next lexeme is EOF \n");
				lexLen=0;
			}while(fileScan.hasNextLine());
		
		}catch(Exception e) {
			System.out.println("ERROR!");
		}
	}

	/*A method that determines the character class of the Token*/
	static void lookup(char ch) {
		switch(ch) {
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		default:
			addChar();
			nextToken = EOF;
			break;
		}
	}
	/*A method that adds the next Token to the Lexeme*/
	private static void addChar() {
		lexeme.append(nextChar);
	}
	
	/*A method that gets the next Token on the line*/
	static void getChar() {
		if (strScan.hasNext()) {
			nextChar=line.charAt(lexLen++);
			if (Character.isLetter(nextChar)) {
				charClass = LETTER;
			}else if (Character.isDigit(nextChar)) {
					charClass = DIGIT;
			}else charClass = UNKNOWN;
		
		}else {
			charClass = EOF;
		}
	}
	/*A method that acts a lexical analyzer and prints the token and lexeme of the character*/
	private static int lex() {
		// TODO Auto-generated method stub
		//lexLen=0;
		switch(charClass) {
			case LETTER:
				addChar();
				getChar();
				while((charClass == LETTER || charClass == DIGIT)) {
					if(lexLen==line.length()) {
						addChar();
						charClass=EOF;
						break;
					}else {
					addChar();
					getChar();
					}
				}
				nextToken = IDENT;
				break;
			case DIGIT:
				addChar();
				getChar();
				while(charClass == DIGIT) {
					if(lexLen==line.length()) {
						addChar();
						charClass=EOF;
						break;
					}else {
					addChar();
					getChar();
					}
				}
				nextToken = INT_LIT;
				break;
			case UNKNOWN:
				lookup(nextChar);
				if(lexLen==line.length()) {
					charClass=EOF;
					break;
				}else {
					getChar();
					break;	
				}
			case EOF:
				nextToken = EOF;
				/*lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = 0;*/
				break;
		}
		System.out.print("Next token is: "+nextToken+" Next lexeme is  "+lexeme.toString()+"\n");
		lexeme= new StringBuilder();
		return 0;
		
	}

}
