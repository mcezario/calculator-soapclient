package org.mcezario.calculator.gateways.soap;

import com.operations.service.calculator.Add;
import com.operations.service.calculator.AddResponse;
import lombok.extern.slf4j.Slf4j;
import org.mcezario.calculator.gateways.CalculatorException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class CalculatorSoapClient extends WebServiceGatewaySupport {

    public int add(final int value1, final int value2) {

        try {
            final Add add = new Add();
            add.setN1(value1);
            add.setN2(value2);

            log.debug("Calling CalculatorSoapClient.", value1, value2);
            final LocalDateTime execStart = LocalDateTime.now();

            final AddResponse response = (AddResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(add);

            log.debug("Integration done in {} milliseconds.", Duration.between(execStart, LocalDateTime.now()).toMillis());

            return response.getReturn();

        } catch (final Exception e) {

            log.error("Error to calculate the SUM operation between {} and {}.", value1, value2, e);
        }

        throw CalculatorException.inSumOperation();
    }


}
