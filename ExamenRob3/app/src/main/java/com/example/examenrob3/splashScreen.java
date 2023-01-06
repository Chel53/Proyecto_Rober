package com.example.examenrob3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class splashScreen extends AppCompatActivity {

    static final long SPLASH_SCREEN_DELAY = 5000;

    TextView labelWelcomeMessage, labelSlogan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        labelWelcomeMessage = (TextView) findViewById( R.id.labelWelcomeMessage1 );
        labelSlogan = (TextView) findViewById( R.id.labelSlogan1);

        // -->> Abrir Pantalla de MainActivity [MainActivity] ::
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(splashScreen.this, MainActivity.class);
                startActivity( intent );
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule( task, SPLASH_SCREEN_DELAY );

        Animation animation1 = AnimationUtils.loadAnimation( splashScreen.this, R.anim.animation2 );
        labelWelcomeMessage.startAnimation( animation1 );

        Animation animation2 = AnimationUtils.loadAnimation( splashScreen.this, R.anim.animation1 );
        labelSlogan.startAnimation( animation2 );
    }
}