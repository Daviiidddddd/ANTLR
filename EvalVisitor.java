import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Double> {

    // memoria para variables
    Map<String, Double> memoria = new HashMap<>();
    // modo: false -> radianes (por defecto), true -> grados
    boolean modoGrados = false;

    /** ID '=' expr NEWLINE */
    @Override
    public Double visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double valor = visit(ctx.expr());
        memoria.put(id, valor);
        return valor;
    }

    /** 'mode' ID NEWLINE */
    @Override
    public Double visitSetMode(LabeledExprParser.SetModeContext ctx) {
        String m = ctx.ID().getText().toLowerCase();
        if (m.equals("deg") || m.equals("degree") || m.equals("degrees")) modoGrados = true;
        else modoGrados = false;
        System.out.println("modo = " + (modoGrados ? "deg" : "rad"));
        return 0.0;
    }

    /** expr NEWLINE */
    @Override
    public Double visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Double valor = visit(ctx.expr());
        System.out.println(valor);
        return 0.0;
    }

    /** NUMBER */
    @Override
    public Double visitNumber(LabeledExprParser.NumberContext ctx) {
        return Double.valueOf(ctx.getText());
    }

    /** ID */
    @Override
    public Double visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memoria.containsKey(id)) return memoria.get(id);
        return 0.0;
    }

    /** expr op=('*'|'/') expr */
    @Override
    public Double visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        double izq = visit(ctx.expr(0));
        double der = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.MUL) return izq * der;
        return izq / der;
    }

    /** expr op=('+'|'-') expr */
    @Override
    public Double visitAddSub(LabeledExprParser.AddSubContext ctx) {
        double izq = visit(ctx.expr(0));
        double der = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.ADD) return izq + der;
        return izq - der;
    }

    /** expr '^' expr */
    @Override
    public Double visitPow(LabeledExprParser.PowContext ctx) {
        double base = visit(ctx.expr(0));
        double exp = visit(ctx.expr(1));
        return Math.pow(base, exp);
    }

    /** expr '!' (factorial, postfix) */
    @Override
    public Double visitFactorial(LabeledExprParser.FactorialContext ctx) {
        double v = visit(ctx.expr());
        if (v < 0) {
            throw new RuntimeException("factorial indefinido para numeros negativos");
        }
        long n = (long) Math.floor(v + 1e-12);
        double resultado = 1.0;
        for (long i = 2; i <= n; i++) resultado *= i;
        return resultado;
    }

    /** FUNC '(' expr (UNIT)? ')' */
    @Override
    public Double visitFuncCall(LabeledExprParser.FuncCallContext ctx) {
        String func = ctx.FUNC().getText().toLowerCase();
        double arg = visit(ctx.expr());
        boolean usarGrados = modoGrados;
        if (ctx.UNIT() != null) {
            String u = ctx.UNIT().getText().toLowerCase();
            usarGrados = u.equals("deg");
        }

        switch (func) {
            case "sin":
                if (usarGrados) arg = Math.toRadians(arg);
                return Math.sin(arg);
            case "cos":
                if (usarGrados) arg = Math.toRadians(arg);
                return Math.cos(arg);
            case "tan":
                if (usarGrados) arg = Math.toRadians(arg);
                return Math.tan(arg);
            case "sqrt":
                if (arg < 0) throw new RuntimeException("sqrt: dominio invalido");
                return Math.sqrt(arg);
            case "ln":
                if (arg <= 0) throw new RuntimeException("ln: dominio invalido");
                return Math.log(arg);
            case "log":
                if (arg <= 0) throw new RuntimeException("log: dominio invalido");
                return Math.log10(arg);
            default:
                throw new RuntimeException("funcion desconocida: " + func);
        }
    }

    /** '(' expr ')' */
    @Override
    public Double visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}
