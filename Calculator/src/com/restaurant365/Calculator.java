
/**
 * 
 */
package com.restaurant365;

/**
 * @author dung.phungtrung
 *
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Scanner;

import com.restaurant365.exceptions.InvalidParametersException;
import com.restaurant365.services.*;
	  

public class Calculator extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -801129506755841484L;
	ICalculatorService _calculatorService;
	Label lblInput;
	Label lblOutput;
	Label lblError;
	Button btnCalculate;
	Button btnClear;
	TextField txtInput;
	TextField txtOutput;
	// initializing using constructor  
	Calculator() {  
		_calculatorService = new CalculatorService();
	      lblInput = new Label("Input text:");   
	      lblOutput = new Label("Result:");  
	      lblError =  new Label("");
	      
	      btnCalculate = new Button("Calculate");  
	      btnClear = new Button("Clear");  
	      txtInput = new TextField();  	  
	      txtOutput = new TextField();  	  
	      // setting button position on screen  
	      btnCalculate.setBounds(180,150,80,30);  
	      btnClear.setBounds(270,150,80,30);  
	      lblInput.setBounds(20, 60, 80, 30);
	      txtInput.setBounds(110, 60, 340, 30);
	      
	      lblOutput.setBounds(20, 100, 80, 30);  	  
	      txtOutput.setBounds(110, 100, 340, 30);
	      lblError.setBounds(20, 140, 430, 30);
	      lblError.setForeground(new Color(255,0,0));
	      txtOutput.setEditable(false);
	      btnCalculate.addActionListener(new ActionListener() {    
	    	    public void actionPerformed (ActionEvent e) {    
	    	    	lblError.setText("");
    	    		txtOutput.setText("");
	    	    	try
	    	    	{
	    	    		String result = _calculatorService.ExecuteAdd(txtInput.getText());
	    	    		txtOutput.setText(result);
	    	    	}
	    	    	catch (InvalidParametersException ex) {
	    	    		lblError.setText(ex.getMessage());
	    	    	}
    	        }    
	    	    });
	      btnClear.addActionListener(new ActionListener() {    
	    	    public void actionPerformed (ActionEvent e) {    
	    	    		txtInput.setText("");
	    	    		txtOutput.setText("");
	    	        }    
	    	    }); 
	      add(lblInput);
	      add(txtInput);
	      add(lblOutput);
	      add(txtOutput);
	      add(lblError);
	      add(btnCalculate);  
	      add(btnClear);  
	      addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                dispose();
	            }
	        });	  
	      // frame size 300 width and 300 height    
	      setSize(500,200);  
	  
	      // setting the title of Frame  
	      setTitle("Challenge Calculator");   
	          
	      // no layout manager   
	      setLayout(null);   
	  
	      // now frame will be visible, by default it is not visible    
	      setVisible(true);  
	}    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
  
		// UI mode
		// Calculator calForm = new Calculator();   
		
		// Console mode
        final Scanner in = new Scanner(System.in);
        ICalculatorService calculatorService = new CalculatorService();
        Runtime.getRuntime().addShutdownHook(new Thread()
        {

            public void run()
            {
                in.close();
            	System.out.println("Shutdown hook ran!");
            }
        });        

        boolean bContinue = true;
        while (bContinue) {
            System.out.print("Enter your expression:");
            String s = in.nextLine();
            
            try {
				String result = calculatorService.ExecuteAdd(s);
	            System.out.println("Result: " + result);
			} catch (InvalidParametersException e) {
				System.out.println("Exception: " + e.getMessage());
			} 
        }
        
	}

}
