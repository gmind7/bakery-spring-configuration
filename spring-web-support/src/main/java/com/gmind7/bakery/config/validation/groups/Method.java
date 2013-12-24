package com.gmind7.bakery.config.validation.groups;

import javax.validation.groups.Default;

public class Method {
	
	public static interface GET extends Default {
	};
	
	public static interface POST extends Default {
	};
	
	public static interface PATCH extends Default {
	};

	public static interface PUT extends Default {
	};
	
	public static interface DELETE extends Default {
	};
	 
	public static interface COPY extends Default {
	};
	
	public static interface HEAD extends Default {
	};
	
	public static interface OPTIONS extends Default {
	};
	
	public static interface LINK extends Default {
	};
	
	public static interface UNLINK extends Default {
	};
	
	public static interface PURGE extends Default {
	};
	
}

