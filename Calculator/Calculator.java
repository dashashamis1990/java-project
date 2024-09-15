import java.util.Scanner;
import java.util.logging.Logger;

public class Calculator {
    private static final Logger logger = Logger.getLogger(Calculator.class.getName());

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double num1, num2, result;
        char operator;

        logger.info("Enter the first number: ");
        num1 = input.nextDouble();

        logger.info("Enter the second number: ");
        num2 = input.nextDouble();

        logger.info("Enter an operator (+, -, *, /): ");
        operator = input.next().charAt(0);

        switch (operator) {
            case '+':
                result = num1 + num2;
                logger.info(num1 + " + " + num2 + " = " + result);
                break;
            case '-':
                result = num1 - num2;
                logger.info(num1 + " - " + num2 + " = " + result);
                break;
            case '*':
                result = num1 * num2;
                logger.info(num1 + " * " + num2 + " = " + result);
                break;
            case '/':
                if (num2 == 0) {
                    logger.info("Error: Division by zero");
                } else {
                    result = num1 / num2;
                    logger.info(num1 + " / " + num2 + " = " + result);
                }
                break;
            default:
                logger.info("Invalid operator");
                break;
        }
    }
}
