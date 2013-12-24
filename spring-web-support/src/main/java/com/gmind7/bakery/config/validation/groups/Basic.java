package com.gmind7.bakery.config.validation.groups;

import javax.validation.groups.Default;

public class Basic {
	
	public static interface KEY extends Default {
	};
		 
	public static interface EXITS extends Default {
	};
	
	public static interface DUPLICATE extends Default {
	};
	
}
