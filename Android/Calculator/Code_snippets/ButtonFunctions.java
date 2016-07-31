package com.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class ButtonFunctions {
	
	private boolean numberWasPressed = false;					// states if a number was pressed.
	private boolean operationWasPressed = false; 				// states if the operation signs were pressed.
	private boolean isDecimalButtonAllowed = true;				// states if is allowed to press the decimal button.
	private boolean isSignButtonAllowed = true;					// states if it is allowed to press the sign button.
	private boolean isComputationDone = false;					// states if the computation has been done.
	private boolean isPercentageButtonAllowed = true;
	private boolean refresh = false;							// states if it s needed to refresh the status of an object.
	private boolean initialState = false;
	private boolean changeOperationIsAllowed = false;	
	private Double percentageData = 0.0;							// converted data into percentage value.		
	private int inputCount = 0;

	private boolean isSignedData = false;
	

	
	private StringBuilder inputNumber = new StringBuilder();
		
	//Function of every button in the calculator
	public void click(Button button, final EditText editTextInput, final EditText editTextEquation, final String input){
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch(view.getId()){
					
					case R.id.addButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
						
					case R.id.subtractButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
						
					case R.id.multiplyButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
					
					case R.id.divideButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
					
					case R.id.equalsButton:
						compute(editTextInput,editTextEquation, input);						
						break;
						
					case R.id.signButton:
						signedNumber(editTextInput);
						break;
					
					case R.id.delButton:
						deleteData(editTextInput, editTextEquation);
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
						inputCount++;
						initialState = true;
						inputNumber.append(input);
						numberWasPressed = true;
						changeOperationIsAllowed = false;
						isPercentageButtonAllowed = true;
						isSignButtonAllowed = true;
						break;
				}			
			}
		});
	}
		
	// function of the math operation buttons
	private void mathOperations(final EditText editTextInput, final EditText editTextEquation, final String input){
		
		if(initialState==true){
			String numericalString = editTextInput.getText().toString();
			
			operationWasPressed = true;
			numberWasPressed = false;
			isDecimalButtonAllowed= true;
			isSignButtonAllowed=true;
			inputNumber.setLength(0);
			inputCount = 0;
			isSignedData = false;
									
			if(isComputationDone==true){
				refresh=false;
				isComputationDone = false;
			}
			
			if(changeOperationIsAllowed==true){
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				editTextInput.setText(numericalString + input);
				
			}else{
				editTextInput.setText(numericalString + input);
			}
			
			if((numericalString.charAt(numericalString.length()-1)=='+')||(numericalString.charAt(numericalString.length()-1)=='-')
					||(numericalString.charAt(numericalString.length()-1)=='x')||(numericalString.charAt(numericalString.length()-1)=='÷')){
				changeOperationIsAllowed = true;	
			}
			
			changeOperationIsAllowed = true;
		}
			
	}
	
	// function for the equal sign
	private void compute(final EditText editTextInput, final EditText editTextEquation, String input ){

		if ((operationWasPressed==true)&&(numberWasPressed==true)) {
			Double result = 0.0;
			
			String initialData = editTextInput.getText().toString();
			String initialFilter = initialData.replaceAll("x", "*");
			String finalFilter = initialFilter.replaceAll("÷", "/");
						
			Expression exp = new ExpressionBuilder(finalFilter).build();
		
			try{
				result = exp.evaluate();
				
				editTextEquation.setText(editTextInput.getText().toString());
				editTextInput.setText("");
				
				if(result % 1 == 0){
					editTextInput.setText(Integer.toString(result.intValue()));
				}else{
					editTextInput.setText(Double.toString((double) Math.round(result * 100) / 100));
				}				
			}catch(Exception e){
				editTextEquation.setText("Syntax Error");
				editTextInput.setText("");
			}

			numberWasPressed = false;
			operationWasPressed = false;
			isComputationDone = true;
			refresh = true;
			isDecimalButtonAllowed = true;
			isSignButtonAllowed= true;
			percentageData = 0.0;
			isSignedData = false;
		}		
	}
	
	public void history(){
		
	}
	
	//function for the delete button
	private void deleteData(final EditText editTextInput, final EditText editTextEquation){
		
		if(isComputationDone==true){
			clearContents(editTextInput, editTextEquation);
		}else{
			
			String numericalString = editTextInput.getText().toString();
			
			if (numericalString.length() > 1) {
				
				if(numericalString.charAt(numericalString.length()-1)=='.'){
					isDecimalButtonAllowed = true;
					isPercentageButtonAllowed = false;
				}else if((numericalString.charAt(numericalString.length()-1)=='+')||(numericalString.charAt(numericalString.length()-1)=='-')
						||(numericalString.charAt(numericalString.length()-1)=='x')||(numericalString.charAt(numericalString.length()-1)=='÷')){
					
					changeOperationIsAllowed = false;
				}
			
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				editTextInput.setText(numericalString);
			}
		
			else if (numericalString.length() <= 1) {
				editTextInput.setText("");
				
				inputNumber.setLength(0);
				numberWasPressed = false;
				isDecimalButtonAllowed = true;
				isPercentageButtonAllowed = false;				
			}
		}
	}
	
	//function for the decimal point
	private void inputDecimalPoint(final EditText editTextInput){
		
		if((isDecimalButtonAllowed == true)&&(numberWasPressed == true)){
			editTextInput.setText(editTextInput.getText().toString() + ".");
			inputNumber.append(".");
			inputCount++;
			isDecimalButtonAllowed = false;
			isSignButtonAllowed = false;
			isPercentageButtonAllowed = false;
		}			
		else
			editTextInput.setText(editTextInput.getText().toString());
	}
	
	//function for clear button
	private void clearContents(final EditText editTextNumbers, final EditText editTextEquation){
		
		editTextNumbers.setText("");
		editTextEquation.setText("");
							
		numberWasPressed = false;					
		operationWasPressed = false; 																	
		isDecimalButtonAllowed = true;
		isSignButtonAllowed= true;
		isComputationDone = false;
		isPercentageButtonAllowed = false;
		refresh = false;
		changeOperationIsAllowed = false;
		percentageData = 0.0;
		inputCount = 0;
		inputNumber.setLength(0);
		initialState = false;
		isSignedData = false;

	}
	
	//function for the sign of the numbers
	public void signedNumber(final EditText editTextInput){
		
		if(isSignButtonAllowed==true){
			
			String numericalString = editTextInput.getText().toString();
			
			if((operationWasPressed==true)&&(numericalString.charAt(numericalString.length()-1)=='+'||
					(numericalString.charAt(numericalString.length()-1)=='-'))){
				
				char operationSymbol= numericalString.charAt(numericalString.length()-1);
				String symbol = "";
				
				switch(operationSymbol){
					case '+':
						symbol = "-";
						break;
					case '-': 
						symbol = "+";
						signedDataCounter = 0;
						break;
					default:
						break;
				}
				
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				editTextInput.setText(numericalString + symbol);				
			}else{
				editTextInput.setText(numericalString + "-");
			}			
		}	
	}
	
	public void handlePercentage(Button button, final EditText editTextInput, final EditText editTextEquation){
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((isPercentageButtonAllowed == true)&&(numberWasPressed == true)&&(operationWasPressed==true)){
					isSignButtonAllowed = false;
					isDecimalButtonAllowed = false;
					
					Double result = 0.0;
					
					String initialData = editTextInput.getText().toString();
					String initialFilter = initialData.replaceAll("x", "*");
					String secondFilter = initialFilter.replaceAll("÷", "/");					
					String finalFilter = secondFilter.substring(0, secondFilter.length() - inputCount);
					String outputString = initialData.substring(0, initialData.length() - inputCount);
					
					percentageData = Double.parseDouble(inputNumber.toString())/100;
					//editTextEquation.setText(finalFilter + Double.toString(percentageData));
					
					Expression exp = new ExpressionBuilder(finalFilter + Double.toString(percentageData)).build();
					
					try{
						result = exp.evaluate();
						
						if(isSignedData==true){
							editTextEquation.setText(outputString + inputNumber.toString() + ")%");
						}else{
							editTextEquation.setText(outputString + inputNumber.toString()+ "%");
						}						
						
						editTextInput.setText("");
						
						if(result % 1 == 0){
							if(isSignedData==true){
								editTextInput.setText(Integer.toString(result.intValue()*-1));
							}else{
								editTextInput.setText(Integer.toString(result.intValue()));
							}						
						}else{
							if(isSignedData==true){
								editTextInput.setText(Double.toString(((double) Math.round(result * 100) / 100)*-1));
							}else{
								editTextInput.setText(Double.toString((double) Math.round(result * 100) / 100));
							}							
						}				
					}catch(Exception e){
						editTextEquation.setText("Syntax Error");
						editTextInput.setText("");
					}
										
					numberWasPressed = false;
					operationWasPressed = false;
					isComputationDone = true;
					refresh = true;
					isSignButtonAllowed= false;
					percentageData = 0.0;			
					inputCount = 0;
					isPercentageButtonAllowed = false;	
					isSignedData = false;
				}			
			}
		});
	}
}
