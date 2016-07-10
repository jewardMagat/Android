package test.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Button_Click_Int {

	private Double num = 0.0;
	private Double result = 0.0;
	private Double percentageData = 0.0;
	private int operatorLog = 0;
	private int inputCount = 0;
	private int clickCount = 0;
	private int counter = 0;
	private Double positiveData = 0.0;
	private Double negativeData = 0.0;
	private Double rawData = 0.0;
	
	
	
	private boolean isNumber = true;

	private boolean decimalButtonState = true;
	private boolean operationState = false;
	private boolean equalState = false;
	private boolean equationState = false;
	private boolean computationDone = false;
	private boolean isPercentage = false;
	private boolean percentageIsAllowed = false;
	private boolean deleteIsAllowed = false;
	private boolean isANegativeNumber = false;
	private boolean signIsAllowed = false;
	
	private boolean checkData = false;


	MainActivity activity = new MainActivity();

	public void setDisplayValue(Button button, final EditText display, final EditText secondDisplay,
			final String input) {

		button.setOnClickListener(new View.OnClickListener() {
		int length;
		
			@Override
			public void onClick(View v) {

				switch (v.getId()) {

				case R.id.addButton:
					mathematicalOperation(1, input, display, secondDisplay);
					break;

				case R.id.subtractButton:
					mathematicalOperation(2, input, display, secondDisplay);
					break;

				case R.id.multiplyButton:
					mathematicalOperation(3, input, display, secondDisplay);
					break;

				case R.id.divideButton:
					mathematicalOperation(4, input, display, secondDisplay);
					break;

				case R.id.periodButton:
					deleteIsAllowed = true;
					
					if (decimalButtonState) {						
						display.setText(display.getText() + input);
						decimalButtonState = false;
					} else
						display.setText(display.getText());
					break;
					
				case R.id.clearButton:
					display.setText(input);
					secondDisplay.setText(input);
					operatorLog = 0;
					result = 0.0;
					num = 0.0;
					deleteIsAllowed = false;
					signIsAllowed = true;
					isNumber = true;
					decimalButtonState = true;
					operationState = true;
					equalState= false;
					equationState = true;
					inputCount = 0;
					isPercentage = false;
					percentageIsAllowed = false;
					percentageData = 0.0;
					negativeData = 0.0;
					positiveData = 0.0;
					break;
					
				case R.id.delButton:
					if(deleteIsAllowed){
						length = display.getText().length();
						
						if(length >0){
							display.getText().delete(length-1, length);
						}
					}					
					break;
					
				case R.id.signButton:
					
					if(checkData){
						rawData = Double.parseDouble(display.getText().toString());
						checkData = false;
					}
						
					
					if(signIsAllowed){
					
						
						if(rawData % 1 == 0){
							counter++;
							
							switch(counter){
								case 1:
									isANegativeNumber = true;
									negativeData = rawData* -1;
									display.setText(Integer.toString(negativeData.intValue()));
									break;
								
								case 2:
									positiveData = negativeData * -1;
									display.setText(Integer.toString(positiveData.intValue()));
									counter = 0;
									break;
								default:
									isANegativeNumber = true;
									display.setText("");
									break;
							}	
						}
						else{
							counter++;
							
							switch(counter){
								case 1:
									isANegativeNumber = true;
									negativeData = rawData* -1;
									display.setText(Double.toString(negativeData));
									break;
								
								case 2:
									positiveData = negativeData * -1;
									display.setText(Double.toString(positiveData));
									counter = 0;
									break;
								default:
									isANegativeNumber = true;
									display.setText("");
									break;
							}	
						}
						
						
					}
						
					break;
				
				case R.id.percentButton:
					handlePercentage(display, length, input);
					break;

				default:
					
					if(computationDone)
						display.setText("");
					
					deleteIsAllowed = true;
					signIsAllowed= true;
					checkData= true;
					computationDone = false;
					isPercentage = false;
					operationState = true;
					isNumber = true;
					display.setText(display.getText() + input);					
					break;
				}
			}
		});
	}

	// method for the mathematical operation to be used
	public void mathematicalOperation(int code, String input, EditText display, EditText secondDisplay) {

			if(!operationState){
				display.setText("");
				secondDisplay.setText(secondDisplay.getText());
				
			}else{
				percentageIsAllowed = true;
				counter = 0;
				decimalButtonState = true;
				clickCount = 0;
				isNumber = false;
				countNumericInputs(display);
				operationState = false;
				
				if(isANegativeNumber)
					secondDisplay.setText(secondDisplay.getText().toString() + " (" + display.getText().toString() + ") " + input);
				else
					secondDisplay.setText(secondDisplay.getText().toString() + display.getText().toString() + input);
				
				display.setText("");
				equalState = true;
				isPercentage = false;
				isANegativeNumber = false;
				operatorLog = code;
				
				if(equationState){
					secondDisplay.setText(roundedData() + input);
					display.setText("");
					equationState = false;
				}
					
			}	
	}

	public void countNumericInputs(EditText display) {
		
		if (!isNumber) {
			inputCount++;

			if (inputCount == 1) {
				
				if(isPercentage){
					result = percentageData;
				}
				else{
					result = Double.parseDouble(display.getText().toString());
				}				
			}

			else {
				executeOperation(display);
				operatorLog = 0;
			}
		}
	}

	public void compute(Button button, final EditText display, final EditText secondDisplay) {

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(equalState){
					computationDone = true;
					
					if(isPercentage){
						int length = display.getText().length();
						display.getText().delete(length-1, length);
						
						num = Double.parseDouble(display.getText().toString());
					}else
						num = Double.parseDouble(display.getText().toString());
					
					executeOperation(display);
					display.setText("");
	
					// check if zero(0) is a divider.
					if (Double.isInfinite(result)) {
						secondDisplay.setText("Syntax error");
						operatorLog = 0;
						result = 0.0;
						deleteIsAllowed = false;
						signIsAllowed = false;
						isNumber = true;
						decimalButtonState = true;
						operationState = false;
						equalState= false;
						equationState = true;
						inputCount = 0;
						isPercentage = false;
						percentageData = 0.0;
						percentageIsAllowed = false;
						negativeData = 0.0;
						positiveData = 0.0;
						display.setText("");
					}
	
					else {

						if (num % 1 == 0){
							if(isPercentage)
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + " (" + num.intValue() + ") " + "%" + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num.intValue() + "%" + " = ");
							else
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + " (" + num.intValue() + ") " + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num.intValue() + " = ");
							
							display.setText(roundedData());
						}
						else{
							if(isANegativeNumber)
								secondDisplay.setText(secondDisplay.getText().toString() + " (" + num + ") " + " = ");
							else
								secondDisplay.setText(secondDisplay.getText() + "" + num + " = ");
							display.setText(roundedData());
						}
	
						operatorLog = 0;
						result = 0.0;
						num = 0.0;
						deleteIsAllowed = false;
						signIsAllowed = false;
						isNumber = true;
						decimalButtonState = true;
						operationState = true;
						equalState= false;
						equationState = true;
						inputCount = 0;
						isPercentage = false;
						percentageIsAllowed = false;
						percentageData = 0.0;
						negativeData = 0.0;
						positiveData = 0.0;
					}
				}
				else{
					display.setText(display.getText());
				}
					
			}
		});
	}
	
	private String roundedData(){
		
		String outputData= null;
		
		Double doubleResult = 0.0;
		
		if (result % 1 == 0)
			outputData = Integer.toString(result.intValue());
		else {
			// round off the output to two(2) decimal places
			doubleResult = (double) Math.round(result * 100) / 100; 

			outputData = Double.toString(doubleResult);
		}
		
		return outputData;
	}

	public void executeOperation(EditText display) {

		switch (operatorLog) {
			case 1:
				if(isPercentage){
					result += percentageData;
				}
				else
					result += Double.parseDouble(display.getText().toString());
				break;
			case 2:
				if(isPercentage)
					result -= percentageData;
				else
					result -= Double.parseDouble(display.getText().toString());
				break;
			case 3:
				if(isPercentage)
					result *= percentageData;
				else
					result *= Double.parseDouble(display.getText().toString());
				break;
			case 4:
				if(isPercentage)
					result /= percentageData;
				else
					result /= Double.parseDouble(display.getText().toString());
				break;

			default:
				break;
		}
	}
	
	public void handlePercentage(EditText display, int length, String input){
		
		signIsAllowed = false;
		
		if(percentageIsAllowed){
			clickCount++;
			
			if(clickCount >= 2){
				display.setText(display.getText());
			}
				
			else{
				isPercentage = true;
				//percentageData = 0.0;					
				display.setText(display.getText().toString() + input);
				
				length = display.getText().length();
				display.getText().delete(length-1, length);
								
				percentageData = Double.parseDouble(display.getText().toString())/100;
				
				display.setText(display.getText().toString() + input);
			}
		}			
	}
	
	public Double signedNumber(EditText display){
		Double data = 0.0;
		int counter = 0;
		
		counter++;
		
		switch(counter){
			case 1:
				data = Double.parseDouble(display.getText().toString())* -1;
				break;
			case 2:
				data = Double.parseDouble(display.getText().toString())* 1;
				counter = 0;
			default:
				break;
		}		
		return data;
	}
}


