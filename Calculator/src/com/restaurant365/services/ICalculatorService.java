package com.restaurant365.services;

import com.restaurant365.exceptions.InvalidParametersException;

public interface ICalculatorService {
	public String ExecuteAdd(String inputText) throws InvalidParametersException;
}
