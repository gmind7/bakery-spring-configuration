package com.gmind7.bakery.config.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class BindingErrorMessageFactory implements Serializable {
	
	private static final long serialVersionUID = -7701870640933641959L;
	
	protected Logger log = LoggerFactory.getLogger(BindingErrorMessageFactory.class);
	
	private List<String> messages = new ArrayList<String>();
	
	private List<BindingErrorMessage> errors;
	
	public BindingErrorMessageFactory(Errors errors){
		setErrorMessage(errors.getFieldErrors());    
	}
		
	public BindingErrorMessageFactory(BindingResult bindingResult){		
		setErrorMessage(bindingResult.getFieldErrors());    
	}
	
	private void setErrorMessage(List<FieldError> errors){
		
		List<FieldError> fieldErrors = errors;
        List<BindingErrorMessage> validError = new ArrayList<>(fieldErrors.size());
        
        for (FieldError fieldError : fieldErrors) {
        	
        	String filed = fieldError.getField();
        	String code = fieldError.getCodes()[0];
        	String message = fieldError.getDefaultMessage();
        	String rejectValue = fieldError.getRejectedValue()+"";
        	
        	validError.add(new BindingErrorMessage(filed, code, message, rejectValue));
        	
        	break;
        	
        }
        
        log.debug("errors : {}", validError.toString());
        
        this.errors = validError;
        this.messages.add("Validation Failed");
        
	}
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<BindingErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<BindingErrorMessage> errors) {
		this.errors = errors;
	}

	
}
