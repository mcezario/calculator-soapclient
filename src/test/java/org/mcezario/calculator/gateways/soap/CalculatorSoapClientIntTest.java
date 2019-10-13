package org.mcezario.calculator.gateways.soap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mcezario.calculator.config.SoapClientConfigurationCalculatorSoapClient;
import org.mcezario.calculator.gateways.CalculatorException;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.springframework.ws.test.client.RequestMatchers.anything;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

@RunWith( SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SoapClientConfigurationCalculatorSoapClient.class })
@TestPropertySource(properties = {
        "soap.calculator.host=http://host",
        "soap.calculator.timeout=1000",
        "soap.calculator.pool.maxTotal=20",
        "soap.calculator.pool.defaultMaxPerRoute=16"
})
public class CalculatorSoapClientIntTest {

    @Autowired
    private CalculatorSoapClient soapClient;

    private MockWebServiceServer mockServer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockServer = MockWebServiceServer.createServer(soapClient);
    }

    @Test
    public void shouldReturnSuccessfully() throws IOException {

        // Prepare
        final Source requestPayload = new StringSource(""
                + "<cal:add xmlns:cal=\"http://service.operations.com/calculator\">\n" +
                "         <cal:n1>1</cal:n1>\n" +
                "         <cal:n2>2</cal:n2>\n" +
                "      </cal:add>");

        final Source responsePayload = new StringSource(""
                + "<cal:addResponse xmlns:cal=\"http://service.operations.com/calculator\">\n" +
                "         <cal:return>3</cal:return>\n" +
                "      </cal:addResponse>");

        mockServer
                .expect(payload(requestPayload))
                .andRespond(withPayload(responsePayload));

        // Given
        final int value1 = 1;
        final int value2 = 2;

        // When
        final int result = soapClient.add(value1, value2);

        // Then
        mockServer.verify();

        assertEquals(3, result);
    }

    @Test(expected = CalculatorException.class)
    public void shouldReturnSoapFault() {

        //Prepare
        final Source responsePayload = new StringSource(""
                + "<S:Fault xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "   <faultcode>server</faultcode>\n"
                + "   <faultstring>ERROR</faultstring>\n"
                + "</S:Fault>");

        mockServer //
                .expect(anything()) //
                .andRespond(withPayload(responsePayload));

        // Given
        final int value1 = 1;
        final int value2 = 2;

        try {
            // When
            soapClient.add(value1, value2);
        } catch (final CalculatorException e) {

            // Then
            assertEquals("Error to calculate the SUM operation.", e.getMessage());

            mockServer.verify();

            throw e;
        }

    }

}
