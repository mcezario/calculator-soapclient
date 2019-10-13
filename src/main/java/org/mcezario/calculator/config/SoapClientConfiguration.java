package org.mcezario.calculator.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

public abstract class SoapClientConfiguration {

    private String host;
    private int timeout;
    private int maxTotal;
    private int defaultMaxPerRoute;

    protected SoapClientConfiguration(final String host, final int timeout) {
        this.host = host;
        this.timeout = timeout;
    }

    protected SoapClientConfiguration(final String host, final int timeout, final int maxTotal, final int defaultMaxPerRoute) {
        this.host = host;
        this.timeout = timeout;
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    protected String getHost() {
        return host;
    }

    protected WebServiceMessageSender createWebServiceMessageSender(final HttpClient httpClient) {
        HttpComponentsMessageSender httpComponentsMessageSender = httpClient == null //
                ? new HttpComponentsMessageSender() //
                : new HttpComponentsMessageSender(httpClient);
        // timeout for creating a connection
        httpComponentsMessageSender.setConnectionTimeout(timeout);
        // when you have a connection, timeout the read blocks for
        httpComponentsMessageSender.setReadTimeout(timeout);

        return httpComponentsMessageSender;
    }

    protected HttpClient createHttpClient() {
        final PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(defaultMaxPerRoute);

        final DefaultHttpClient defaultClient = new DefaultHttpClient(cm);
        defaultClient.addRequestInterceptor(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor(), 0);

        return defaultClient;
    }

    protected ClientInterceptor[] interceptors() {
        return new ClientInterceptor[] { new LogHttpHeaderClientInterceptor() };
    }
}