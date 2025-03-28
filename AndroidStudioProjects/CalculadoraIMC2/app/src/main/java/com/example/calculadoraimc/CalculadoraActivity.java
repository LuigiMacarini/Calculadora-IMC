package com.example.calculadoraimc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CalculadoraActivity extends AppCompatActivity {
    private EditText pesoInput, alturaInput;
    private Button btnLimpar, btnCalcular;
    private TextView resultadoIMC;
    private TextView interpretacao, pesoIdeal;
    // private TextView teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora_activity);
        pesoInput = findViewById(R.id.peso);
        alturaInput = findViewById(R.id.altura);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnCalcular = findViewById(R.id.calculator);
        resultadoIMC = findViewById(R.id.IMC);
        interpretacao = findViewById(R.id.interpretacao);
        pesoIdeal = findViewById(R.id.pesoIdeal);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CalculadoraActivity.this);
        String imcSalvo = preferences.getString("SAVE_IMC", "IMC");
        String pesoIdealSalvo = preferences.getString("SAVE_PESO_IDEAL", "Peso Ideal");
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(CalculadoraActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("Nome","Luigi");
        //editor.apply();
        //String res = preferences.getString("Nome",""); //aqui pega a var - só precisa passar a key
        //teste = findViewById(R.id.res); //habilita teste para o textView no layout
        //teste.setText(res);//seta teste com a String res

        resultadoIMC.setText(imcSalvo);
        pesoIdeal.setText(pesoIdealSalvo);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesoInput.setText("");
                alturaInput.setText("");
                resultadoIMC.setText("IMC");
                interpretacao.setText("Interpretação");
                pesoIdeal.setText("Peso Ideal");
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("SAVE_IMC", "IMC");
                edit.putString("SAVE_PESO_IDEAL", "Peso Ideal");
                edit.apply();
                interpretacao.setTextColor(getResources().getColor(R.color.white));
                resultadoIMC.setTextColor(getResources().getColor(R.color.white));
                pesoIdeal.setTextColor(getResources().getColor(R.color.white));
            }
        });
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIMC();
            }
        });
    }

    private void calcularIMC() {
        String pesoStr = pesoInput.getText().toString();
        String alturaStr = alturaInput.getText().toString();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);
            double alturaCm = altura * 100;
            double pesoIdealCalculado = (alturaCm - 100) - ((alturaCm - peso) / 4) * (5 / 100);

            if (altura > 0) {
                double imc = peso / (altura * altura);
                resultadoIMC.setText(String.format("IMC: %.2f", imc));
                pesoIdeal.setText(String.format("Peso Ideal: %.2f", pesoIdealCalculado));
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CalculadoraActivity.this);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("SAVE_IMC", String.format("IMC: %.1f", imc));
                edit.putString("SAVE_PESO_IDEAL", String.format("Peso Ideal: %.1f", pesoIdealCalculado));
                edit.apply();

                String classificacao;
                if (imc < 18.5) {
                    classificacao = "Abaixo do peso";
                    interpretacao.setTextColor(getResources().getColor(R.color.yellow));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.yellow));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.yellow));
                } else if (imc < 24.9) {
                    classificacao = "Peso normal";
                    interpretacao.setTextColor(getResources().getColor(R.color.white));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.white));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.white));
                } else if (imc < 29.9) {
                    classificacao = "Sobrepeso";
                    interpretacao.setTextColor(getResources().getColor(R.color.yellow));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.yellow));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.yellow));
                } else if (imc < 34.9) {
                    classificacao = "Obesidade grau 1";
                    interpretacao.setTextColor(getResources().getColor(R.color.red));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.red));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.red));
                } else if (imc < 39.9) {
                    classificacao = "Obesidade grau 2";
                    interpretacao.setTextColor(getResources().getColor(R.color.red));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.red));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.red));
                } else {
                    classificacao = "Obesidade grau 3";
                    interpretacao.setTextColor(getResources().getColor(R.color.red));
                    resultadoIMC.setTextColor(getResources().getColor(R.color.red));
                    pesoIdeal.setTextColor(getResources().getColor(R.color.red));
                }
                interpretacao.setText(classificacao);
            } else {
                resultadoIMC.setText("Altura inválida");
            }
        } else {
            resultadoIMC.setText("Preencha todos os campos");
        }
    }
}
