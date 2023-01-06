package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Puerta extends AppCompatActivity {

    // Consumo CONTINUO de WS consultarEstatusPuerta [000webhost-eec]
    private class ConsultarEstatusPuertaTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //TODO: Implementar llamada a WS consultarTempCorp() y actualizar la Temperatura en el
                    // textView [tvValorTemperaturaCorp]
                    // Toast.makeText( getApplicationContext(), "Consultando temperatura V2...", Toast.LENGTH_SHORT).show();
                    imageBtnEstatusCerradura = (ImageButton) findViewById( R.id.imageBtnEstatusCerradura );

                    /*
                    String urlTmp = "http://" + WSConfig.serverIP + "/ws_punto_venta_android/validarLoginUsuario.php?usuario=" + cTUsuario.getText().toString() + "&password=" + cTContrasenia.getText().toString();

                    validarUsuario( urlTmp );
                    */

                    // tvValorTemperaturaCorp.setText( "66.6 °Cel" );
                    // String urlConsultaTempCorp = "http://localhost/ws_punto_venta_android/random_temperatures.php";
                    // http://192.168.0.108/ws_punto_venta_android/random_temperatures.php

                    consultarEstatusCerradura( urlConsultarEstatusCerradura );

                    // getSupportFragmentManager().findFragmentById()
                } //--fin: run()
            });
        } //--fin: run()

    } //--fin :: Private Class :: ConsultarTemperaturaTask

    Switch switchCerradura;
    Integer statusControlPuerta;
    ImageButton imageBtnEstatusCerradura;
    Button btnBackToHomeP;
    // ImageButton btnReloadPuerta;
    String urlConsultarEstatusCerradura = "https://conceptos-web-2010067-eec.000webhostapp.com/consultar_estatus_cerradura.php";

    // Button [agregarHuella]... --control de Arduino
    Button btnAgregarHuella;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puerta);
        switchCerradura = (Switch) findViewById(R.id.switchCerradura);
        imageBtnEstatusCerradura = (ImageButton) findViewById(R.id.imageBtnEstatusCerradura);
        btnAgregarHuella = (Button) findViewById( R.id.btnAgregarHuella );

        statusControlPuerta = 0; // CERRADA
        switchCerradura.setChecked( false ); // CERRADA
        switchCerradura.setText( "Abrir la puerta" ); // CERRADA

        /*
        statusControlPuerta = 0;
        1 :: 'Abierta'
        0 :: 'Cerrada'
        */
        // TODO: inicializar [statusPuer] de ACUERDO al valor recuperado del WS
        // consumo constante cada 350 ms

        // imageBtnEstatusCerradura.setBackgroundDrawable(null);

        btnBackToHomeP = (Button) findViewById( R.id.btnBackToHomeP );
        //btnReloadTPuerta = (ImageButton) findViewById( R.id.btnReloadPuerta);

        // validarstatusPuer();

         /*
        statusControlPuerta = 0;
        1 :: 'Abierta'
        0 :: 'Cerrada'
        */

        switchCerradura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (statusControlPuerta == 1) {
                    // Puerta ABIERTA Actualmente,
                    // se desea CERRAR la Puerta...

                    // switchCerradura.setChecked( true ); // Indica : Puerta ABIERTA (Actualmente)
                    // INTENCIÓN de CERRAR la Puerta...
                    // switchCerradura.setText( "Cerrar la puerta" );
                    Toast.makeText(getApplicationContext(), "Cerrando la puerta...", Toast.LENGTH_SHORT).show();
                    controlCerrarPuerta();
                    // DESPUÉS DE haber Cerrado la Puerta... [Toogle :: ${statusControlPuerta = 0} :: 0 :: 'Cerrada']

                    statusControlPuerta = 0;
                    switchCerradura.setChecked( false );
                    switchCerradura.setText( "ABRIR la puerta" );
                    // validarstatusPuer();
                } else {
                    // Puerta CERRADA Actualmente,
                    // se desea ABRIR la Puerta...
                    // switchCerradura.setChecked(false);
                    // INTENCIÓN de ABRIR la Puerta...
                    // switchCerradura.setText( "ABRIR la puerta" );
                    Toast.makeText( getApplicationContext(), "Abriendo la puerta...", Toast.LENGTH_SHORT).show();
                    controlAbrirPuerta();
                    // DESPUÉS de haber Abierto la Puerta... [Toogle :: ${statusControlPuerta = 1} ::  1 :: 'Abierta']

                    statusControlPuerta = 1;
                    switchCerradura.setChecked( true );
                    switchCerradura.setText( "Cerrar la puerta" );
                    // validarstatusPuer();
                }
            }
        });

        btnBackToHomeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // -->> Enviar a pantalla de MENU Principal
                Intent intent = new Intent(Puerta.this, MainActivity.class);
                startActivity( intent );
                finish();
            }
        }); //--fin: btnBackToHome.clickListener()

        btnAgregarHuella.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO: Implementar consumo de WS para setear campo [RECEIVED_BOOL5] = 1
                String urlAgregarHuellaEnable = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led=5" + "&valor=1";
                actualizarLEDEnBD( urlAgregarHuellaEnable );
                // Luego DELAY de 600 ms para setear de nueva cuenta [RECEIVED_BOOL5] = 0
                String urlAgregarHuellaDisable = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led=5" + "&valor=0";

                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something AFTER 1200 ms == 1.2 s
                        actualizarLEDEnBD( urlAgregarHuellaDisable );
                    }
                }, 700); //--fin: handler.postDelayed()

            } //--fin :onClick()

        }); //--fin: btnAgregarHuella.clickListener()

        /*
        btnReloadPuerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : Actualizar temperatura [Consultar Temperatura en BD]
                //consultarTemperaturaBD( urlConsultaTempCorp );
            }
        }); //--fin: btnReloadPuerta.clickListener()
        */

        // TODO: inicializar [statusPuer] de ACUERDO al valor recuperado del WS
        // consumo constante cada 350 ms
        timer = new Timer();
        timer.scheduleAtFixedRate( new ConsultarEstatusPuertaTask(), 0, 350 );

    } //--fin: onCreate()


    /*
      statusPuer = 1;
      1 :: 'Abierta'
      0 :: 'Cerrada'
      */

    /*
    public void validarstatusPuer(){
        if (statusControlPuerta==1){//Cuando el switch es checado
            switchCerradura.setChecked(true);
            switchCerradura.setText("Cerrar la puerta");
            // imageBtnEstatusCerradura.setImageResource(R.drawable.abierta);
            Toast.makeText(this, "Cerrando la puerta...", Toast.LENGTH_SHORT).show();
            controlCerrarPuerta();
        }else{
            switchCerradura.setChecked(false);
            switchCerradura.setText("Abrir la puerta");
            // imageBtnEstatusCerradura.setImageResource(R.drawable.cerrada);
            Toast.makeText(this, "Abriendo la puerta...", Toast.LENGTH_SHORT).show();
            controlAbrirPuerta();
        }

    } //--fin: validarstatusPuer()
    */


    private void controlAbrirPuerta(){
        //--INI: abrirPuerta
        String url = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led=1" + "&valor=0";
        actualizarLEDEnBD( url );
        //++FIN: abrirPuerta
    } //--fin: controlAbrirPuerta()

    private void controlCerrarPuerta(){
        //--INI: cerrarPuerta
        String url = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led=1" + "&valor=1";
        actualizarLEDEnBD( url );
        //++FIN: cerrarPuerta
    } //--fin: controlCerrarPuerta()

    //--INI: actualizarLEDEnBD(url : String)
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
    //++FIN: actualizarLEDEnBD(url : String)

    //--INI: consultarEstatusCerradura( url: String )
    private void consultarEstatusCerradura(String url){

        // String nombreCompletoTmp;
        // Toast.makeText( getApplicationContext(), url, Toast.LENGTH_LONG ).show();

        // Para JsonArrayRequest :: default :: método GET de Http

        JsonArrayRequest stringArray = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                // Toast.makeText( getApplicationContext(), response.toString(), Toast.LENGTH_LONG ).show();
                // String nombreEmpleado = "";
                // String strTemperatura = "", strTimestamp = "";
                int accessCode, estatusCerradura;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        // ctNombre.setText(jsonObject.getString("nombre"));
                        // ctEstado.setText( jsonObject.getString("estado") );
                        // Integer.toString(123)
                        // Toast.makeText(getApplicationContext(), Integer.toString( jsonObject.getInt("code") ) , Toast.LENGTH_LONG ).show();
                        accessCode = jsonObject.getInt( "code" );
                        /*
                         */
                        estatusCerradura = jsonObject.getInt("estatus_cerradura");

                        // Toast.makeText( getApplicationContext(), nombreEmpleado, Toast.LENGTH_LONG ).show();

                        if( accessCode == 100 ){
                            // OK : Dejar pasar al Usuario...
                            // Registrar nombreCompleto en archivo Preferences..,
                            // registrarNombreEmpleado( nombreEmpleado );
                            // registrarIDEmpleado( jsonObject.getInt( "id_empleado" ) );
                            // -->> Enviar a pantalla de MENU Principal...
                            /*
                            Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
                            startActivity( intent );
                            finish();
                            */
                              /*
                                statusPuer = 1;
                                1 :: 'Abierta'
                                0 :: 'Cerrada'
                            */
                            // tvValorTemperaturaCorp.setText( strTemperatura + " °C" );

                            // MOD. dinámica de IMG...
                            if( estatusCerradura == 1 ){
                                imageBtnEstatusCerradura.setImageResource(R.drawable.abierta);
                            }else{
                                // Cerrada ::
                                imageBtnEstatusCerradura.setImageResource(R.drawable.cerrada);
                            }

                            // tVValorTimestamp.setText( strTimestamp );
                        }else{
                            Toast.makeText( getApplicationContext(), "ERROR al recurepar Temp. Corp. del WS", Toast.LENGTH_LONG).show();
                            // labelMsgLogin.setText( "Usuario y contraseña NO coinciden." );
                        } //-- fin : IF accessCode == 100 (OK - Dejar pasar)

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add( stringArray );

        // return accessCode;
    }
    //++FIN: consultarEstatusCerradura( url: String )



}