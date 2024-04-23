package com.skyapi.weatherforecast;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

//Apply to all controller in the project
@ControllerAdvice
public class GloalExceptionHandler {
	
	//TO BE ABLE TO DISPLAY THE STRACK TRACE LOGS IN THE CONSOLE!
	private static final Logger LOGGER = LoggerFactory.getLogger(GloalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//For the client 
	@ResponseBody//The return object from this method - dto - will be bound to the response body 
	public ErrorDTO handlerGenericException(HttpServletRequest request ,Exception ex)
	{
		ErrorDTO error = new ErrorDTO();
		
		error.setTimestamp(new Date());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		error.setPath(request.getServletPath());
		
		LOGGER.error(ex.getMessage(), ex);
		
		return error;
		
	}
	
	
	
	

}
