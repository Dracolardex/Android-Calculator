package com.example.ejercicio3_pmdm_t2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String texto="";

    private TextView resultados;

    public void botonPulsado(View view) {
        texto += ((Button) view).getText().toString();
        resultados.setText(texto);
    }

    public static double calcular(String texto) {

        List<Double> numeros = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        String actual = "";

        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) {
                actual += c;
            } else {
                numeros.add(Double.parseDouble(actual));
                ops.add(c);
                actual = "";
            }
        }
        numeros.add(Double.parseDouble(actual));

        int i = 0;
        while (i < ops.size()) {
            char op = ops.get(i);
            if (op == '*' || op == '/') {
                double a = numeros.get(i);
                double b = numeros.get(i + 1);
                numeros.set(i, op == '*' ? a * b : a / b);
                numeros.remove(i + 1);
                ops.remove(i);
            } else {
                i++;
            }
        }

        double resultado = numeros.get(0);
        for (int j = 0; j < ops.size(); j++) {
            double b = numeros.get(j + 1);
            resultado = ops.get(j) == '+' ? resultado + b : resultado - b;
        }
        return resultado;
    }

    public void botonCalcular(View view){

        try {
            double r = calcular(texto);
            if (Double.isInfinite(r) || Double.isNaN(r)) {
                resultados.setText("Math Error");
            } else {
                resultados.setText(String.valueOf(r));
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            resultados.setText("Syntax Error");
        }

        texto="";

    }
    public void botonBorrar(View view) {
        texto = "";
        resultados.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        resultados= findViewById(R.id.txtResultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}