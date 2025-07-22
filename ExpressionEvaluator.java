import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionEvaluator {

    public double evaluate(String expression) {
        try {
            expression = expression(expression);
            Expression exp = new ExpressionBuilder(expression).build();
            return exp.evaluate();
        } catch (Exception e) {
            throw new RuntimeException("Error evaluating expression: " + e.getMessage());
        }
    }

    private String expression(String expression) {
        while (expression.contains("root(")) {
            int start = expression.indexOf("root(");
            int end = findClosingParenthesis(expression, start + 4);
            String inside = expression.substring(start + 5, end);
            String[] parts = inside.split(",");
            String degree = parts[0].trim();
            String value = parts[1].trim();
            String replacement = "(" + value + "^(1.0/" + degree + "))";
            expression = expression.substring(0, start) + replacement + expression.substring(end + 1);
        }

        while (expression.contains("log(")) {
            int start = expression.indexOf("log(");
            int end = findClosingParenthesis(expression, start + 3);
            String inside = expression.substring(start + 4, end);
            String[] parts = inside.split(",");
            String base = parts[0].trim();
            String value = parts[1].trim();
            String replacement = "(log(" + value + ")/log(" + base + "))";
            expression = expression.substring(0, start) + replacement + expression.substring(end + 1);
        }

        return expression;
    }

    private int findClosingParenthesis(String str, int startIndex) {
        int open = 1;
        for (int i = startIndex + 1; i < str.length(); i++) {
            if (str.charAt(i) == '(') open++;
            else if (str.charAt(i) == ')') open--;
            if (open == 0) return i;
        }
        throw new IllegalArgumentException("No matching closing parenthesis found.");
    }
}
