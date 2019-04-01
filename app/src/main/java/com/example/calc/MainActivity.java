package com.example.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EmptyStackException;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    short tmp = 0;
    boolean isComa = false, isDiv = false, isPlus = false, isMul = false, isSub = false;
    final String KEY = "text_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //numeric keyboard
        Button button_0 = findViewById(R.id.button_0);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        Button button_5 = findViewById(R.id.button_5);
        Button button_6 = findViewById(R.id.button_6);
        Button button_7 = findViewById(R.id.button_7);
        Button button_8 = findViewById(R.id.button_8);
        Button button_9 = findViewById(R.id.button_9);

        button_0.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {   // 0,1...9
        TextView display = findViewById(R.id.display);
        Button button = (Button) v;
        if(tmp==1){
            display.append(button.getText());
            resetBoolean(false);
        }
        else{
            if(display.getText().charAt(display.getText().length()-1) == ','){
                display.append(button.getText());
                resetBoolean(false);
            }
            else{
                display.setText(button.getText());
                resetBoolean(false);
            }
            tmp=1;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView display = findViewById(R.id.display);
        String s = savedInstanceState.getString(KEY);
        display.setText(s);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView display = findViewById(R.id.display);
        outState.putString(KEY, display.getText().toString());
    }

    // AC
    public void clearDisplay(View view) {
        TextView display = findViewById(R.id.display);
        display.setText("0");
        tmp=0;
    }
    // +/-
    public void changeSign(View view) {
        TextView display = findViewById(R.id.display);
        if(display.getText().charAt(0)=='-')
            display.setText(display.getText().toString().substring(1));
        else
            display.setText("-" + display.getText().toString());
    }
    // %
    public void makePercentage(View view) {
        TextView display = findViewById(R.id.display);
        Double number = Double.parseDouble(display.getText().toString())/100;
        display.setText(number.toString());
    }
    // ,
    public void makeComa(View view) {
        TextView display = findViewById(R.id.display);
        if(!isComa){
           display.setText(display.getText().toString() + ",");
           isComa = true;
        }
    }
    // =
    public void showResult(View view) {
        TextView display = findViewById(R.id.display);
        ReversePolishNotation rpn = new ReversePolishNotation();
        String rep = display.getText().toString().replace(",",".");

        try{

            Double a = Double.parseDouble(rpn.convertToOnp(rep));
            Double result = BigDecimal.valueOf(a).setScale(2, RoundingMode.HALF_DOWN).doubleValue();

            String readyString = result.toString().replace(".",",");
            display.setText(readyString);

        }catch(EmptyStackException e){
            display.setText(R.string.empty_string_Excpetion);
        }catch(ArithmeticException e){
            display.setText(R.string.div_by_0_exception);
        }catch(NumberFormatException e){
            display.setText(R.string.empty_string_Excpetion);
        }finally {
            resetBoolean(true);
        }
    }

    public void division(View view) {
        TextView display = findViewById(R.id.display);
        if(!isDiv){
            if(display.getText().charAt(display.getText().length()-1) == ',')
                display.append("0 / ");
            else
                display.append(" / ");
            isDiv = true;
            isComa = false;
        }
    }

    public void multiplication(View view) {
        TextView display = findViewById(R.id.display);
        if(!isMul){
            if(display.getText().charAt(display.getText().length()-1) == ',')
                display.append("0 * ");
            else
                display.append(" * ");
            isMul = true;
            isComa = false;
        }
    }

    public void subtraction(View view) {
        TextView display = findViewById(R.id.display);
        if(!isSub){
            if(display.getText().charAt(display.getText().length()-1) == ',')
                display.append("0 - ");
            else
            display.append(" - ");
            isSub = true;
            isComa = false;
        }
    }

    public void addition(View view) {
        TextView display = findViewById(R.id.display);
        if(!isPlus){
            if(display.getText().charAt(display.getText().length()-1) == ',')
                display.append("0 + ");
            else
                display.append(" + ");
            isPlus = true;
            isComa = false;
        }
    }

    private void resetBoolean(Boolean all ){
        if(all){
            isComa = false;
            isDiv = false;
            isPlus = false;
            isMul = false;
            isSub = false;
        }else{
            isDiv = false;
            isPlus = false;
            isMul = false;
            isSub = false;
        }

    }

    public void makeSqrt(View view) {
        TextView display = findViewById(R.id.display);
        double a = Math.sqrt( Integer.parseInt( display.getText().toString() ) ) ;
        display.setText( String.format(Locale.getDefault(),"%.2f", a) );
    }

    public void makePowThree(View view) {
        TextView display = findViewById(R.id.display);
        double a = Math.pow( Integer.parseInt( display.getText().toString() ), 3 );
        display.setText( String.format(Locale.getDefault(),"%.2f", a) );
    }

    public void makePowTwo(View view) {
        TextView display = findViewById(R.id.display);
        double a = Math.pow( Integer.parseInt( display.getText().toString() ), 2 );
        display.setText( String.format(Locale.getDefault(),"%.2f", a) );
    }

    public void factorials(View view) {
        TextView display = findViewById(R.id.display);
        long f = doFactorial(Integer.parseInt( display.getText().toString()));
        display.setText( String.format(Locale.getDefault(),"%d", f) );
    }

    public void MakeLogTen(View view) {
        TextView display = findViewById(R.id.display);
        double a =  Math.log10( Integer.parseInt( display.getText().toString() ) );
        display.setText( String.format(Locale.getDefault(),"%.2f", a) );
    }

    private long doFactorial(int n) {
        if (n <= 2) {
            return n;
        }
        return n * doFactorial(n - 1);
    }
}
