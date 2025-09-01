grammar LabeledExpr;

// programa
prog: stat+ ;

// sentencias
stat
    : expr NEWLINE                 # printExpr
    | ID '=' expr NEWLINE          # assign
    | 'mode' ID NEWLINE            # setMode
    | NEWLINE                      # blank
    ;

// expresiones
expr
    : expr op=('*'|'/') expr       # MulDiv
    | expr op=('+'|'-') expr       # AddSub
    | expr '^' expr                # Pow
    | expr '!'                     # Factorial
    | FUNC '(' expr (UNIT)? ')'    # FuncCall
    | NUMBER                       # number
    | ID                           # id
    | '(' expr ')'                 # parens
    ;

// tokens
FUNC: 'sin' | 'cos' | 'tan' | 'sqrt' | 'ln' | 'log' ;
UNIT: 'deg' | 'rad' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

ID  : [a-zA-Z]+ ;
NUMBER
    : [0-9]+'.'[0-9]*    // 123. or 123.45
    | '.'[0-9]+          // .5
    | [0-9]+             // integer part
    ;

NEWLINE: '\r'? '\n' ;
WS : [ \t]+ -> skip ;
