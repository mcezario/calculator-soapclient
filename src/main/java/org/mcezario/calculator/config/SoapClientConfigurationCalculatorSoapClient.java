package org.mcezario.calculator.config;

import com.operations.service.calculator.CalculatorPortType;
import org.apache.commons.lang3.ClassUtils;
import org.mcezario.calculator.gateways.soap.CalculatorSoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;

@Configuration
public class SoapClientConfigurationCalculatorSoapClient extends SoapClientConfiguration {

    public SoapClientConfigurationCalculatorSoapClient(
            @Value("${soap.calculator.host}") final String host,
            @Value("${soap.calculator.timeout}") final int timeout,
            @Value("${soap.calculator.pool.maxTotal}") final int maxTotal,
            @Value("${soap.calculator.pool.defaultMaxPerRoute}") final int defaultMaxPerRoute) {
        super(host, timeout, maxTotal, defaultMaxPerRoute);
    }

    @Bean("soapClient")
    public CalculatorSoapClient calculatorSoapClient() {
        return newClientInstance(getHost());
    }

    @Bean
    public Jaxb2Marshaller calculatorMarshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(ClassUtils.getPackageName(CalculatorPortType.class));

        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory calculatorMessageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);

        return messageFactory;
    }

    @Bean
    public WebServiceMessageSender webServiceMessageSender() {
        return createWebServiceMessageSender(createHttpClient());
    }

    private CalculatorSoapClient newClientInstance(final String host) {
        final CalculatorSoapClient client = new CalculatorSoapClient();
        client.setDefaultUri(host);
        client.setMarshaller(calculatorMarshaller());
        client.setUnmarshaller(calculatorMarshaller());
        client.setMessageFactory(calculatorMessageFactory());
        client.setMessageSender(webServiceMessageSender());
        client.setInterceptors(interceptors());

        return client;
    }

}