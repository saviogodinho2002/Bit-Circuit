package com.savio.bitcircuit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView xor_1Comparion, xor_2Comparion, and_1Comparion, and_2Comparion, orComparion;
    private TextView xor_1Output, xor_2Output, and_1Output, and_2Output, orOutput, sumOutPut, carryOutPut;
    private TextView instrucions;
    private CheckBox chkCarry, chkBit_1, chkBit_2;
    private String bit_1, bit_2, carry;
    private int delay;
    private Button btnStart, btnNext, btnBefore,btnReset;
    private ImageView arrow_Xor_1, arrow_Xor_2, arrow_And_1, arrow_And_2, arrow_Or, arrow_Carry, arrow_Sum;
    private int indexStage;
    private Handler handler;

    private LogicGate andGate_1,andGate_2,orGate,xorGate_1,xorGate_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btn_start);
        btnBefore = findViewById(R.id.btn_ant_step);
        btnNext = findViewById(R.id.btn_next_step);
        btnReset = findViewById(R.id.btn_reset);
        instrucions = findViewById(R.id.txt_text_descri);

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

        sumOutPut = findViewById(R.id.txt_carry);
        carryOutPut = findViewById(R.id.txt_bit);

        chkBit_1 = findViewById(R.id.check_a);
        chkBit_2 = findViewById(R.id.check_b);
        chkCarry = findViewById(R.id.check_carry_1);

        arrow_And_1 = findViewById(R.id.img_seta_and_1);
        arrow_And_2 = findViewById(R.id.img_seta_and_2);
        arrow_Xor_1 = findViewById(R.id.img_seta_xor_1);
        arrow_Xor_2 = findViewById(R.id.img_seta_xor_2);
        arrow_Or = findViewById(R.id.img_seta_or);
        arrow_Carry = findViewById(R.id.img_seta_carryout);
        arrow_Sum = findViewById(R.id.img_seta_soma);



        resetAll();
        bit_1 = "0";
        bit_2 = "0";
        carry = "0";

        andGate_1 = new AndGate(bit_1,bit_2);
        xorGate_1 = new XorGate(bit_1,bit_2);

        andGate_2 = new AndGate(carry, xorGate_1.getDataExit());
        xorGate_2 = new XorGate(carry, xorGate_1.getDataExit());

        orGate = new OrGate(andGate_2.getDataExit(),andGate_1.getDataExit());

        indexStage = 0;
        delay = 0;
        handler = new Handler();
        getSupportActionBar().hide();

        instrucions.setText("*CORES\n\uD83D\uDFE1 ANALIZANDO\n\uD83D\uDFE2 VERDADEIRO \n\uD83D\uDD34 FALSO \n*Sobre o Circuito:\nencurtador.com.br/knJ37");

        chkCarry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carry = isChecked ? "1" : "0";
            }
        });
        chkBit_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bit_1 = isChecked ? "1" : "0";
                Log.e("teste", bit_1 + "");
            }
        });
        chkBit_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bit_2 = isChecked ? "1" : "0";
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                delay = 0;
                resetAll();
                execAnsync();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                delay = 0;
                indexStage++;
                Log.e("teste", indexStage + "");
                if (indexStage > 7) {
                     indexStage = 7;
                    Toast.makeText(MainActivity.this, "Nenhum passo a frente", Toast.LENGTH_SHORT).show();
                } else {
                    exeSyncNextStep();
                }

            }
        });
        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                delay = 0;
                indexStage--;
                Log.e("teste", indexStage + "");
                if (indexStage <= 0) {
                    exexSyncBeforeStep();
                    Toast.makeText(MainActivity.this, "Nenhum passo a atrás", Toast.LENGTH_SHORT).show();
                    indexStage = 0;
                } else {
                    exexSyncBeforeStep();
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                delay = 0;
                resetAll();
            }
        });

    }

    private void resetAll() {
        indexStage = 0;
        startStage(xor_1Comparion,xor_1Output, arrow_Xor_1);
        startStage(xor_2Comparion,xor_2Output, arrow_Xor_2);

        startStage(sumOutPut,arrow_Sum);
        startStage(carryOutPut,arrow_Carry);

        startStage(and_2Comparion,and_2Output, arrow_And_2);
        startStage(and_1Comparion,and_1Output, arrow_And_1);
        startStage(orComparion,orOutput, arrow_Or);

        startStage(sumOutPut,arrow_Sum);
        startStage(carryOutPut,arrow_Carry);

        xor_1Comparion.setText("0 xor 0");
        xor_2Comparion.setText("0 xor 0");
        and_2Comparion.setText("0 and 0");
        and_1Comparion.setText("0 and 0");
        orComparion.setText("0 or 0");


    }

    private void exeSyncNextStep() {
        switch (indexStage) {
            case 1:
                stageOne();
                break;
            case 2:
                stageTwo();
                break;
            case 3:
                stageThree();
                break;
            case 4:
                stageFour();
                break;
            case 5:
                stageFive();
                break;
            case 6:
                stageSix();
                break;
            case 7:
                stageSeven();
                break;
        }
    }

    private void exexSyncBeforeStep() {
        switch (indexStage) {
            case 0:
                //stageOne();
                resetStageOne();
                break;
            case 1:
                resetStageTwo();
                break;
            case 2:
                resetStageThree();
                break;
            case 3:
                resetStageFour();
                break;
            case 4:
                resetStageFive();
                break;
            case 5:
                resetStageSix();
                break;
            case 6:
                resetStageSeven();
                break;
        }
    }

    private void execAnsync() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageOne(); indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageTwo();indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageThree();indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageFour();indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageFive();indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageSix();indexStage++;
            }
        }, delay += 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageSeven();indexStage++;
            }
        }, delay += 2000);


    }
    private void startStage(TextView txtComparation, TextView txtOutPut, ImageView imgArrow_1){
       txtComparation.setTextColor(Color.WHITE);
       txtOutPut.setTextColor(Color.WHITE);
       txtOutPut.setBackgroundColor(Color.BLACK);
       txtOutPut.setText("0");
       imgArrow_1.setVisibility(View.INVISIBLE);
    }
    private void startStage( TextView txtOutPut, ImageView imgArrow_1){
        txtOutPut.setTextColor(Color.BLACK);
        txtOutPut.setBackgroundColor(Color.WHITE);
        txtOutPut.setText("0");
        imgArrow_1.setVisibility(View.INVISIBLE);
    }
    private void comparationStage(TextView txtComparation, TextView txtOutPut, ImageView imgArrow){
        txtComparation.setTextColor(Color.YELLOW);
        txtOutPut.setText("0");
        txtOutPut.setTextColor(Color.WHITE);
        txtOutPut.setBackgroundColor(Color.BLACK);
        imgArrow.setVisibility(View.VISIBLE);
    }

    private void resultStage(TextView txtComparation, TextView txtOutPut, ImageView imgArrow,String outPutStage){
        imgArrow.setVisibility(View.VISIBLE);
        if(outPutStage.equals("1")){
            txtComparation.setTextColor(Color.GREEN);
            txtOutPut.setBackgroundColor(Color.CYAN);
            txtOutPut.setText(outPutStage);
            txtOutPut.setTextColor(Color.WHITE);
        }else {
            txtComparation.setTextColor(Color.RED);
            txtOutPut.setBackgroundColor(Color.BLACK);
            txtOutPut.setText(outPutStage);
            txtOutPut.setTextColor(Color.WHITE);
        }
    }
    private void resultStage( TextView txtOutPut, ImageView imgArrow,String outPutStage){
        imgArrow.setVisibility(View.VISIBLE);
        if(outPutStage.equals("1")){

            txtOutPut.setBackgroundColor(Color.CYAN);
            txtOutPut.setText(outPutStage);
            txtOutPut.setTextColor(Color.WHITE);
        }else {
            txtOutPut.setBackgroundColor(Color.WHITE);
            txtOutPut.setText(outPutStage);
            txtOutPut.setTextColor(Color.BLACK);
        }
    }

    private void resetStageOne() {
        xor_1Comparion.setText("0 xor 0");
        and_1Comparion.setText("0 and 0");

        startStage(xor_1Comparion,xor_1Output,arrow_Xor_1);
        startStage(and_1Comparion,and_1Output,arrow_And_1);

    }


    private void stageOne() {

        xor_1Comparion.setText(bit_1 + " xor " + bit_2);
        and_1Comparion.setText(bit_1 + " and " + bit_2);
        comparationStage(xor_1Comparion,xor_1Output,arrow_Xor_1);
        comparationStage(and_1Comparion,and_1Output,arrow_And_1);

    }

    private void resetStageTwo() {
        stageOne();

    }

    private void stageTwo() {

        xorGate_1 = new XorGate(bit_1,bit_2);
        andGate_1 = new AndGate(bit_1,bit_2);

        resultStage(xor_1Comparion,xor_1Output,arrow_Xor_1,xorGate_1.getDataExit());
        resultStage(and_1Comparion,and_1Output,arrow_And_1,andGate_1.getDataExit());

    }

    private void resetStageThree() {
        stageTwo();
        startStage(xor_2Comparion,xor_2Output,arrow_Xor_2);
        startStage(and_2Comparion,and_2Output,arrow_And_2);

        xor_2Comparion.setText("0 xor 0");
        and_2Comparion.setText("0 and 0");

    }

    private void stageThree() {

        arrow_Xor_1.setVisibility(View.INVISIBLE);
        arrow_And_1.setVisibility(View.INVISIBLE);
        xor_2Comparion.setText(carry + " xor " + xor_1Output.getText());
        and_2Comparion.setText(carry + " and " + xor_1Output.getText());
        //
        comparationStage(xor_2Comparion,xor_2Output,arrow_Xor_2);
        comparationStage(and_2Comparion,and_2Output,arrow_And_2);


    }

    private void resetStageFour() {
        stageThree();

    }

    private void stageFour() {
        andGate_2 = new AndGate(carry,xorGate_1.getDataExit());
        xorGate_2 = new XorGate(carry,xorGate_1.getDataExit());

        resultStage(xor_2Comparion,xor_2Output,arrow_Xor_2,xorGate_2.getDataExit());
        resultStage(and_2Comparion,and_2Output,arrow_And_2,andGate_2.getDataExit());

    }


    private void resetStageFive() {
        stageFour();
        startStage(orComparion,orOutput,arrow_Or);
        startStage(sumOutPut,arrow_Sum);
        Log.e("teste","resetando 7");
        orComparion.setText("0 or 0");

    }

    private void stageFive() {
        arrow_Xor_2.setVisibility(View.INVISIBLE);
        arrow_And_2.setVisibility(View.INVISIBLE);


        sumOutPut.setText(xorGate_2.getDataExit());
        orComparion.setText(and_2Output.getText() + " or " + and_1Output.getText());

        comparationStage(orComparion,orOutput,arrow_Or);
        resultStage(sumOutPut,arrow_Sum,xorGate_2.getDataExit());

    }

    private void resetStageSix() {
        stageFive();
    }

    private void stageSix() {

        boolean finalBitOne = (and_2Output.getText().equals("1") || and_1Output.getText().equals("1"));

        orGate = new OrGate(andGate_2.getDataExit(),andGate_1.getDataExit());
        resultStage(orComparion,orOutput,arrow_Or,orGate.getDataExit());

        arrow_Sum.setVisibility(View.INVISIBLE);

    }

    private void resetStageSeven() {
        stageSix();
        startStage(carryOutPut,arrow_Carry);

    }

    private void stageSeven() {
        resultStage(carryOutPut,arrow_Carry,orGate.getDataExit());

    }

}