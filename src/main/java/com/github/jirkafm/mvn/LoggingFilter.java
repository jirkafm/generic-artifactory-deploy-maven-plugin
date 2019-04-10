package com.github.jirkafm.mvn;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class LoggingFilter implements ClientRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void filter(final ClientRequestContext requestContext) throws IOException {
		LOGGER.debug("{} {}", requestContext.getMethod(), requestContext.getUri().toString());
		LOGGER.debug("Headers:{}", getHeaderLog(requestContext.getStringHeaders()));
	}

	private String getHeaderLog(final MultivaluedMap<String, String> stringHeaders) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.lineSeparator());

		for (final Entry<String, List<String>> entry : stringHeaders.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append('\t');
			stringBuilder.append(String.join(",", entry.getValue()));
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString().trim();
	}

}
