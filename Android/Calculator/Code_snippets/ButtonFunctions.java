package com.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ButtonFunctions {
	
	private Double computedResult = 0.0; 						// computedResult variable holds the result of the computation.
	private boolean numberWasPressed = false;					// states if a number was pressed.
	private boolean operationWasPressed = false; 				// states if the operation signs were pressed.
	private boolean initialState = true; 						// states if the calculator is in its initial State.
	private boolean mathOperationIsAllowed = false; 			// states if it is allowed to press the mathematical operators.
	private boolean isDelButtonAllowed = false;					// states if it is allowed to press delete button.
	private boolean isDecimalButtonAllowed = true;				// states if is allowed to press the decimal button.
	private boolean isSignButtonAllowed = true;
	private boolean isComputationDone = false;					// states if the computation has been done.
	private boolean refresh = false;							// states if it s needed to refresh the status of an object.
	
	
	//Function of every button in the calculator
	public void click(Button button, final EditText editTextInput, final EditText editTextEquation, final String input){
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch(view.getId()){
					
					case R.id.addButton:
						mathOperations(editTextInput,editTextEquation, input);
						break;
					
					case R.id.equalsButton:
						compute(editTextInput,editTextEquation);
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
												
						numberWasPressed = true;
						editTextInput.setText(editTextInput.getText() + input);
						mathOperationIsAllowed = true;
						isDelButtonAllowed = true;
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
			
				
				if (initialState) {
					computedResult = Double.parseDouble(editTextInput.getText().toString());
					initialState = false;
				} else {
					computedResult += Double.parseDouble(editTextInput.getText().toString());
				}
				
				if(isComputationDone == true){
					Double data = computedResult;
					
					if(data % 1 == 0)
						editTextEquation.setText(Integer.toString(computedResult.intValue()) + input);
					else
						editTextEquation.setText(Double.toString(computedResult) + input);
					
					isComputationDone = false;
				}else{							
					editTextEquation.setText(editTextEquation.getText() + editTextInput.getText().toString() + input);
				}
									
				editTextInput.setText("");
				mathOperationIsAllowed = false;
			
		} else {
			editTextEquation.setText(editTextEquation.getText().toString());
		}
	}
	
	// function for the equal sign
	public void compute(final EditText editTextInput, final EditText editTextEquation ){
		
		if ((operationWasPressed==true)&&(numberWasPressed==true)) {
			computedResult += Double.parseDouble(editTextInput.getText().toString());
			editTextEquation.setText(
					editTextEquation.getText().toString() + editTextInput.getText().toString()+ "=");
			
			Double data = computedResult;
			
			if(data % 1 == 0)
				editTextInput.setText(Integer.toString(computedResult.intValue()));
			else 
				editTextInput.setText(Double.toString((double)Math.round(computedResult *100)/100));

			initialState = true;
			numberWasPressed = false;
			operationWasPressed = false;
			isDelButtonAllowed = false;
			isComputationDone = true;
			refresh = true;
			isDecimalButtonAllowed = true;
			isSignButtonAllowed= true;
			computedResult = 0.0;
		}			
	}
	
	//function for the delete button
	public void deleteData(final EditText editTextInput){
		
		if(isDelButtonAllowed){
			String numericalString = editTextInput.getText().toString();
	
			if (numericalString.length() > 1) {
				if(numericalString.charAt(numericalString.length()-1)=='.')
					isDecimalButtonAllowed = true;
				
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				editTextInput.setText(numericalString);
			} 
			else if (numericalString.length() <= 1) {
				editTextInput.setText("");
				isDelButtonAllowed = false;
				numberWasPressed = false;
				isDecimalButtonAllowed = true;
			}			
		}
	}
	
	//function for the decimal point
	public void inputDecimalPoint(final EditText editTextInput){
		
		if((isDecimalButtonAllowed == true)&&(numberWasPressed == true)){
			editTextInput.setText(editTextInput.getText().toString() + ".");
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
		refresh = false;							
	}
	
	public void signedNumber(final EditText editTextInput){
		
		if((isSignButtonAllowed==true)&&(numberWasPressed == true)){
			int dataCount = 0;
			String numericalString = editTextInput.getText().toString();
			if(numericalString.charAt(numericalString.length()-1)=='.')
				isSignButtonAllowed = false;
			
			Double data = Double.parseDouble(editTextInput.getText().toString());
			
			
			
			dataCount++;
			
			switch(dataCount){
				case 1:
					data*=-1;
					break;
				case 2:
					data*=-1;
					dataCount=0;
					break;
				default:
					break;		
			}
			
			if(data % 1 == 0)
				editTextInput.setText(Integer.toString(data.intValue()));
			else
				editTextInput.setText(Double.toString(data));
		}
		
		
		
	}
}
