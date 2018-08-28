/* Generated By:JavaCC: Do not edit this line. JavaParserConstants.java */
package org.zaval.tools.i18n.translator;

public interface JavaParserConstants {

  int EOF = 0;
  int SINGLE_LINE_COMMENT = 9;
  int FORMAL_COMMENT = 10;
  int MULTI_LINE_COMMENT = 11;
  int ABSTRACT = 13;
  int BOOLEAN = 14;
  int BREAK = 15;
  int BYTE = 16;
  int CASE = 17;
  int CATCH = 18;
  int CHAR = 19;
  int CLASS = 20;
  int CONST = 21;
  int CONTINUE = 22;
  int _DEFAULT = 23;
  int DO = 24;
  int DOUBLE = 25;
  int ELSE = 26;
  int EXTENDS = 27;
  int FALSE = 28;
  int FINAL = 29;
  int FINALLY = 30;
  int FLOAT = 31;
  int FOR = 32;
  int GOTO = 33;
  int IF = 34;
  int IMPLEMENTS = 35;
  int IMPORT = 36;
  int INSTANCEOF = 37;
  int INT = 38;
  int INTERFACE = 39;
  int LONG = 40;
  int NATIVE = 41;
  int NEW = 42;
  int NULL = 43;
  int PACKAGE = 44;
  int PRIVATE = 45;
  int PROTECTED = 46;
  int PUBLIC = 47;
  int RETURN = 48;
  int SHORT = 49;
  int STATIC = 50;
  int SUPER = 51;
  int SWITCH = 52;
  int SYNCHRONIZED = 53;
  int THIS = 54;
  int THROW = 55;
  int THROWS = 56;
  int TRANSIENT = 57;
  int TRUE = 58;
  int TRY = 59;
  int VOID = 60;
  int VOLATILE = 61;
  int WHILE = 62;
  int STRICTFP = 63;
  int INTEGER_LITERAL = 64;
  int DECIMAL_LITERAL = 65;
  int HEX_LITERAL = 66;
  int OCTAL_LITERAL = 67;
  int FLOATING_POINT_LITERAL = 68;
  int EXPONENT = 69;
  int CHARACTER_LITERAL = 70;
  int STRING_LITERAL = 71;
  int IDENTIFIER = 72;
  int LETTER = 73;
  int DIGIT = 74;
  int LPAREN = 75;
  int RPAREN = 76;
  int LBRACE = 77;
  int RBRACE = 78;
  int LBRACKET = 79;
  int RBRACKET = 80;
  int SEMICOLON = 81;
  int COMMA = 82;
  int DOT = 83;
  int ASSIGN = 84;
  int GT = 85;
  int LT = 86;
  int BANG = 87;
  int TILDE = 88;
  int HOOK = 89;
  int COLON = 90;
  int EQ = 91;
  int LE = 92;
  int GE = 93;
  int NE = 94;
  int SC_OR = 95;
  int SC_AND = 96;
  int INCR = 97;
  int DECR = 98;
  int PLUS = 99;
  int MINUS = 100;
  int STAR = 101;
  int SLASH = 102;
  int BIT_AND = 103;
  int BIT_OR = 104;
  int XOR = 105;
  int REM = 106;
  int LSHIFT = 107;
  int RSIGNEDSHIFT = 108;
  int RUNSIGNEDSHIFT = 109;
  int PLUSASSIGN = 110;
  int MINUSASSIGN = 111;
  int STARASSIGN = 112;
  int SLASHASSIGN = 113;
  int ANDASSIGN = 114;
  int ORASSIGN = 115;
  int XORASSIGN = 116;
  int REMASSIGN = 117;
  int LSHIFTASSIGN = 118;
  int RSIGNEDSHIFTASSIGN = 119;
  int RUNSIGNEDSHIFTASSIGN = 120;

  int DEFAULT = 0;
  int IN_SINGLE_LINE_COMMENT = 1;
  int IN_FORMAL_COMMENT = 2;
  int IN_MULTI_LINE_COMMENT = 3;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"//\"",
    "<token of kind 7>",
    "\"/*\"",
    "<SINGLE_LINE_COMMENT>",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 12>",
    "\"abstract\"",
    "\"boolean\"",
    "\"break\"",
    "\"byte\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"class\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"float\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"int\"",
    "\"interface\"",
    "\"long\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"short\"",
    "\"static\"",
    "\"super\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"this\"",
    "\"throw\"",
    "\"throws\"",
    "\"transient\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "\"strictfp\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\">\"",
    "\"<\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\">>\"",
    "\">>>\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
  };

}
