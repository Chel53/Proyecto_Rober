package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
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

public class Ventilador extends AppCompatActivity {
    private class ConsultarTemperaturaTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //TODO: Implementar llamada a WS consultarLumenes() y actualizar el valor de [LUMENES] en el
                    // textView [tvValorLumenes]
                    // Toast.makeText( getApplicationContext(), "Consultando LUMENES V2...", Toast.LENGTH_SHORT).show();
                    tvValorTemperatura = findViewById( R.id.tvValorTemperatura );

                    /*
                    String urlTmp = "http://" + WSConfig.serverIP + "/ws_punto_venta_android/validarLoginUsuario.php?usuario=" + cTUsuario.getText().toString() + "&password=" + cTContrasenia.getText().toString();

                    validarUsuario( urlTmp );
                    */

                    // tvValorTemperaturaCorp.setText( "66.6 °Cel" );
                    // String urlConsultaTempCorp = "http://localhost/ws_punto_venta_android/random_temperatures.php";
                    // http://192.168.0.108/ws_punto_venta_android/random_temperatures.php

                    consultartemperaturaBD(urlConsultartemperatura);;

                    // getSupportFragmentManager().findFragmentById()
                } //--fin: run()
            });
        } //--fin: run()

    } //--fin :: Private Class :: ConsultarTemperaturaTask
    Button btnBackToHomeL;
    Switch switchVentilador;
    Integer  statusControlVentilador;
    TextView tvValorTemperatura;
    String urlConsultartemperatura = "https://pruebasoooo.000webhostapp.com/consultar_temperatura_lm35.php";
    Timer timer;

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
        timer = new Timer();
        timer.scheduleAtFixedRate(new ConsultarTemperaturaTask(),0,350);

    }//--fin del oncreate
    private void consultartemperaturaBD(String url){

        // String nombreCompletoTmp;
        // Toast.makeText( getApplicationContext(), url, Toast.LENGTH_LONG ).show();

        // Para JsonArrayRequest :: default :: método GET de Http

        JsonArrayRequest stringArray = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                // Toast.makeText( getApplicationContext(), response.toString(), Toast.LENGTH_LONG ).show();
                // String nombreEmpleado = ""; lux
                String strtemperatura = "";

                int accessCode;

                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        // ctNombre.setText(jsonObject.getString("nombre"));
                        // ctEstado.setText( jsonObject.getString("estado") );
                        // Integer.toString(123)
                        // Toast.makeText(getApplicationContext(), Integer.toString( jsonObject.getInt("code") ) , Toast.LENGTH_LONG ).show();
                        accessCode = jsonObject.getInt( "code" );
                        /*
                        nombreEmpleado += jsonObject.getString( "nombre" );
                        nombreEmpleado += " ";
                        nombreEmpleado += jsonObject.getString( "ap_pat" );
                        nombreEmpleado += " ";
                        nombreEmpleado += jsonObject.getString( "ap_mat" );
                        labelMsgLogin.setText( Integer.toString(accessCode) );
                        */
                        strtemperatura = jsonObject.getString("temp" );
                        // strTimestamp = jsonObject.getString( "timestamp_temperatura" );

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
                            tvValorTemperatura.setText( strtemperatura + " °C " );
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
    //++FIN: consultarLumenesBD( url: String )
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