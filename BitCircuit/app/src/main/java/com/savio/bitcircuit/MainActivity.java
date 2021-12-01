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
    private TextView xor_1Output, xor_2Output, and_1Output, and_2Output, orOutput, carryOutPut, bitOutPut;
    private TextView instrucions;
    private CheckBox chkCarry, chkBit_1, chkBit_2;
    private String bit_1, bit_2, carry;
    private int delay;
    private Button btnStart, btnNext, btnBefore,btnReset;
    private ImageView setaXor_1, setaXor_2, setaAnd_1, setaAnd_2, setaOr, setaCarry, setaSoma;
    private int indexStage;
    private Handler handler;
    private List<String> descript;

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

        carryOutPut = findViewById(R.id.txt_carry);
        bitOutPut = findViewById(R.id.txt_bit);

        chkBit_1 = findViewById(R.id.check_a);
        chkBit_2 = findViewById(R.id.check_b);
        chkCarry = findViewById(R.id.check_carry_1);

        setaAnd_1 = findViewById(R.id.img_seta_and_1);
        setaAnd_2 = findViewById(R.id.img_seta_and_2);
        setaXor_1 = findViewById(R.id.img_seta_xor_1);
        setaXor_2 = findViewById(R.id.img_seta_xor_2);
        setaOr = findViewById(R.id.img_seta_or);
        setaCarry = findViewById(R.id.img_seta_carryout);
        setaSoma = findViewById(R.id.img_seta_soma);
        resetAll();
        bit_1 = "0";
        bit_2 = "0";
        carry = "0";
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
        xor_1Comparion.setTextColor(Color.WHITE);
        and_1Comparion.setTextColor(Color.WHITE);
        xor_2Comparion.setTextColor(Color.WHITE);
        and_2Comparion.setTextColor(Color.WHITE);
        orComparion.setTextColor(Color.WHITE);

        xor_1Comparion.setText("0 xor 0");
        xor_2Comparion.setText("0 xor 0");
        and_2Comparion.setText("0 and 0");
        and_1Comparion.setText("0 and 0");
        orComparion.setText("0 or 0");

        xor_2Output.setText("0");
        xor_1Output.setText("0");
        and_2Output.setText("0");
        and_1Output.setText("0");
        orOutput.setText("0");

        xor_2Output.setTextColor(Color.WHITE);
        xor_1Output.setTextColor(Color.WHITE);
        and_2Output.setTextColor(Color.WHITE);
        and_1Output.setTextColor(Color.WHITE);
        orOutput.setTextColor(Color.WHITE);

        xor_1Output.setBackgroundColor(Color.BLACK);
        and_1Output.setBackgroundColor(Color.BLACK);
        xor_2Output.setBackgroundColor(Color.BLACK);
        and_2Output.setBackgroundColor(Color.BLACK);
        orOutput.setBackgroundColor(Color.BLACK);

        carryOutPut.setBackgroundColor(Color.WHITE);
        bitOutPut.setBackgroundColor(Color.WHITE);

        carryOutPut.setText("0");
        bitOutPut.setText("0");

        setaSoma.setVisibility(View.INVISIBLE);
        setaCarry.setVisibility(View.INVISIBLE);
        setaOr.setVisibility(View.INVISIBLE);
        setaXor_1.setVisibility(View.INVISIBLE);
        setaXor_2.setVisibility(View.INVISIBLE);
        setaAnd_1.setVisibility(View.INVISIBLE);
        setaAnd_2.setVisibility(View.INVISIBLE);
        /*
         int delay = 0;   // delay de 2 seg.
        int interval = 1000;  // intervalo de 1 seg.

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    String texto =  binario;
                    txtHistoric.setText(texto);
                    timer.cancel();
                    Log.e("teste",binario);
                }
            }, delay+=2000,0);
            Log.e("teste",binario);
        }
        DELAYYY
         */

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

    private void resetStageOne() {
        //seria voltar para o  0

        xor_1Comparion.setText("0 xor 0");
        and_1Comparion.setText("0 and 0");

        xor_1Output.setText("0");
        and_1Output.setText("0");

        setaXor_1.setVisibility(View.INVISIBLE);
        setaAnd_1.setVisibility(View.INVISIBLE);

        xor_1Output.setBackgroundColor(Color.BLACK);
        and_1Output.setBackgroundColor(Color.BLACK);

        xor_1Comparion.setTextColor(Color.WHITE);
        and_1Comparion.setTextColor(Color.WHITE);


        xor_1Output.setTextColor(Color.WHITE);
        and_1Output.setTextColor(Color.WHITE);

    }


    private void stageOne() {

        xor_1Comparion.setText(bit_1 + " xor " + bit_2);
        and_1Comparion.setText(bit_1 + " and " + bit_2);

        xor_1Output.setText("0");
        and_1Output.setText("0");

        xor_1Output.setBackgroundColor(Color.BLACK);
        and_1Output.setBackgroundColor(Color.BLACK);

        xor_1Comparion.setTextColor(Color.YELLOW);
        and_1Comparion.setTextColor(Color.YELLOW);

        setaXor_1.setVisibility(View.VISIBLE);
        setaAnd_1.setVisibility(View.VISIBLE);

        xor_1Output.setTextColor(Color.WHITE);
        and_1Output.setTextColor(Color.WHITE);

    }

    private void resetStageTwo() {
        stageOne();
        xor_1Output.setBackgroundColor(Color.BLACK);
        and_1Output.setBackgroundColor(Color.BLACK);
        xor_1Output.setTextColor(Color.WHITE);
        and_1Output.setTextColor(Color.WHITE);
        xor_1Output.setText("0");
        and_1Output.setText("0");

    }

    private void stageTwo() {
        boolean finalBitOne = bit_1.equals(bit_2);

        setaXor_1.setVisibility(View.VISIBLE);
        setaAnd_1.setVisibility(View.VISIBLE);

        xor_1Comparion.setTextColor(!finalBitOne ?
                Color.GREEN : Color.RED);
        and_1Comparion.setTextColor(finalBitOne && bit_2.equals("1") ?
                Color.GREEN : Color.RED);

        xor_1Output.setText((!finalBitOne) ? "1" : "0");
        and_1Output.setText((finalBitOne && bit_2.equals("1")) ? "1" : "0");

        xor_1Output.setTextColor((!finalBitOne) ? Color.BLACK : Color.WHITE);
        and_1Output.setTextColor((finalBitOne && bit_2.equals("1")) ? Color.BLACK : Color.WHITE);

        xor_1Output.setBackgroundColor(!finalBitOne ?
                Color.CYAN :
                Color.BLACK);
        and_1Output.setBackgroundColor(finalBitOne && bit_2.equals("1") ?
                Color.CYAN :
                Color.BLACK);
    }

    private void resetStageThree() {
        stageTwo();
        setaXor_2.setVisibility(View.INVISIBLE);
        setaAnd_2.setVisibility(View.INVISIBLE);

        xor_2Comparion.setTextColor(Color.WHITE);
        and_2Comparion.setTextColor(Color.WHITE);

        xor_2Comparion.setText("0 xor 0");
        and_2Comparion.setText("0 and 0");

    }

    private void stageThree() {

        setaXor_1.setVisibility(View.INVISIBLE);
        setaAnd_1.setVisibility(View.INVISIBLE);
        xor_2Comparion.setText(carry + " xor " + xor_1Output.getText());
        and_2Comparion.setText(carry + " and " + xor_1Output.getText());
        //
        xor_2Comparion.setTextColor(Color.YELLOW);
        and_2Comparion.setTextColor(Color.YELLOW);

        //
        setaXor_2.setVisibility(View.VISIBLE);
        setaAnd_2.setVisibility(View.VISIBLE);

    }

    private void resetStageFour() {
        stageThree();
        xor_2Output.setBackgroundColor(Color.BLACK);
        and_2Output.setBackgroundColor(Color.BLACK);

        xor_2Output.setTextColor(Color.WHITE);
        and_2Output.setTextColor(Color.WHITE);

        xor_2Output.setText("0");
        and_2Output.setText("0");

    }

    private void stageFour() {
        boolean finalBitOne = carry.equals(xor_1Output.getText());

        setaXor_2.setVisibility(View.VISIBLE);
        setaAnd_2.setVisibility(View.VISIBLE);

        xor_2Comparion.setTextColor(!finalBitOne ?
                Color.GREEN : Color.RED);
        and_2Comparion.setTextColor(finalBitOne && carry.equals("1") ?
                Color.GREEN : Color.RED);
        //
        xor_2Output.setText((!finalBitOne) ? "1" : "0");
        and_2Output.setText((finalBitOne && carry.equals("1")) ? "1" : "0");

        xor_2Output.setTextColor((!finalBitOne) ? Color.BLACK : Color.WHITE);
        and_2Output.setTextColor((finalBitOne && carry.equals("1")) ? Color.BLACK : Color.WHITE);

        //
        xor_2Output.setBackgroundColor(!finalBitOne ?
                Color.CYAN :
                Color.BLACK);
        and_2Output.setBackgroundColor(finalBitOne && carry.equals("1") ?
                Color.CYAN :
                Color.BLACK);
    }

    private void resetStageFive() {
        stageFour();
        carryOutPut.setText("0");
        carryOutPut.setBackgroundColor(Color.WHITE);
        orComparion.setText("0 or 0");

        setaOr.setVisibility(View.INVISIBLE);
        setaSoma.setVisibility(View.INVISIBLE);
        orComparion.setTextColor(Color.WHITE);
    }

    private void stageFive() {
        setaXor_2.setVisibility(View.INVISIBLE);
        setaAnd_2.setVisibility(View.INVISIBLE);

        carryOutPut.setText(xor_2Output.getText());
        orComparion.setText(and_2Output.getText() + " or " + and_1Output.getText());

        orComparion.setTextColor(Color.YELLOW);
        setaOr.setVisibility(View.VISIBLE);

        carryOutPut.setBackgroundColor(carryOutPut.getText().equals("1") ?
                Color.CYAN :
                Color.WHITE);
        setaSoma.setVisibility(
                View.VISIBLE);

    }

    private void resetStageSix() {
        stageFive();
        orOutput.setText("0");
        orOutput.setTextColor(Color.WHITE);
        orOutput.setBackgroundColor(Color.BLACK);
    }

    private void stageSix() {

        boolean finalBitOne = (and_2Output.getText().equals("1") || and_1Output.getText().equals("1"));
        setaSoma.setVisibility(View.INVISIBLE);
        orComparion.setTextColor(finalBitOne ?
                Color.GREEN : Color.RED);

        orOutput.setText(finalBitOne ? "1" : "0");
        orOutput.setTextColor(finalBitOne ? Color.BLACK : Color.WHITE);
        orOutput.setBackgroundColor(finalBitOne ?
                Color.CYAN :
                Color.BLACK);
    }

    private void resetStageSeven() {
        stageSix();
        setaCarry.setVisibility(
                View.INVISIBLE);
        setaOr.setVisibility(
                View.VISIBLE);

        bitOutPut.setBackgroundColor(
                Color.WHITE);
        bitOutPut.setText("0");
    }

    private void stageSeven() {

        bitOutPut.setText(orOutput.getText());
        bitOutPut.setBackgroundColor(bitOutPut.getText().equals("1") ?
                Color.CYAN :
                Color.WHITE);
        setaCarry.setVisibility(
                View.VISIBLE
                );
        setaOr.setVisibility(
                View.INVISIBLE
                );
    }

}