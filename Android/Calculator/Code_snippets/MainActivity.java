package com.calculator;

import android.app.Activity;
import android.graphics.Typeface;
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
		Button num2Button = (Button) findViewById(R.id.num2Button);
		Button num3Button = (Button) findViewById(R.id.num3Button);
		Button num4Button = (Button) findViewById(R.id.num4Button);
		Button num5Button = (Button) findViewById(R.id.num5Button);
		Button num6Button = (Button) findViewById(R.id.num6Button);
		Button num7Button = (Button) findViewById(R.id.num7Button);
		Button num8Button = (Button) findViewById(R.id.num8Button);
		Button num9Button = (Button) findViewById(R.id.num9Button);
		
		
		Button addButton = (Button) findViewById(R.id.addButton);
		Button subtractButton = (Button)findViewById(R.id.subtractButton);
		Button multiplyButton = (Button)findViewById(R.id.multiplyButton);
		Button divideButton = (Button) findViewById(R.id.divideButton);		
		Button equalsButton = (Button) findViewById(R.id.equalsButton);
		
		Button delButton = (Button) findViewById(R.id.delButton);
		Button decimalButton = (Button)findViewById(R.id.decimalButton);
		Button clearButton = (Button)findViewById(R.id.clearButton);
		Button signButton = (Button)findViewById(R.id.signButton);
		Button percentageButton = (Button)findViewById(R.id.percentageButton);

		final EditText editTextInput = (EditText) findViewById(R.id.editTextInput);
		final EditText editTextEquation = (EditText) findViewById(R.id.editTextEquation);
		
		editTextInput.setKeyListener(null);
		editTextEquation.setKeyListener(null);
		
		Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/Istok_regular.ttf");
		editTextInput.setTypeface(typeface);
		editTextEquation.setTypeface(typeface);
		
		ButtonFunctions function = new ButtonFunctions();
		
		function.click(num0Button, editTextInput, editTextEquation, "0");
		function.click(num1Button, editTextInput, editTextEquation, "1");
		function.click(num2Button, editTextInput, editTextEquation, "2");
		function.click(num3Button, editTextInput, editTextEquation, "3");
		function.click(num4Button, editTextInput, editTextEquation, "4");
		function.click(num5Button, editTextInput, editTextEquation, "5");
		function.click(num6Button, editTextInput, editTextEquation, "6");
		function.click(num7Button, editTextInput, editTextEquation, "7");
		function.click(num8Button, editTextInput, editTextEquation, "8");
		function.click(num9Button, editTextInput, editTextEquation, "9");
		
		function.click(addButton, editTextInput, editTextEquation, "+");
		function.click(subtractButton, editTextInput, editTextEquation, "-");
		function.click(multiplyButton, editTextInput, editTextEquation, "x");
		function.click(divideButton, editTextInput, editTextEquation, "÷");		
		function.click(equalsButton, editTextInput, editTextEquation, "=");
		
		function.click(delButton, editTextInput, editTextEquation, "");
		function.click(decimalButton, editTextInput, editTextEquation, "");
		function.click(clearButton, editTextInput, editTextEquation, "");
		function.click(signButton, editTextInput, editTextEquation, "");
		function.handlePercentage(percentageButton, editTextInput, editTextEquation);
		

	}
	
	
}
