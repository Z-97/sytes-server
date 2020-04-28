package org.sytes.common;
/**
 * 符号常量
 * @author wang
 *
 */
public enum Symbol {
	LBRACE("{"), 
	RBRACE("}"), 
	LBRACKET("["), 
	RBRACKET("]"), 
	COMMA(","), 
	COLON(":"),
	;

	public final String val;

	Symbol(String val) {
		this.val = val;
	}
}
