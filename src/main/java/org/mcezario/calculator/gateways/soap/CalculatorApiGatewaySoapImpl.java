package org.mcezario.calculator.gateways.soap;

import lombok.extern.slf4j.Slf4j;
import org.mcezario.calculator.gateways.CalculatorApiGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculatorApiGatewaySoapImpl implements CalculatorApiGateway {

    @Autowired
    private CalculatorSoapClient calculatorSoapClient;

    @Override
    public int sum(final int value1, final int value2) {
        log.debug("Sending values {} and {} to calculate the SUM between them.", value1, value2);

        return calculatorSoapClient.add(value1, value2);
    }

}
