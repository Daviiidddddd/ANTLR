# calc_py.py
import sys
from antlr4 import FileStream, CommonTokenStream, InputStream
from LabeledExprLexer import LabeledExprLexer
from LabeledExprParser import LabeledExprParser
from eval_visitor import EvalVisitor

def run_archivo(nombre=None):
    if nombre:
        data = FileStream(nombre, encoding='utf-8')
    else:
        data = InputStream(sys.stdin.read())
    lexer = LabeledExprLexer(data)
    tokens = CommonTokenStream(lexer)
    parser = LabeledExprParser(tokens)
    tree = parser.prog()
    v = EvalVisitor()
    v.visit(tree)

if __name__ == '__main__':
    run_archivo(sys.argv[1] if len(sys.argv)>1 else None)
