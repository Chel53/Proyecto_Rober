package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btnMenuOptC1Huella, btnMenuOptC2Temperatura, btnMenuOptC3LucesOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenuOptC1Huella = (ImageButton) findViewById( R.id.btnMenuOptC1Huella );
        btnMenuOptC2Temperatura = (ImageButton) findViewById( R.id.btnMenuOptC2Temperatura );
        btnMenuOptC3LucesOff = (ImageButton) findViewById( R.id.btnMenuOptC3LucesOff );

        btnMenuOptC1Huella.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO: Redirigir a la Pantalla (Acticity) :: Circuito 1: Huella
                Intent i = new Intent(MainActivity.this, Puerta.class);
                startActivity(i);
            }
        }); //--fin: btnMenuOptC1Huella.clickListener()

        btnMenuOptC2Temperatura.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO: Redirigir a la Pantalla (Acticity) :: Circuito 2: Temperatura Corporal
                // -->> Enviar a pantalla de C2TemperaturaActivity
                Intent intent = new Intent(MainActivity.this, Ventilador.class);
                startActivity( intent );
               finish();
            }
        }); //--fin: btnMenuOptC2Temperatura.clickListener()

        btnMenuOptC3LucesOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //TODO: Redirigir a la Pantalla (Acticity) :: Circuito 3: Luces de Oficina
                Intent i = new Intent(MainActivity.this, Luz.class);
                startActivity(i);
            }
        }); //--fin: btnMenuOptC3LucesOff.clickListener()
    }
}