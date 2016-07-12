package test.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
	
	EditText displayNum, displayEquation; 
	
	Button 
	num0Button, 
	num1Button, 
	num2Button, 
	num3Button, 
	num4Button,
	num5Button,
	num6Button,
	num7Button,
	num8Button,
	num9Button,
	addButton,
	subtractButton,
	multiplyButton,
	divideButton,
	equalsButton,
	periodButton,
	clearButton,
	percentageButton,
	signButton;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		Button_Click_Int buttonClick = new Button_Click_Int();
		
		displayNum = (EditText)findViewById(R.id.editTextNum);
		displayEquation = (EditText)findViewById(R.id.editTextEquation);
		
		displayNum.setKeyListener(null);
		displayEquation.setKeyListener(null);

		//numbers
		num0Button = (Button)findViewById(R.id.num0Button);
		num1Button = (Button)findViewById(R.id.num1Button);
		num2Button = (Button)findViewById(R.id.num2Button);
		num3Button = (Button)findViewById(R.id.num3Button);
		num4Button = (Button)findViewById(R.id.num4Button);
		num5Button = (Button)findViewById(R.id.num5Button);
		num6Button = (Button)findViewById(R.id.num6Button);
		num7Button = (Button)findViewById(R.id.num7Button);
		num8Button = (Button)findViewById(R.id.num8Button);
		num9Button = (Button)findViewById(R.id.num9Button);
		
		//operations
		addButton = (Button)findViewById(R.id.addButton);
		subtractButton = (Button)findViewById(R.id.subtractButton);
		multiplyButton = (Button)findViewById(R.id.multiplyButton);
		divideButton = (Button)findViewById(R.id.divideButton);
		equalsButton = (Button)findViewById(R.id.equalsButton);
		periodButton = (Button)findViewById(R.id.decimalButton);
		clearButton = (Button)findViewById(R.id.clearButton);
		percentageButton = (Button)findViewById(R.id.percentButton);
		signButton = (Button)findViewById(R.id.signButton);
		

		//numbers
		buttonClick.setDisplayValue(num0Button, displayNum, displayEquation, "0");
		buttonClick.setDisplayValue(num1Button, displayNum, displayEquation, "1");
		buttonClick.setDisplayValue(num2Button, displayNum, displayEquation, "2");
		buttonClick.setDisplayValue(num3Button, displayNum, displayEquation, "3");
		buttonClick.setDisplayValue(num4Button, displayNum, displayEquation, "4");
		buttonClick.setDisplayValue(num5Button, displayNum, displayEquation, "5");
		buttonClick.setDisplayValue(num6Button, displayNum, displayEquation, "6");
		buttonClick.setDisplayValue(num7Button, displayNum, displayEquation, "7");
		buttonClick.setDisplayValue(num8Button, displayNum, displayEquation, "8");
		buttonClick.setDisplayValue(num9Button, displayNum, displayEquation, "9");
		
		//operations
		buttonClick.setDisplayValue(addButton, displayNum, displayEquation, "+");
		buttonClick.setDisplayValue(subtractButton, displayNum, displayEquation, "-");
		buttonClick.setDisplayValue(multiplyButton, displayNum, displayEquation, "x");
		buttonClick.setDisplayValue(divideButton, displayNum, displayEquation, "÷");
		buttonClick.setDisplayValue(periodButton, displayNum, displayEquation,  ".");
		buttonClick.setDisplayValue(clearButton, displayNum, displayEquation,  "");
		buttonClick.setDisplayValue(percentageButton, displayNum, displayEquation, "%");
		buttonClick.setDisplayValue(signButton, displayNum, displayEquation, "");
		buttonClick.compute(equalsButton, displayNum,displayEquation);
		
	}
}
