import java.util.StringTokenizer;

import static com.google.common.base.Preconditions.checkNotNull;

public class Evaluate {
    private final String exp;
    private final double result;
    
    public Evaluate(String exp) {
        checkNotNull(exp);
        this.exp = exp;
        StringTokenizer token = new StringTokenizer(exp);
        Stack<Double> operands = new ArrayStack<>();
        Stack<String> operators = new ArrayStack<>();
        while (token.hasMoreTokens()) {
            String e = token.nextToken().trim();
            if ("(".equals(e)) continue;
            else if (")".equals(e)) {
                double y = operands.pop();
                String op = operators.pop();
                double z = calc(operands.pop(), op, y);
                operands.push(z);
            }
            else if (isDigit(e)) operands.push(Double.parseDouble(e));
            else operators.push(e);
        }
        this.result = operands.pop();
    }

    private boolean isDigit(String e) {
        try {
            Double.parseDouble(e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private double calc(double x, String op, double y) {
        if ("+".equals(op)) return x + y;
        if ("-".equals(op)) return x - y;
        if ("*".equals(op)) return x * y;
        if ("/".equals(op)) return x / y;
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        String exp = "( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )";
        Evaluate ev = new Evaluate(exp);
        System.out.println(ev.result);
    }
}
