package com.test.utill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class APiStatus<T> {

	private String status;
	private String message;
	private T entity;
	public APiStatus(String status, String message, T entity) {
		super();
		this.status = status;
		this.message = message;
		this.entity = entity;
	}
	public String getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public T getEntity() {
		return entity;
	}

	
	
}
