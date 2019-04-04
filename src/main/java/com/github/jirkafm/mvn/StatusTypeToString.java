package com.github.jirkafm.mvn;

import java.util.Objects;

import javax.ws.rs.core.Response.StatusType;

public class StatusTypeToString {

	private final StatusType statusType;

	public StatusTypeToString(final StatusType statusType) {
		this.statusType = Objects.requireNonNull(statusType);
	}

	@Override
	public String toString() {
		return String.format("[%d] %s", statusType.getStatusCode(), statusType.getReasonPhrase());
	}
}
