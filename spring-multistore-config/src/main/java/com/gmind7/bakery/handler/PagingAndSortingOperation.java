package com.gmind7.bakery.handler;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingAndSortingOperation<T, ID extends Serializable> {
	
	public T findOne(ID id);	
	
	public Page<T> findAll();
	
	public Page<T> findAll(Pageable pageable);
	
	public Long count();
	
	public T save(T source);
	
	public Boolean delete(ID id);
	
}

