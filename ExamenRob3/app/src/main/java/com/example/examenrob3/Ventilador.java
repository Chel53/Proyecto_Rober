package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Ventilador extends AppCompatActivity {
    Button btnBackToHomeL;
    Switch switchVentilador;
    Integer  statusControlVentilador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventilador);
        switchVentilador = (Switch)findViewById(R.id.switchVentilador);
        btnBackToHomeL = (Button) findViewById( R.id.btnBackToHomeL );
        statusControlVentilador= (0);
        switchVentilador.setChecked(false);
        switchVentilador.setText("Encender");

        btnBackToHomeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // --> enviar a la pantalla principal

                Intent intent = new Intent(Ventilador.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }); //-- fin de el boton de home
        switchVentilador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (statusControlVentilador == 1) {
                    // Luces ENCENDIDAS Actualmente
                    // se desea APAGAR las luces...
                    // INTENCIÓN de APAGAR las luces
                    Toast.makeText( getApplicationContext(), "Apagando el ventilador...", Toast.LENGTH_SHORT).show();
                    controlApagarVentilador(4);

                    // DESPUÉS de haber APAGADO las LUCES... [Toogle :: ${statusControlLuz = 0} :: 0 :: Apagado]
                    statusControlVentilador = 0; // Apagadas



                    switchVentilador.setChecked( false ); // Apagadas
                    switchVentilador.setText( "ENCENDER" );

                } else {
                    // Luces APAGADAS Actualmente [statusControlLuz == 0]
                    // se desea ENCENDER las luces...
                    // INTENCIÓN de ENCENDER las luces
                    Toast.makeText( getApplicationContext(), "Encendiendo Ventilador...", Toast.LENGTH_SHORT).show();
                    controlEncenderVentilador(4);

                    // DESPUÉS de haber ENCENDIDO las LUCES... [Toogle :: ${statusControlLuz = 1} :: 1 ::  ENCENDIDO]
                    statusControlVentilador = 1; // ENCENDIDO



                    switchVentilador.setText( "Apagar" );

                }
            }
        });

    }//--fin del oncreate
    private void actualizarLEDEnBD(String url){
        Toast.makeText( getApplicationContext(), url, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Toast.makeText( getApplicationContext(), "ACTUALIZACIÓN realizada.", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add( stringRequest );
    }
    private void controlApagarVentilador(int numfoco){
        String url = "https://pruebasoooo.000webhostapp.com/control_leds.php?num_led="+ numfoco+ "&valor=0";
        actualizarLEDEnBD( url );
    } //--fin: controlApagarLuces()

    private void controlEncenderVentilador(int numfoco){
        String url = "https://pruebasoooo.000webhostapp.com/control_leds.php?num_led="+ numfoco+ "&valor=1";
        actualizarLEDEnBD( url );
    } //--fin: controlEncenderLuces()
}