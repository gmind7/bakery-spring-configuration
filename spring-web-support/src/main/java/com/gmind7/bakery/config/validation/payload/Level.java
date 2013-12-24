package com.gmind7.bakery.config.validation.payload;

import javax.validation.Payload;

public class Level {
	
	 public static interface Warn extends Payload {
	 };

	 public static interface Error extends Payload {
	 };
	 
	 public static interface Fatal extends Payload {
	 };
}
