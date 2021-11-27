package com.savio.bitcircuit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView xor_1Comparion,xor_2Comparion,and_1Comparion,and_2Comparion,orComparion;
    private TextView xor_1Output,xor_2Output,and_1Output,and_2Output,orOutput,carryOutPut,bitOutPut;
    private CheckBox chkCarry,chkBit_1,chkBit_2;
    private String bit_1,bit_2,carry;
    private int delay;
    private Button btnStart;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        xor_1Comparion = findViewById(R.id.txt_xor_1);
        xor_2Comparion = findViewById(R.id.txt_xor_2);
        xor_1Output = findViewById(R.id.txt_result_xor_1);
        xor_2Output = findViewById(R.id.txt_result_xor_2);

        and_1Comparion = findViewById(R.id.txt_and_1);
        and_2Comparion = findViewById(R.id.txt_and_2);
        and_1Output = findViewById(R.id.txt_result_and_1);
        and_2Output = findViewById(R.id.txt_result_and_2);

        orComparion = findViewById(R.id.txt_or);
        orOutput = findViewById(R.id.txt_result_or);

        carryOutPut = findViewById(R.id.txt_carry);
        bitOutPut = findViewById(R.id.txt_bit);

        chkBit_1 = findViewById(R.id.check_a);
        chkBit_2 = findViewById(R.id.check_b);
        chkCarry = findViewById(R.id.check_carry_1);

        bit_1 = "0";
        bit_2 = "0";
        carry = "0";
        delay = 0;
        handler = new Handler();
        getSupportActionBar().hide();

        chkCarry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carry = isChecked?"1":"0";
            }
        });
        chkBit_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bit_1 = isChecked?"1":"0";
                Log.e("teste",bit_1+"");
            }
        });
        chkBit_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bit_2 = isChecked?"1":"0";
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                delay = 0;
                firstRotine();
            }
        });

    }
    private void reset(){

    }
    private void firstRotine(){
        xor_1Comparion.setText(bit_1 + " xor " + bit_2);
        and_1Comparion.setText(bit_1 + " and " + bit_2);

        xor_1Output.setText(  (!bit_1.equals(bit_2 )) ?"1":"0");
        and_1Output.setText((bit_1.equals(bit_2 ) && bit_2.equals("1")) ?"1":"0");

        secondRotine();
    }
    private void secondRotine(){
        xor_2Comparion.setText(carry + " xor " + xor_1Output.getText());
        and_2Comparion.setText(carry + " and " + xor_1Output.getText());

        xor_2Output.setText( (!carry.equals(xor_1Output.getText())   )?"1":"0");
        and_2Output.setText( (carry.equals(xor_1Output.getText()) && carry.equals("1"))?"1":"0");

        ThirtRotine();
    }
    private void ThirtRotine(){
        carryOutPut.setText(xor_2Output.getText());
        orComparion.setText(and_2Output.getText() + " or " + and_1Output.getText());

        orOutput.setText( (and_2Output.getText().equals("1") || and_1Output.getText().equals("1"))?"1":"0");

        quatroRotine();

    }
    private void quatroRotine(){
        bitOutPut.setText(orOutput.getText());
    }

}