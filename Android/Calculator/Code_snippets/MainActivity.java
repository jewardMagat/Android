package com.calculator;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button num0Button = (Button) findViewById(R.id.num0Button);
		Button num1Button = (Button) findViewById(R.id.num1Button);
		Button addButton = (Button) findViewById(R.id.addButton);
		Button divideButton = (Button) findViewById(R.id.divideButton);
		Button equalsButton = (Button) findViewById(R.id.equalsButton);
		Button delButton = (Button) findViewById(R.id.delButton);
		Button decimalButton = (Button)findViewById(R.id.decimalButton);
		Button clearButton = (Button)findViewById(R.id.clearButton);
		Button signButton = (Button)findViewById(R.id.signButton);
		Button percentageButton = (Button)findViewById(R.id.percentageButton);

		final EditText editTextNumbers = (EditText) findViewById(R.id.editTextNumbers);
		final EditText editTextEquation = (EditText) findViewById(R.id.editTextEquation);
		
		ButtonFunctions function = new ButtonFunctions();
		
		function.click(num0Button, editTextNumbers, editTextEquation, "0");
		function.click(num1Button, editTextNumbers, editTextEquation, "1");
		
		function.click(addButton, editTextNumbers, editTextEquation, "+");
		function.click(divideButton, editTextNumbers, editTextEquation, "/");
		
		function.click(equalsButton, editTextNumbers, editTextEquation, "=");
		function.click(delButton, editTextNumbers, editTextEquation, "");
		function.click(decimalButton, editTextNumbers, editTextEquation, "");
		function.click(clearButton, editTextNumbers, editTextEquation, "");
		function.click(signButton, editTextNumbers, editTextEquation, "");
		function.handlePercentage(percentageButton, editTextNumbers, editTextEquation);
		

	}
	
	
}
