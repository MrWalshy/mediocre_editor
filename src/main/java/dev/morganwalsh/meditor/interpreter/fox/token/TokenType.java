package dev.morganwalsh.meditor.interpreter.fox.token;

public enum TokenType {
	// Single-character tokens.
	LEFT_PAREN, RIGHT_PAREN, COMMA, MINUS, PLUS, SLASH, STAR, QUESTION_MARK, COLON, DOT, LEFT_CURLY, RIGHT_CURLY,
	APOSTROPHE, LEFT_BRACKET, RIGHT_BRACKET, SEMICOLON, PIPE, UNDERSCORE,

	// One or two character tokens.
	BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, TERNARY, ARROW, FAT_ARROW,

	// Literals.
	IDENTIFIER, STRING, NUMBER,

	// Keywords.
	AND, FALSE, DEFUN, WHILE, NULL, OR, TRUE, VAR, IF, ASSIGN, IMPORT, MATCH, WHERE, BREAK,

	EOF
}
