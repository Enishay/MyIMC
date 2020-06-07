package com.project.myimc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate 2");
        setContentView(R.layout.activity_main);

        Button btnCalcul = findViewById(R.id.buttonCalcul);
        Button btnReset = findViewById(R.id.buttonReset);

        EditText edtPoids = findViewById(R.id.poids);
        EditText edtTaille = findViewById(R.id.taille);
System.out.println("onCreate 2");
        listenerEdtText(edtPoids);
        listenerEdtText(edtTaille);
        onClickBtnReset(btnReset);
        onClickBtnCalcul(btnCalcul);
        btnCalcul.setEnabled(false);
        btnReset.setEnabled(false);
    }

    @Override
    protected void onStart() {

        super.onStart();
        TextView txtResult = findViewById(R.id.txtResult);
        txtResult.setHeight(txtResult.getWidth());
    }

    protected void onClickBtnReset(Button btnReset) {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnCalcul = findViewById(R.id.buttonCalcul);
                Button btnReset = findViewById(R.id.buttonReset);
                btnCalcul.setEnabled(false);
                btnReset.setEnabled(false);
                EditText edtPoids = findViewById(R.id.poids);
                EditText edtTaille = findViewById(R.id.taille);
                TextView edtResultat = findViewById(R.id.txtResult);
                edtPoids.getText().clear();
                edtTaille.getText().clear();
                edtResultat.setText("");
            }
        });
    }

    protected void onClickBtnCalcul(Button btnCalcul) {
        btnCalcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculerIMC();
            }
        });
    }

    protected void calculerIMC() {
        EditText edtPoids = findViewById(R.id.poids);
        EditText edtTaille = findViewById(R.id.taille);
        TextView txtResult = findViewById(R.id.txtResult);
        Double conversion = 1.00;

        Double poids = Double.parseDouble(edtPoids.getText().toString());
        Double taille = Double.parseDouble(edtTaille.getText().toString());

        RadioButton rbCentimetre = findViewById(R.id.radioButtonCentimetre);
        if(rbCentimetre.isChecked()) {
            conversion = 10000.00;
        }

        BigDecimal imc =  new BigDecimal(poids / Math.pow(taille, 2) * conversion);
        System.out.println(imc);
        MathContext arrondi = new MathContext(4);
        txtResult.setText(imc.round(arrondi).toString());

    }

    protected void listenerEdtText(EditText edtTxt) {
        edtTxt.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        activerBtn();
                    }
                }
        );
    }

    private boolean estSaisie(EditText edtTxt) {
        boolean b = true;
        String edtString = edtTxt.getText().toString();
        String msgErreur = "";
        Double edt = 0.00;

        if(edtString.length() > 0 && edtString.compareTo(".") != 0) {
            edt = Double.parseDouble(edtString);
        } else {
            b = false;
            msgErreur += "- Champ obligatoire";
        }

        if(edtTxt.getId() == R.id.poids) {
            if(edt <= 0 && edtString.length() > 0) {
                b = false;
                msgErreur += "- Le poids doit être supérieur à 0 kg";
            }
            if(edt > 300 && edtString.length() > 0) {
                b = false;
                msgErreur += "- Le poids doit être inférieur à 300 kg";
            }
        }

        if(edtTxt.getId() == R.id.taille) {
            if(edt <= 0 && edtString.length() > 0) {
                b = false;
                msgErreur += "- La taille doit être supérieur à 0 cm/m";
            }
            if(edt > 300 && edtString.length() > 0) {
                b = false;
                msgErreur += "- La taille doit être inférieur à 300 cm/m";
            }
        }
        if(msgErreur.length() > 0)
            edtTxt.setError(msgErreur);
        return b;
    }

    private boolean verifierChampSaisi() {
        return (estSaisie((EditText) findViewById(R.id.poids))
                && estSaisie((EditText) findViewById(R.id.taille)));
    }

    private void activerBtn() {
        boolean activerBtn = verifierChampSaisi();
        Button btnCalcul = findViewById(R.id.buttonCalcul);
        Button btnReset = findViewById(R.id.buttonReset);

        btnCalcul.setEnabled(activerBtn);
        btnReset.setEnabled(activerBtn);
    }
}
