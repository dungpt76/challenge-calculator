package com.restaurant365.services;

import com.restaurant365.exceptions.InvalidParametersException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String convertedInputText = inputText;
			if (inputText.startsWith("//")) {
				int firstNewLinePosition = inputText.indexOf("\n");
				if (firstNewLinePosition < 3) {
					throw new InvalidParametersException("The input is invalid.");
				}
				String additionalDelimiters = inputText.substring(2,firstNewLinePosition);
				convertedInputText = inputText.substring(firstNewLinePosition + 1);
				ArrayList<String> delimiters = new ArrayList<String>();
				if (additionalDelimiters.length() > 1) {
					if (!additionalDelimiters.startsWith("[") || !additionalDelimiters.endsWith("]") || (additionalDelimiters.length() < 3)) {
						throw new InvalidParametersException("The input is invalid.");
					}
					// Extract all delimiters in square brackets
					Pattern pattern = Pattern.compile("\\[(.*?)\\]");
					Matcher matcher = pattern.matcher(additionalDelimiters);
					String delimiterText = ""; // rebuild from matcher groups
					while(matcher.find()) {
						delimiterText = delimiterText + "[" + matcher.group(1) + "]";
						delimiters.add(matcher.group(1));
					}

					if (!delimiterText.equals(additionalDelimiters)) {
						throw new InvalidParametersException("Additional delimiters part is invalid: " + additionalDelimiters);
					}
					
					//additionalDelimiters = additionalDelimiters.substring(1, additionalDelimiters.length() -1);
				}
				else {
					delimiters.add(additionalDelimiters);
				}
				
				// standardize convertedInputText
				for (String delimiter : delimiters) {
					convertedInputText = replaceAll(convertedInputText, delimiter, ",");
				}
				
			}
			String[] arrInput = convertedInputText.split("[,\n]+");
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
					else if (value > 1000) {
						// Make any value greater than 1000 an invalid number
						value = 0;
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
	
	private String replaceAll(String input, String search, String replace) throws InvalidParametersException {
		String result = "";
		
		if (replace.indexOf(search) >=0) {
			if (replace.equals(search)) {
				return input;
			}
			else {
				throw new InvalidParametersException("Replace text includes search text");
			}
		}
		String remainingText = input;
		int index = remainingText.indexOf(search);
		while (index >=0) {
			String prefix = "";
			if (index > 0) {
				prefix = remainingText.substring(0, index);
			}
			result = result + prefix + replace;

			if (prefix.length() + search.length() < remainingText.length()) {
				remainingText = remainingText.substring(index + search.length());
			}
			else {
				remainingText = "";
			}
			index = remainingText.indexOf(search);
		}
		result = result + remainingText;
		return result;
	}
}
