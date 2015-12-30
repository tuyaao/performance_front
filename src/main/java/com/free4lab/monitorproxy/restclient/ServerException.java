package com.free4lab.monitorproxy.restclient;

public class ServerException extends ApiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5555908358836429171L;

	public ServerException() {
		this("Internal Server Error");
	}
	
	public ServerException (String msg) {
		super(500, msg);
	}
}
