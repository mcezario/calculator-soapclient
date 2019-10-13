package org.mcezario.calculator.config;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

public class LogHttpHeaderClientInterceptor implements ClientInterceptor {

    @Override
    public void afterCompletion(MessageContext arg0, Exception arg1)
            throws WebServiceClientException {
        //
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Response Fault", messageContext.getRequest());

        return true;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Request Message", messageContext.getRequest());

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        HttpLoggingUtils.logMessage("Client Response Message", messageContext.getResponse());

        return true;
    }

}
