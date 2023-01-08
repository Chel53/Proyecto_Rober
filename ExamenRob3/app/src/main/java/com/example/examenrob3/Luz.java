package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class Luz extends AppCompatActivity {

    Switch switchLucesOficina;
    Integer  statusControlLuz;
    ImageView imgBtnEstatusLuz,imgBtnEstatusLuz1,imgBtnEstatusLuz2;
    Button btnBackToHomeL;
    ImageButton btnReloadLuz;

    String urlConsultarEstatusLucesOficina = "https://conceptos-web-2010067-eec.000webhostapp.com/consultar_estatus_luces.php";

    /*
    statusControlLuz == 1 ::  ENCENDIDO
    statusControlLuz == 0 :: Apagado
    -- INIT :: Apagado
    */

    Timer timer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);
        switchLucesOficina = (Switch) findViewById(R.id.switchLucesOficina);
        imgBtnEstatusLuz = (ImageView) findViewById(R.id.imgBtnEstatusLuz);
        imgBtnEstatusLuz1 = (ImageView) findViewById(R.id.imgBtnEstatusLuz1);
        imgBtnEstatusLuz2 = (ImageView) findViewById(R.id.imgBtnEstatusLuz2);

        imgBtnEstatusLuz.setBackgroundDrawable(null);

        // INIT :: Considerar LUCES Apagadas
        /*
            statusControlLuz == 1 ::  ENCENDIDO
            statusControlLuz == 0 :: Apagado
            -- INIT :: Apagado
        */
        statusControlLuz = 0; // Apagadas
        //switchLucesOficina.setChecked( false ); // Apagadas
       //switchLucesOficina.setText( "Encender" ); // Apagadas
        //imgBtnEstatusLuz.setImageResource(R.drawable.lamparapag); // Apagadas



        btnBackToHomeL = (Button) findViewById( R.id.btnBackToHomeL );

        validarstatusLuz();


        switchLucesOficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // actualizar("http://192.168.114.225/serviceexamen/regprodfact.php");

                if (statusControlLuz == 1) {
                    // Luces ENCENDIDAS Actualmente
                    // se desea APAGAR las luces...
                    // INTENCIÓN de APAGAR las luces
                    Toast.makeText( getApplicationContext(), "Apagando las luces...", Toast.LENGTH_SHORT).show();
                    //controlApagarLuces();
                    // DESPUÉS de haber APAGADO las LUCES... [Toogle :: ${statusControlLuz = 0} :: 0 :: Apagado]
                   //statusControlLuz = 0; // Apagadas
                    //switchLucesOficina.setChecked( false ); // Apagadas
                   // switchLucesOficina.setText( "ENCENDER" );
                    validarstatusLuz();
                } else {
                    // Luces APAGADAS Actualmente [statusControlLuz == 0]
                    // se desea ENCENDER las luces...
                    // INTENCIÓN de ENCENDER las luces
                    Toast.makeText( getApplicationContext(), "Encendiendo las luces...", Toast.LENGTH_SHORT).show();
                    //controlEncenderLuces();
                    // DESPUÉS de haber ENCENDIDO las LUCES... [Toogle :: ${statusControlLuz = 1} :: 1 ::  ENCENDIDO]
                    //statusControlLuz = 1; // ENCENDIDO
                    ////switchLucesOficina.setText( "Apagar" );
                    validarstatusLuz();
                }

            }
        });

        btnBackToHomeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // -->> Enviar a pantalla de MENU Principal
                Intent intent = new Intent(Luz.this, MainActivity.class);
                startActivity( intent );
                finish();
            }
        }); //--fin: btnBackToHome.clickListener()

        /*
        btnReloadLuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : Actualizar temperatura [Consultar Temperatura en BD]
                //consultarTemperaturaBD( urlConsultaTempCorp );
            }
        }); //--fin: btnReloadLuz.clickListener()
        */

        // TODO: inicializar [statusLucesOff] de ACUERDO al valor recuperado del WS
        // consumo constante cada 350 ms
        timer = new Timer();
        //timer.scheduleAtFixedRate( new ConsultarEstatusLucesOficinaTask(), 0, 350 );

    } //--fin: onCreate()

    private void controlApagarLuces(int numfoco){
        String url = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led="+ numfoco+ "&valor=0";
        actualizarLEDEnBD( url );
    } //--fin: controlApagarLuces()

    private void controlEncenderLuces(int numfoco){
        String url = "https://conceptos-web-2010067-eec.000webhostapp.com/control_leds.php?num_led="+ numfoco+ "&valor=1";
        actualizarLEDEnBD( url );
    } //--fin: controlEncenderLuces()

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



    public void validarstatusLuz(){
        if (statusControlLuz==1){
            statusControlLuz = 0;
            switchLucesOficina.setChecked(true);
            switchLucesOficina.setText("Apagar la luz");
            imgBtnEstatusLuz.setImageResource(R.drawable.lamparapren);
            imgBtnEstatusLuz1.setImageResource(R.drawable.lamparapren);
            imgBtnEstatusLuz2.setImageResource(R.drawable.lamparapren);
            Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();

        }else{
            statusControlLuz = 1;
            switchLucesOficina.setChecked(false);
            switchLucesOficina.setText("Encender la luz");
            imgBtnEstatusLuz.setImageResource(R.drawable.lamparapag);
            imgBtnEstatusLuz1.setImageResource(R.drawable.lamparapag);
            imgBtnEstatusLuz2.setImageResource(R.drawable.lamparapag);
            Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();


        }
    } //-- fin: validarstatusLuz()



}