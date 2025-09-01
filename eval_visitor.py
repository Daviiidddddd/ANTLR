# eval_visitor.py
from LabeledExprParser import LabeledExprParser
from LabeledExprVisitor import LabeledExprBaseVisitor
import math

class EvalVisitor(LabeledExprBaseVisitor):
    def __init__(self):
        self.memoria = {}
        self.modoGrados = False

    # ID '=' expr NEWLINE
    def visitAssign(self, ctx:LabeledExprParser.AssignContext):
        idname = ctx.ID().getText()
        val = self.visit(ctx.expr())
        self.memoria[idname] = val
        return val

    # 'mode' ID NEWLINE
    def visitSetMode(self, ctx:LabeledExprParser.SetModeContext):
        m = ctx.ID().getText().lower()
        self.modoGrados = (m == 'deg')
        print("modo = " + ("deg" if self.modoGrados else "rad"))
        return 0.0

    # expr NEWLINE
    def visitPrintExpr(self, ctx:LabeledExprParser.PrintExprContext):
        val = self.visit(ctx.expr())
        print(val)
        return 0.0

    def visitNumber(self, ctx:LabeledExprParser.NumberContext):
        return float(ctx.getText())

    def visitId(self, ctx:LabeledExprParser.IdContext):
        idname = ctx.ID().getText()
        return self.memoria.get(idname, 0.0)

    def visitMulDiv(self, ctx:LabeledExprParser.MulDivContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        if ctx.op.type == LabeledExprParser.MUL:
            return left * right
        else:
            return left / right

    def visitAddSub(self, ctx:LabeledExprParser.AddSubContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        if ctx.op.type == LabeledExprParser.ADD:
            return left + right
        else:
            return left - right

    def visitPow(self, ctx:LabeledExprParser.PowContext):
        base = self.visit(ctx.expr(0))
        exp = self.visit(ctx.expr(1))
        return math.pow(base, exp)

    def visitFactorial(self, ctx:LabeledExprParser.FactorialContext):
        v = self.visit(ctx.expr())
        if v < 0:
            raise Exception("factorial: numero negativo")
        n = int(math.floor(v + 1e-12))
        res = 1.0
        for i in range(2, n+1):
            res *= i
        return res

    def visitFuncCall(self, ctx:LabeledExprParser.FuncCallContext):
        func = ctx.FUNC().getText().lower()
        arg = self.visit(ctx.expr())
        usarGrados = self.modoGrados
        if ctx.UNIT() is not None:
            u = ctx.UNIT().getText().lower()
            usarGrados = (u == 'deg')
        if func == 'sin':
            if usarGrados: arg = math.radians(arg)
            return math.sin(arg)
        if func == 'cos':
            if usarGrados: arg = math.radians(arg)
            return math.cos(arg)
        if func == 'tan':
            if usarGrados: arg = math.radians(arg)
            return math.tan(arg)
        if func == 'sqrt':
            if arg < 0: raise Exception("sqrt: dominio invalido")
            return math.sqrt(arg)
        if func == 'ln':
            if arg <= 0: raise Exception("ln: dominio invalido")
            return math.log(arg)
        if func == 'log':
            if arg <= 0: raise Exception("log: dominio invalido")
            return math.log10(arg)
        raise Exception("funcion desconocida " + func)

    def visitParens(self, ctx:LabeledExprParser.ParensContext):
        return self.visit(ctx.expr())
