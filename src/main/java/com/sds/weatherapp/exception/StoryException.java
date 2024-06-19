package com.sds.weatherapp.exception;

public class StoryException extends RuntimeException{
	
	public StoryException(String msg) {
		super(msg);
	}
	
	public StoryException(String msg, Throwable e) {
		super(msg, e);
	}
	
	public StoryException(Throwable e) {
		super(e);
	}
}
