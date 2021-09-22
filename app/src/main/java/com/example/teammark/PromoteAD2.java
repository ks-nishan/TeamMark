package com.example.teammark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class PromoteAD2 extends AppCompatActivity {

    Button paymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promote_ad2);

        paymentBtn = (Button)findViewById(R.id.btnPayment);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromoteAD2.this,makePayment.class));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Button promoteBtn = (Button)findViewById(R.id.btnPromote);
        promoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichselect();
            }
        });
    }

    public void whichselect(){
        TextView displaySubTotal = (TextView)findViewById(R.id.txt5) ;
        TextView adAmount = (TextView)findViewById(R.id.txt2);
        EditText edtDays = (EditText)findViewById(R.id.editTxt1);
        String days = edtDays.getText().toString();
        int userDays = Integer.parseInt(days);

        EditText userCode = (EditText)findViewById(R.id.editTxt2);
        String ucode = userCode.getText().toString();
        int icode = Integer.parseInt(ucode);
        TextView codeAmount = (TextView)findViewById(R.id.txt8);

        TextView payAmount = (TextView)findViewById(R.id.txt10);


        RadioButton radioGold = (RadioButton) findViewById(R.id.radio_gold);
        RadioButton radioSilver = (RadioButton) findViewById(R.id.radio_silver);
        RadioButton radioBrown = (RadioButton)findViewById(R.id.radio_brownz);

        CalculationAD calculationAD = new CalculationAD();

        if(radioGold.isChecked()){
            adAmount.setText("LKR.1000/=");
            Integer ans = calculationAD.GoldSubTotal(userDays);
            String sub = Integer.toString(ans);
            displaySubTotal.setText("LKR."+sub+"/=");
            if (icode == 3333){
                codeAmount.setText("LKR.300/=");
                Integer fAns = ans - 300;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }

            if (icode == 4444){
                codeAmount.setText("LKR.400/=");
                Integer fAns = ans - 400;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }
        }

        if(radioSilver.isChecked()){
            adAmount.setText("LKR.750/=");
            Integer ans  = calculationAD.SilverSubTotal(userDays);
            String sub = Integer.toString(ans);
            displaySubTotal.setText("LKR."+sub+"/=");
            if (icode == 3333){
                codeAmount.setText("LKR.300/=");
                Integer fAns = ans - 300;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }

            if (icode == 4444){
                codeAmount.setText("LKR.400/=");
                Integer fAns = ans - 400;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }
        }

        if (radioBrown.isChecked()){
            adAmount.setText("LKR.500/=");
            Integer ans  = calculationAD.BrownSubTotal(userDays);
            String sub = Integer.toString(ans);
            displaySubTotal.setText("LKR."+sub+"/=");
            if (icode == 3333){
                codeAmount.setText("LKR.300/=");
                Integer fAns = ans - 300;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }

            if (icode == 4444){
                codeAmount.setText("LKR.400/=");
                Integer fAns = ans - 400;
                String lastAns = Integer.toString(fAns);
                payAmount.setText("LKR."+lastAns+"/=");
            }
        }
    }
}