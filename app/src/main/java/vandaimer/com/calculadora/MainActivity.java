package vandaimer.com.calculadora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private boolean lastIsNumber = false;
    private static final String PATTERN = "(\\d*) ([/\\+\\*-]) (\\d*)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView)findViewById(R.id.tv_resultado);
        createListenerToNumbersButton();
        createListenerToOperatorsButton();
    }

    public void reset(View v){
        result.setText("0");
        lastIsNumber = false;
    }

    public void setResult(View v){
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(result.getText().toString());
        if(m.find()) {
            double result = calculate(m);
            this.result.setText(String.valueOf(result));
        }
    }

    private Double calculate(Matcher toCalculate){
        double first, second, result = 0;
        String operator;

        first = Double.parseDouble(toCalculate.group(1));
        second = Double.parseDouble(toCalculate.group(3));
        operator = toCalculate.group(2);

        switch (operator) {
            case "-":
                result = first - second;
                break;
            case "+":
                result = first + second;
                break;
            case "/":
                result = first / second;
                break;
            case "*":
                result = first * second;
                break;
        }
        return result;
    }


    private void createListenerToOperatorsButton() {
        int idButtons[] = {
                R.id.bt_adicao,
                R.id.bt_subtracao,
                R.id.bt_divisao,
                R.id.bt_multiplicacao,
        };
        for(int x:idButtons){
            final Button bt = (Button)findViewById(x);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastIsNumber){
                        Pattern r = Pattern.compile(PATTERN);
                        Matcher m = r.matcher(result.getText().toString());
                        String newResult = "";
                        if(m.find()){
                            double result = calculate(m);
                            newResult = result + " " + bt.getText().toString();
                        } else {
                            newResult = result.getText().toString() + " " + bt.getText().toString();
                        }
                        result.setText(newResult);
                        lastIsNumber = false;
                    }
                }
            });
        }
    }

    private void createListenerToNumbersButton() {
        int idButtons[] = {
                R.id.bt_um,
                R.id.bt_dois,
                R.id.bt_tres,
                R.id.bt_quatro,
                R.id.bt_cinco,
                R.id.bt_seis,
                R.id.bt_sete,
                R.id.bt_oito,
                R.id.bt_nove,
                R.id.bt_zero,
        };
        for(int x:idButtons){
            final Button bt = (Button)findViewById(x);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(result.getText().toString().equals("0")){
                        result.setText(bt.getText().toString());
                    } else {
                        String newText = result.getText().toString();
                        if (!lastIsNumber) {
                            newText += " ";
                        }
                        newText += bt.getText().toString();
                        result.setText(newText);
                    }
                    lastIsNumber = true;
                }
            });
        }
    }
}