package com.restaurant365.tests;

import com.restaurant365.exceptions.InvalidParametersException;
import com.restaurant365.services.CalculatorService;
import com.restaurant365.services.ICalculatorService;

import junit.framework.TestCase;

public class CalculatorServiceTests extends TestCase {
    private ICalculatorService calculatorService;
	public CalculatorServiceTests(String name) {  
        super(name);  
        calculatorService = new CalculatorService();
    }  

	/*
	 * Test ExecuteAdd method
	 */
    public void testExecuteAdd() {  
        String output;

        try {
    		// 20 will return 20
			output = calculatorService.ExecuteAdd("20");
	        assertEquals("20", output);  

	        // 1,5000 will return 1 + 5000 = 5001
			output = calculatorService.ExecuteAdd("1,5000");
	        assertEquals("1 + 5000 = 5001", output);  

			// 4,-3 will return 4 + -3 = 1
			output = calculatorService.ExecuteAdd("4,-3");
	        assertEquals("4 + -3 = 1", output);  

			// empty will return 0
			output = calculatorService.ExecuteAdd("");
	        assertEquals("0", output);  

			// invalid numbers should be converted to 0 e.g. 5,tytyt will return 5 + 0 = 5
			output = calculatorService.ExecuteAdd("5,tytyt");
	        assertEquals("5 + 0 = 5", output); 
			output = calculatorService.ExecuteAdd("abcd,tytyt");
	        assertEquals("0 + 0 = 0", output); 

        } catch (InvalidParametersException e) {
			fail("Unexpected exception");
		}
        
		// Not support for more than two numbers
        try {
			output = calculatorService.ExecuteAdd("5,7,9");
			fail("Not throw exception as expected");
        } catch (InvalidParametersException e) {
			
        }
    }  
}