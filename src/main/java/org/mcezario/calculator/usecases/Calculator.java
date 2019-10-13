package org.mcezario.calculator.usecases;

import lombok.extern.slf4j.Slf4j;
import org.mcezario.calculator.gateways.CalculatorApiGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Calculator {

    @Autowired
    private CalculatorApiGateway gateway;

    public int sum(final int value1, final int value2) {
        return gateway.sum(value1, value2);
    }

}
