package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ventilador extends AppCompatActivity {
    Button btnBackToHomeL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventilador);

        btnBackToHomeL = (Button) findViewById( R.id.btnBackToHomeL );
        btnBackToHomeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // --> enviar a la pantalla principal

                Intent intent = new Intent(Ventilador.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }); //-- fin de el boton de home
    }
}