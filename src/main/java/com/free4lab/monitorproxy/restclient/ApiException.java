package com.free4lab.monitorproxy.restclient;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ApiException  extends WebApplicationException{

	private static final long serialVersionUID = 7670010733404950022L;

	private String message;
	private int status;
	
	public ApiException (int status) {
		this(status, "");
	}
	
	public ApiException (int status, String message) {
		super(status);
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
