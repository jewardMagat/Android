package com.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button num1Button = (Button) findViewById(R.id.num1Button);
		Button addButton = (Button) findViewById(R.id.addButton);
		Button equalsButton = (Button) findViewById(R.id.equalsButton);
		Button delButton = (Button) findViewById(R.id.delButton);
		Button decimalButton = (Button)findViewById(R.id.decimalButton);
		Button clearButton = (Button)findViewById(R.id.clearButton);
		Button signButton = (Button)findViewById(R.id.signButton);

		final EditText editTextNumbers = (EditText) findViewById(R.id.editTextNumbers);
		final EditText editTextEquation = (EditText) findViewById(R.id.editTextEquation);
		
		ButtonFunctions function = new ButtonFunctions();
		
		function.click(num1Button, editTextNumbers, editTextEquation, "1");
		function.click(addButton, editTextNumbers, editTextEquation, "+");
		function.click(equalsButton, editTextNumbers, editTextEquation, "");
		function.click(delButton, editTextNumbers, editTextEquation, "");
		function.click(decimalButton, editTextNumbers, editTextEquation, "");
		function.click(clearButton, editTextNumbers, editTextEquation, "");
		function.click(signButton, editTextNumbers, editTextEquation, "");
		

	}
	
	
}
