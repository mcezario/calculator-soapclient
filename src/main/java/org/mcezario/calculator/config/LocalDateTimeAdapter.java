package org.mcezario.calculator.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal(final String v) throws Exception {
		return LocalDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME);
	}

	@Override
	public String marshal(final LocalDateTime dateTime) throws Exception {
		return dateTime != null
				? dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
				: null;
	}
}
