package test.calculator;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Button_Click_Int {

	private Double num = 0.0;
	private Double result = 0.0;
	private int operatorLog = 0;
	private int inputCount = 0;
	private boolean isNumber = true;

	private boolean decimalButtonState = true;
	private boolean operationState = false;
	private boolean equalState = false;
	private boolean equationState = false;
	private boolean computationDone = false;
	private boolean isPercentage = false;

	MainActivity activity = new MainActivity();

	public void setDisplayValue(Button button, final EditText display, final EditText secondDisplay,
			final String input) {

		button.setOnClickListener(new View.OnClickListener() {

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
					if (decimalButtonState) {
						decimalButtonState = false;
						display.setText(display.getText() + input);					
					} else
						display.setText(display.getText());
					break;
					
				case R.id.clearButton:
					display.setText(input);
					secondDisplay.setText(input);
					break;
					
				case R.id.delButton:
					int length = display.getText().length();
					
					if(length >0){
						display.getText().delete(length-1, length);
					}
					
					break;
				
				case R.id.percentButton:
					isPercentage = true;					
					display.setText(display.getText().toString() + input);
					//mathematicalOperation(0, input, display, secondDisplay);
					break;

				default:
					
					if(computationDone)
						display.setText("");
					
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
				isNumber = false;
				countNumericInputs(display);
				operationState = false;				
				secondDisplay.setText(secondDisplay.getText().toString() + display.getText().toString() + input);
				display.setText("");
				equalState = true;
				operatorLog = code;
				
				if(equationState){
					secondDisplay.setText(roundedData() + input);
					display.setText("");
					equationState = false;
				}
					
			}	
	}

	public void countNumericInputs(EditText display) {
		int length = display.getText().length();
		
		if (!isNumber) {
			inputCount++;

			if (inputCount == 1) {
				
				if(isPercentage){
					display.getText().delete(length-1, length);
					result = Double.parseDouble(display.getText().toString())/100;
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
					num = Double.parseDouble(display.getText().toString());
					
					executeOperation(display);
					display.setText("");
	
					// check if zero(0) is a divider.
					if (Double.isInfinite(result)) {
						secondDisplay.setText("Syntax error");
						operatorLog = 0;
						result = 0.0;
						isNumber = true;
						decimalButtonState = true;
						operationState = false;
						equalState= false;
						equationState = true;
						inputCount = 0;
						display.setText("");
					}
	
					else {

						if (num % 1 == 0){
							secondDisplay.setText(secondDisplay.getText() + "" + num.intValue() + " = ");
							display.setText(roundedData());
						}
						else{
							secondDisplay.setText(secondDisplay.getText() + "" + num + " = ");
							display.setText(roundedData());
						}
	
						operatorLog = 0;
						result = 0.0;
						num = 0.0;
						isNumber = true;
						decimalButtonState = true;
						operationState = true;
						equalState= false;
						equationState = true;
						inputCount = 0;
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
				if(isPercentage)
					result += Double.parseDouble(display.getText().toString())/100;
				else
					result += Double.parseDouble(display.getText().toString());
				break;
			case 2:
				if(isPercentage)
					result -= Double.parseDouble(display.getText().toString())/100;
				else
					result -= Double.parseDouble(display.getText().toString());
				break;
			case 3:
				if(isPercentage)
					result *= Double.parseDouble(display.getText().toString())/100;
				else
					result *= Double.parseDouble(display.getText().toString());
				break;
			case 4:
				if(isPercentage)
					result /= Double.parseDouble(display.getText().toString())/100;
				else
					result /= Double.parseDouble(display.getText().toString());
				break;

			default:
				break;
		}
	}
	

}


