import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;



public class ExpressionEvaluator {

    public double evaluate(String expression) {
        try {
            // تحويل root(n,x) إلى x^(1/n)
            expression = expression.replaceAll("root\\((\\d+),(\\d+)\\)", "($2^(1.0/$1))");

            // تحويل log(base,x) إلى log(x)/log(base)
            expression = expression.replaceAll("log\\(([^,]+),([^\\)]+)\\)", "(log($2)/log($1))");

            Expression exp = new ExpressionBuilder(expression).build();
            return exp.evaluate();

        } catch (Exception e) {
            throw new RuntimeException("خطأ في التقييم: " + e.getMessage());
        }
    }
}
