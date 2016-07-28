package com.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class ButtonFunctions {
	
	private Double computedResult = 0.0; 						// computedResult variable holds the result of the computation.
	private boolean numberWasPressed = false;					// states if a number was pressed.
	private boolean operationWasPressed = false; 				// states if the operation signs were pressed.
	private boolean initialState = true; 						// states if the calculator is in its initial State.
	private boolean mathOperationIsAllowed = false; 			// states if it is allowed to press the mathematical operators.
	private boolean isDelButtonAllowed = false;					// states if it is allowed to press delete button.
	private boolean isDecimalButtonAllowed = true;				// states if is allowed to press the decimal button.
	private boolean isSignButtonAllowed = true;					// states if it is allowed to press the sign button.
	private boolean isComputationDone = false;					// states if the computation has been done.
	private boolean isPercentageButtonAllowed = true;
	private boolean isPercentageData = false;
	private boolean refresh = false;							// states if it s needed to refresh the status of an object.
	private int percentageDataCount = 0; 							// counter for the percentageData.
	private int signedDataCount = 0;							// counter for the signedData.
	private Double percentageData = 0.0;							// converted data into percentage value.
	private int mathOperation = 0;
	private int operationCount = 0;	
	private boolean continuousOperation = false;
	private boolean divisionMethod = false;
	
	private StringBuilder inputNumber = new StringBuilder();
	private StringBuilder equation = new StringBuilder();
	private int inputCount = 0;
	private boolean deleteButtonWasPressed = false;
	
	
	//Function of every button in the calculator
	public void click(Button button, final EditText editTextInput, final EditText editTextEquation, final String input){
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch(view.getId()){
					
					case R.id.addButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
					
					case R.id.divideButton:
						divisionMethod = true;
						mathOperations(editTextInput,editTextEquation, input);
						
						break;
					
					case R.id.equalsButton:
						compute(editTextInput,editTextEquation, input);						
						break;
						
					case R.id.signButton:
						signedNumber(editTextInput);
						break;
					
					case R.id.delButton:
						deleteData(editTextInput);
						break;
						
					case R.id.decimalButton:
						inputDecimalPoint(editTextInput);
						break;
					
					case R.id.clearButton:
						clearContents(editTextInput, editTextEquation);
						break;
				
					default:
						
						if(refresh == true){
							editTextInput.setText("");
							refresh= false;
						}
											
						editTextInput.setText(editTextInput.getText() + input);
												
						inputNumber.append(input);
						inputCount++;
						numberWasPressed = true;
						mathOperationIsAllowed = true;
						isDelButtonAllowed = true;
						isPercentageButtonAllowed = true;
						break;
				}			
			}
		});
	}
	
	// function of the math operation buttons
	public void mathOperations(final EditText editTextInput, final EditText editTextEquation, final String input){
		
		if (mathOperationIsAllowed == true) {

			operationWasPressed = true;
			numberWasPressed = false;
			isDecimalButtonAllowed= true;
			isSignButtonAllowed=true;
			operationCount++;
			
			
			if(continuousOperation==true){
				refresh=false;
				inputNumber.append(editTextInput.getText().toString());
				continuousOperation=false;
			}
			
			if(divisionMethod == true){
				editTextInput.setText(editTextInput.getText() + "÷");	
				divisionMethod = false;
			}else{
				editTextInput.setText(editTextInput.getText() + input);
			}
						
			equation.append(inputNumber.toString() + input);
			inputNumber.setLength(0);
			inputCount = 0;
		
			signedDataCount = 0;
			percentageDataCount = 0;
			mathOperationIsAllowed = false;
			isPercentageData = false;				
		} else {
			//editTextEquation.setText(editTextEquation.getText().toString());
		}
	}
	
	
	// function for the equal sign
	public void compute(final EditText editTextInput, final EditText editTextEquation, String input ){
		
	
		
		
	  
		if ((operationWasPressed==true)&&(numberWasPressed==true)) {
			Double result = 0.0;
			
			if(deleteButtonWasPressed==true){
				equation.append(editTextInput.getText().toString());
			}else{
				equation.append(inputNumber.toString());
			}
			

			
			Expression exp = new ExpressionBuilder(equation.toString()).build();

			
			try{
				result = exp.evaluate();
				
				editTextEquation.setText(editTextInput.getText().toString());
				
				if(result % 1 == 0){
					editTextInput.setText(Integer.toString(result.intValue()));
				}else{
					editTextInput.setText(Double.toString((double)Math.round(result *100)/100));
				}
				
				continuousOperation = true;
				
			}catch(ArithmeticException e){
				editTextEquation.setText("Syntax Error");
				
			}

			
			inputNumber.setLength(0);
			equation.setLength(0);
			inputCount = 0;
			
			initialState = true;
			numberWasPressed = false;
			operationWasPressed = false;
			isDelButtonAllowed = false;
			isComputationDone = true;
			refresh = true;
			isDecimalButtonAllowed = true;
			isSignButtonAllowed= true;
			isPercentageData = false;
			computedResult = 0.0;
			percentageData = 0.0;
			signedDataCount = 0;
			percentageDataCount = 0;
			mathOperation = 0;
			operationCount = 0;
			deleteButtonWasPressed= false;
		}		
	}
	
	//function for the delete button
	public void deleteData(final EditText editTextInput){
		
		if(isDelButtonAllowed){
			String numericalString = editTextInput.getText().toString();
			equation.setLength(0);
	
			if (numericalString.length() > 1) {
				if(numericalString.charAt(numericalString.length()-1)=='.'){
					isDecimalButtonAllowed = true;
				}
					
				else if(numericalString.charAt(numericalString.length()-2)=='.'){
					numericalString = numericalString.substring(0, numericalString.length() - 1);
					isDecimalButtonAllowed = true;
				}else if((numericalString.charAt(numericalString.length()-2)=='-')&&(signedDataCount == 1)){
					numericalString=" ";
					signedDataCount = 0;
					isSignButtonAllowed = true;
				}
									
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				equation.append(numericalString);
				deleteButtonWasPressed = true;
			} 
			else if (numericalString.length() <= 1) {
				editTextInput.setText("");
				equation.setLength(0);
				inputNumber.setLength(0);
				isDelButtonAllowed = false;
				numberWasPressed = false;
				isDecimalButtonAllowed = true;
				mathOperationIsAllowed = false;
				signedDataCount = 0;
				percentageDataCount = 0;
				isPercentageButtonAllowed = true;
			}			
		}
	}
	
	//function for the decimal point
	public void inputDecimalPoint(final EditText editTextInput){
		
		if((isDecimalButtonAllowed == true)&&(numberWasPressed == true)){
			editTextInput.setText(editTextInput.getText().toString() + ".");
			inputNumber.append(".");
			isPercentageButtonAllowed = false;
			isDecimalButtonAllowed = false;
		}			
		else
			editTextInput.setText(editTextInput.getText().toString());
	}
	
	//function for clear button
	public void clearContents(final EditText editTextNumbers, final EditText editTextEquation){
		
		editTextNumbers.setText("");
		editTextEquation.setText("");
		
		computedResult = 0.0; 						
		numberWasPressed = false;					
		operationWasPressed = false; 				
		initialState = true; 						
		mathOperationIsAllowed = false; 			
		isDelButtonAllowed = false;					
		isDecimalButtonAllowed = true;
		isSignButtonAllowed= true;
		isComputationDone = false;	
		isPercentageData = false;
		refresh = false;
		signedDataCount = 0;
		percentageDataCount = 0;
		percentageData = 0.0;
		mathOperation = 0;
		operationCount= 0;	
		inputNumber.setLength(0);
		equation.setLength(0);
		inputCount = 0;
	}
	
	//function for the sign of the numbers
	public void signedNumber(final EditText editTextInput){
		
		if(isSignButtonAllowed==true){
			
			editTextInput.setText(editTextInput.getText().toString() + "-");
			equation.append("-");			
		}	
	}
	
	public void handlePercentage(Button button, final EditText editTextInput, final EditText editTextEquation){
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((isPercentageButtonAllowed == true)&&(numberWasPressed == true)&&(operationWasPressed==true)){
					isSignButtonAllowed = false;
					isDecimalButtonAllowed = false;
					percentageDataCount++;
					
					Double result = 0.0;
					percentageData = Double.parseDouble(inputNumber.toString())/100;
										
					equation.append(percentageData);
					
					Expression exp = new ExpressionBuilder(equation.toString()).build();

					
					try{
						result = exp.evaluate();
						
						editTextEquation.setText(editTextInput.getText().toString() + "%");
						
						if(result % 1 == 0){
							editTextInput.setText(Integer.toString(result.intValue()));
						}else{
							editTextInput.setText(Double.toString((double)Math.round(result *100)/100));
						}
						
						continuousOperation = true;
						
					}catch(ArithmeticException e){
						editTextEquation.setText("Syntax Error");
						
					}
			
					inputNumber.setLength(0);
					equation.setLength(0);
					inputCount = 0;
					
					initialState = true;
					numberWasPressed = false;
					operationWasPressed = false;
					isDelButtonAllowed = false;
					isComputationDone = true;
					refresh = true;
					isDecimalButtonAllowed = true;
					isSignButtonAllowed= true;
					isPercentageData = false;
					computedResult = 0.0;
					percentageData = 0.0;
					signedDataCount = 0;
					percentageDataCount = 0;
					mathOperation = 0;
					operationCount = 0;
				}			
			}
		});
	}
}
