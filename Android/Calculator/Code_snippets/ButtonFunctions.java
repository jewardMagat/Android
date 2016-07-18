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
	private boolean isSignButtonAllowed = true;					// states if it is allowed to press the sign button.
	private boolean isComputationDone = false;					// states if the computation has been done.
	private boolean isPercentageButtonAllowed = true;
	private boolean showPercentageSign = false;
	private boolean refresh = false;							// states if it s needed to refresh the status of an object.
	private int percentageDataCount = 0; 							// counter for the percentageData.
	private int signedDataCount = 0;							// counter for the signedData.
	private Double percentageData = 0.0;							// converted data into percentage value.
	
	
	
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
					if((signedDataCount == 1)&&(initialState == false)){
						editTextEquation.setText(editTextEquation.getText() + "(" + editTextInput.getText().toString() + ")" + input);
					}else{
						editTextEquation.setText(editTextEquation.getText() + editTextInput.getText().toString() + input);
					}
					
				}
									
				editTextInput.setText("");
				signedDataCount = 0;
				mathOperationIsAllowed = false;
				
		} else {
			editTextEquation.setText(editTextEquation.getText().toString());
		}
	}
	
	// function for the equal sign
	public void compute(final EditText editTextInput, final EditText editTextEquation ){
		
		if ((operationWasPressed==true)&&(numberWasPressed==true)) {
			computedResult += Double.parseDouble(editTextInput.getText().toString());
			
			if(signedDataCount == 1){
				editTextEquation.setText(
						editTextEquation.getText().toString() + "(" + editTextInput.getText().toString()+ ")" + "=");
			}else{
				editTextEquation.setText(
						editTextEquation.getText().toString() + editTextInput.getText().toString()+ "=");
			}
			
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
			signedDataCount = 0;
		}			
	}
	
	//function for the delete button
	public void deleteData(final EditText editTextInput){
		
		if(isDelButtonAllowed){
			String numericalString = editTextInput.getText().toString();
	
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
				editTextInput.setText(numericalString);
			} 
			else if (numericalString.length() <= 1) {
				editTextInput.setText("");
				isDelButtonAllowed = false;
				numberWasPressed = false;
				isDecimalButtonAllowed = true;
				mathOperationIsAllowed = false;
				signedDataCount = 0;
				percentageDataCount = 0;
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
		signedDataCount = 0;
		percentageDataCount = 0;
		percentageData = 0.0;
	}
	
	//function for the sign of the numbers
	public void signedNumber(final EditText editTextInput){
		
		if((isSignButtonAllowed==true)&&(numberWasPressed == true)){			
			Double data;
						
			if(percentageDataCount == 1){
				String numericalString = editTextInput.getText().toString();
				numericalString = numericalString.substring(0, numericalString.length() - 1);
				data = Double.parseDouble(numericalString);
				percentageDataCount = 0;
			}else{
				data = Double.parseDouble(editTextInput.getText().toString());
			}
				
			signedDataCount++;
			
			switch(signedDataCount){
				case 1:
					data*=-1;
					break;
				case 2:
					data*=-1;
					signedDataCount=0;
					break;
				default:
					break;		
			}
			
			if(data % 1 == 0){
				if(showPercentageSign == true){
					editTextInput.setText(Integer.toString(data.intValue())+ "%");
					percentageDataCount = 1;
				}else{
					editTextInput.setText(Integer.toString(data.intValue()));
					percentageDataCount = 0;
				}
					
			}
				
			else{
				if(showPercentageSign == true){
					editTextInput.setText(Double.toString(data)+ "%"); 
					percentageDataCount = 1;
				}else{
					editTextInput.setText(Double.toString(data));
					percentageDataCount = 0;
				}
				
			}
				
		}	
	}
	
	public void handlePercentage(Button button, final EditText editTextInput){
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((isPercentageButtonAllowed == true)&&(numberWasPressed == true)){
					percentageDataCount++;
					
					switch(percentageDataCount){
						case 1:
							percentageData = Double.parseDouble(editTextInput.getText().toString())/100;
							editTextInput.setText(editTextInput.getText().toString() + "%");
							showPercentageSign = true;
							break;
						case 2:
							percentageData*=100;
							showPercentageSign = false;
							if(percentageData % 1 == 0){
								editTextInput.setText(Integer.toString(percentageData.intValue()));
							}else{
								editTextInput.setText(Double.toString(percentageData));
							}
							
							percentageDataCount = 0;
							break;
						default:
							break;
					}
				}
				
				
				
			}
		});
	}
}
