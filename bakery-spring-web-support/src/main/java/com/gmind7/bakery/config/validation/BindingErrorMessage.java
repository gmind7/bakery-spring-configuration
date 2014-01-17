package com.gmind7.bakery.config.validation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BindingErrorMessage implements Serializable {
	
	private static final long serialVersionUID = 1666779303835271384L;

	private String code;
	
	private String filed;
	
	private String message;
	
	private String rejectValue;
	
	public BindingErrorMessage(String code, String filed, String message, String rejectValue){
		
		this.code = code;
		this.filed = filed;
		this.message = message;
		this.rejectValue = right(rejectValue, 100);
		
	}
	
	public String right(final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRejectValue() {
		return rejectValue;
	}

	public void setRejectValue(String rejectValue) {
		this.rejectValue = rejectValue;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((filed == null) ? 0 : filed.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((rejectValue == null) ? 0 : rejectValue.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BindingErrorMessage other = (BindingErrorMessage)obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (filed == null) {
			if (other.filed != null)
				return false;
		} else if (!filed.equals(other.filed))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (rejectValue == null) {
			if (other.rejectValue != null)
				return false;
		} else if (!rejectValue.equals(other.rejectValue))
			return false;
		return true;
	}
	
	public String toString() {
		return "BindingErrorMessage [code=" + code + ", filed=" + filed + ", message=" + message + ", rejectValue=" + rejectValue + "]";
	}

}
