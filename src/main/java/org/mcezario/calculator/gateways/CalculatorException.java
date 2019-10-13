package org.mcezario.calculator.gateways;

public class CalculatorException extends RuntimeException {

    private CalculatorException(final String message) {
        super(message);
    }

    public static CalculatorException inSumOperation() {
        return new CalculatorException("Error to calculate the SUM operation.");
    }

}
