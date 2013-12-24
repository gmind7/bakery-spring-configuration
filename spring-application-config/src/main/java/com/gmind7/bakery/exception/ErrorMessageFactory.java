package com.gmind7.bakery.exception;

public interface ErrorMessageFactory<T extends Exception> {
 
    Class<T> getExceptionClass();
 
    ErrorMessage getErrorMessage(T ex);
 
    int getResponseCode();
}
