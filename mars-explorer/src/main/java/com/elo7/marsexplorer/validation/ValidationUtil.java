package com.elo7.marsexplorer.validation;

import org.springframework.stereotype.Component;

import com.elo7.marsexplorer.exception.ResourceNotFoundException;

@Component
public class ValidationUtil {

	public void ensureExistence(Object resource) throws ResourceNotFoundException {
		throw new UnsupportedOperationException("not yet implemented");
	}
}