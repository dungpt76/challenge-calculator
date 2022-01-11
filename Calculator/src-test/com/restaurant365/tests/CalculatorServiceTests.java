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

	        // 1,5000 will return 1 + 0 = 1
			output = calculatorService.ExecuteAdd("1,5000");
	        assertEquals("1 + 0 = 1", output);  

			// 4,-3 will return 4 + -3 = 1
			/*
	        output = calculatorService.ExecuteAdd("4,-3");
	        assertEquals("4 + -3 = 1", output);  
			*/	
			// empty will return 0
			output = calculatorService.ExecuteAdd("");
	        assertEquals("0", output);  

			// invalid numbers should be converted to 0 e.g. 5,tytyt will return 5 + 0 = 5
			output = calculatorService.ExecuteAdd("5,tytyt");
	        assertEquals("5 + 0 = 5", output); 
			output = calculatorService.ExecuteAdd("abcd,tytyt");
	        assertEquals("0 + 0 = 0", output); 
	        
	        // support new line as a delimiter
			// invalid numbers should be converted to 0 e.g. 5,7\ntytyt will return 5 + 7 + 0 = 12
			output = calculatorService.ExecuteAdd("5,7\ntytyt");
	        assertEquals("5 + 7 + 0 = 12", output); 
			output = calculatorService.ExecuteAdd("1\n2,3");
	        assertEquals("1 + 2 + 3 = 6", output); 
	        
	        // Make any value greater than 1000 an invalid number e.g. 2,1001,6 will return 8
			output = calculatorService.ExecuteAdd("2,1001,6");
	        assertEquals("2 + 0 + 6 = 8", output); 
	        
	        // Support 1 custom delimiter of a single character using the format: //{delimiter}\n{numbers}
			output = calculatorService.ExecuteAdd("//#\n2#5");
	        assertEquals("2 + 5 = 7", output); 
			output = calculatorService.ExecuteAdd("//,\n2,ff,100");
	        assertEquals("2 + 0 + 100 = 102", output); 
	        
	        // Support 1 custom delimiter of any length using the format: //[{delimiter}]\n{numbers}
			output = calculatorService.ExecuteAdd("//[***]\n11***22***33");
	        assertEquals("11 + 22 + 33 = 66", output); 
	        
	        // Support multiple delimiters of any length using the format: //[{delimiter1}][{delimiter2}]...\n{numbers}
			output = calculatorService.ExecuteAdd("//[*][!!][r9r]\n11r9r22*hh*33!!44");
	        assertEquals("11 + 22 + 0 + 33 + 44 = 110", output); 	        
	        

        } catch (InvalidParametersException e) {
        	e.printStackTrace();
			fail("Unexpected exception");
		}
        
		// Not support for more than two numbers
        /*
        try {
			output = calculatorService.ExecuteAdd("5,7,9");
			fail("Not throw exception as expected");
        } catch (InvalidParametersException e) {
			
        }
        */
        
        try {
			// 1,2,3,4,5,6,7,8,9,10,11,12 will return 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 = 78
        	output = calculatorService.ExecuteAdd("1,2,3,4,5,6,7,8,9,10,11,12");
	        assertEquals("1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 = 78", output); 

        } catch (InvalidParametersException e) {
			fail("Unexpected exception");
        }
        
        // Negative numbers are not supported
        
        try {
			// 1,2,3,4,5,6,7,8,9,10,11,12 will return 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 = 78
        	output = calculatorService.ExecuteAdd("1,-2,3,-4");
        	fail("Wrong implementation: the negative numbers must not be supported. Error message must include them.");
        } catch (InvalidParametersException e) {
	        assertEquals("Negative numbers [-2, -4] are not supported.", e.getMessage()); 
        }
    }  
}
