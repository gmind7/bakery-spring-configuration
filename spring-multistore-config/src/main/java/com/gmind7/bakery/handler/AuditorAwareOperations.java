package com.gmind7.bakery.handler;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareOperations implements AuditorAware<Long> {

	private long auditor;

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}

	public Long getCurrentAuditor() {
		return auditor;
	}

}
