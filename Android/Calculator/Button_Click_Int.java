package test.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
	private boolean buttonIsAllowed = false;
	private int cursorCount = 0;
	private String deletedData= "";


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

				case R.id.decimalButton:
					isPercentage = true;
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
					signIsAllowed = false;
					isNumber = true;
					decimalButtonState = true;
					operationState = true;
					equalState= false;
					equationState = true;
					inputCount = 0;
					isPercentage = false;
					percentageIsAllowed = false;
					buttonIsAllowed = false;
					percentageData = 0.0;
					cursorCount = 0;
					counter = 0;
					break;
					
					
				case R.id.delButton:
					if(deleteIsAllowed){
						length = display.getText().length();
						deletedData = "";

						if(length >0){
							display.getText().delete(length-1, length);
							deletedData = display.getText().toString();
							deleteIsAllowed = false;
						}
					}					
					break;
					
				case R.id.signButton:				
					if(signIsAllowed){
						if(checkData){
							
							negativeData = 0.0;
							positiveData = 0.0;
							rawData = Double.parseDouble(display.getText().toString());
							checkData = false;
						}
	
						counter++;
						
						switch(counter){
							case 1:
								isANegativeNumber = true;
								cursorCount++;
								negativeData = rawData* -1;
								
								if(rawData % 1 == 0)
									display.setText(Integer.toString(negativeData.intValue()));
								else
									display.setText(Double.toString(negativeData));
								break;
							
							case 2:
								isANegativeNumber = false;
								cursorCount--;
								positiveData = negativeData * -1;
								if(rawData % 1 == 0)
									display.setText(Integer.toString(positiveData.intValue()));
								else
									display.setText(Double.toString(positiveData));
								counter = 0;
								break;
							
							default:
								//isANegativeNumber = true;
								display.setText("");
								break;
							}
						display.setSelection(cursorCount);
					}
						
					break;
				
				case R.id.percentButton:
					handlePercentage(display, length, input);
					display.setSelection(cursorCount);
					break;

				default:
					
					if(computationDone)
						display.setText("");
					
					if(equalState)
						buttonIsAllowed = true;
					
					if(deleteIsAllowed == false)
						display.setText(deletedData + input);
					else					
						display.setText(display.getText() + input);
					
					decimalButtonState = true;
					signIsAllowed= true;
					checkData= true;
					computationDone = false;
					isPercentage = false;
					operationState = true;
					isNumber = true;				
					deleteIsAllowed = true;
					cursorCount++;
					display.setSelection(cursorCount);
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
				cursorCount = 0;
				buttonIsAllowed = false;
				signIsAllowed = false;
				percentageIsAllowed = true;
				counter = 0;
				decimalButtonState = false;
				clickCount = 0;
				isNumber = false;
				countNumericInputs(display);
				operationState = false;
				
				if(isANegativeNumber)
					secondDisplay.setText(secondDisplay.getText().toString() + "(" + display.getText().toString() + ")" + input);
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

				if(buttonIsAllowed){
					
					
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
						decimalButtonState = false;
						operationState = false;
						equalState= false;
						equationState = true;
						inputCount = 0;
						isPercentage = false;
						percentageData = 0.0;
						buttonIsAllowed = false;
						percentageIsAllowed = false;
						cursorCount = 0;
						counter = 0;
						
						display.setText("");
					}
	
					else {

						if (num % 1 == 0){
							if(isPercentage){
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + "(" + num.intValue() + ")" + "%" + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num.intValue() + "%" + " = ");
							}
							else{
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + "(" + num.intValue() + ")" + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num.intValue() + " = ");
							}
							
							display.setText(roundedData());
						}
						else{
							if(isPercentage){
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + "(" + num + ")" + "%" + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num + "%" + " = ");
							}
								
							else{
								if(isANegativeNumber)
									secondDisplay.setText(secondDisplay.getText().toString() + "(" + num + ")" + " = ");
								else
									secondDisplay.setText(secondDisplay.getText() + "" + num + " = ");
							}
							
							display.setText(roundedData());
						}
						
						display.setSelection(display.getText().length());
						
						operatorLog = 0;
						result = 0.0;
						num = 0.0;
						deleteIsAllowed = false;
						signIsAllowed = false;
						isNumber = true;
						decimalButtonState = false;
						operationState = true;
						equalState= false;
						equationState = true;
						inputCount = 0;
						isPercentage = false;
						percentageIsAllowed = false;
						buttonIsAllowed = false;
						percentageData = 0.0;
						cursorCount = 0;
						counter = 0;
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
		
		if((percentageIsAllowed)&&(buttonIsAllowed)){
			cursorCount++;
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


