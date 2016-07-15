package com.code_snippets;


import com.code_snippets.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Double computedResult = 0.0; 						// computedResult variable holds the result of the computation.
	private boolean numberWasPressed = false;					// states if a number was pressed.
	private boolean operationWasPressed = false; 				// states if the operation signs were pressed.
	private boolean initialState = true; 						// states if the calculator is in its initial State.
	private boolean mathOperationIsAllowed = false; 			// states if it is allowed to press the mathematical operators.
	private boolean isButtonAllowed = false;					// states if it is allowed to press a certain button.
	private boolean isComputationDone = false;					// states if the computation has been done.
	private boolean refresh = false;							// states if it s needed to refresh the status of an object.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button num1Button = (Button) findViewById(R.id.num1Button);
		Button addButton = (Button) findViewById(R.id.addButton);
		Button equalsButton = (Button) findViewById(R.id.equalsButton);
		Button delButton = (Button) findViewById(R.id.delButton);
		Button decimalButton = (Button)findViewById(R.id.decimalButton);

		final EditText editTextNumbers = (EditText) findViewById(R.id.editTextNumbers);
		final EditText editTextEquation = (EditText) findViewById(R.id.editTextEquation);
		
		ButtonFunctions function = new ButtonFunctions();
		
		function.click(num1Button, editTextNumbers, editTextEquation, "1");
		function.click(addButton, editTextNumbers, editTextEquation, "+");
		function.click(equalsButton, editTextNumbers, editTextEquation, "");
		function.click(delButton, editTextNumbers, editTextEquation, "");
		function.click(decimalButton, editTextNumbers, editTextEquation, "");
		

	}
	
	
}
