import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionEvaluator {

    public double evaluate(String expression) {
        try {
            // دعم root(n,x) ← تحويل إلى (x^(1.0/n))
            expression = expression.replaceAll("root\\(([^,]+),([^\\)]+)\\)", "($2^(1.0/$1))");

            // دعم log(base,x) ← تحويل إلى log(x)/log(base)
            // ملاحظة: exp4j تدعم log(x) ← وهي log الطبيعي (ln)
            expression = expression.replaceAll("log\\(([^,]+),([^\\)]+)\\)", "(log($2)/log($1))");

            Expression exp = new ExpressionBuilder(expression)
                    .variables("x")
                    .build()
                    .setVariable("x", 0); // استخدم القيمة الافتراضية لـ x إذا كانت موجودة في التعبير

            return exp.evaluate();

        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression: " + e.getMessage());
        }
    }
}
