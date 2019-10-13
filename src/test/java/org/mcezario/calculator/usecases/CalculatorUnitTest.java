package org.mcezario.calculator.usecases;

import org.junit.Before;
import org.junit.Test;
import org.mcezario.calculator.gateways.CalculatorApiGateway;
import org.mcezario.calculator.gateways.CalculatorException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CalculatorUnitTest {

    @InjectMocks
    private final Calculator calculator = new Calculator();

    @Mock
    private CalculatorApiGateway gateway;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldEvaluateTheOperationSuccessfully() {
        // Given
        final int value1 = 1;
        final int value2 = 2;

        // Prepare
        when(gateway.sum(value1, value2)).thenReturn(3);

        // When
        int result = calculator.sum(value1, value2);

        // Then
        assertEquals(3, result);
        verify(gateway, VerificationModeFactory.times(1)).sum(value1, value2);
    }

    @Test(expected = CalculatorException.class)
    public void shouldGetExceptionWhenEvaluateTheOperation() {
        // Given
        final int value1 = 1;
        final int value2 = 2;

        // Prepare
        when(gateway.sum(value1, value2)).thenThrow(CalculatorException.inSumOperation());

        // When
        try {
            calculator.sum(value1, value2);
        } catch (final CalculatorException e) {

            // Then
            assertEquals("Error to calculate the SUM operation.", e.getMessage());

            verify(gateway, VerificationModeFactory.times(1)).sum(value1, value2);

            throw e;
        }
    }

}
