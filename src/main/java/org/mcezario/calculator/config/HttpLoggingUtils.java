package org.mcezario.calculator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.WebServiceMessage;
import org.springframework.xml.transform.TransformerObjectSupport;

import java.io.ByteArrayOutputStream;

@Slf4j
public class HttpLoggingUtils extends TransformerObjectSupport {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String LINE_SEPARATOR = "----------------------------";

    private HttpLoggingUtils() {}

    public static void logMessage(final String id, final WebServiceMessage webServiceMessage) {

        try(final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            webServiceMessage.writeTo(buffer);

            final StringBuilder logMessage = new StringBuilder()
                    .append(LINE_SEPARATOR)
                    .append(NEW_LINE)
                    .append(id)
                    .append(NEW_LINE)
                    .append(LINE_SEPARATOR)
                    .append(NEW_LINE)
                    .append(new String(buffer.toByteArray()))
                    .append(NEW_LINE);

            log.debug(logMessage.toString());

        } catch (final Exception e) {
            log.error("Unable to log HTTP message.", e);
        }
    }

}
