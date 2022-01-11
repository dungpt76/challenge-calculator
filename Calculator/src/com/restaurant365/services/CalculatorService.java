package com.restaurant365.services;

import com.restaurant365.exceptions.InvalidParametersException;
import java.util.ArrayList;
import java.util.List;

public class CalculatorService implements ICalculatorService {

	public String ExecuteAdd(String inputText) throws InvalidParametersException {
		ArrayList<Integer> numbers = parseInput(inputText);
		String output = "";
		Integer result = 0;
		if (numbers.size() == 1) return numbers.get(0).toString();
		for (int index = 0; index < numbers.size(); index ++) {
			Integer currentNumber = numbers.get(index);
			output = (output == "") ? ("" + currentNumber) : ( output + " + " + currentNumber);
			result = result + currentNumber;
		}
		return output + " = " + result;
	}
	
	private ArrayList<Integer> parseInput(String inputText) throws InvalidParametersException {
		inputText = inputText.trim();
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (inputText == "") {
			result.add(0);
		}
		else {
			String[] arrInput = inputText.split("[,\n]+");
			//Remove the maximum constraint for numbers
			/*
			if (arrInput.length > 2) {
				throw new InvalidParametersException("Not support for more than two numbers");
			}
			*/
			ArrayList<Integer> negativeNumbers = new ArrayList<Integer>();
					
			for (int index = 0; index < arrInput.length; index ++) {
				String currentInput = arrInput[index];
				Integer value = 0;
				try {
					value = Integer.parseInt(currentInput);
					if (value < 0) {
						negativeNumbers.add(value);
					}
				}
				catch (NumberFormatException ex) {
					// empty or invalid number will be converted to 0
				}
				result.add(value);
			}
			if (negativeNumbers.size() > 0) {
				throw new InvalidParametersException(String.format("Negative numbers %s are not supported.", negativeNumbers.toString()));
			}
		}
		return result;
	}
}
