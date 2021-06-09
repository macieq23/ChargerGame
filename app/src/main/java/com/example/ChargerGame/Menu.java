package com.example.ChargerGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.example.ChargerGame.MainActivity.SHARED;
import static com.example.ChargerGame.MainActivity.TEXT;
import static com.example.ChargerGame.MainActivity.SPEED;


public class Menu extends Activity{
public static TextView bestLevel;
public static TextView bestSpeed;
public static String saveEXTRALEVEL;
public static String saveEXTRASPEED;

public static final String EXTRA_TEXT = "com.example.application.rotatetheimage.EXTRA_TEXT";
public static final String EXTRA_SPEED = "com.example.application.rotatetheimage.EXTRA_SPEED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);

        Button buttonPlay = (Button) findViewById(R.id.btnPlay);
        bestLevel = (TextView) findViewById(R.id.tvRecord2);
        bestSpeed = (TextView) findViewById(R.id.tvSpeed2);

        //wczytanie rekordu levelu
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        String saveLevel = sharedPreferences.getString(TEXT, "");
        String saveSpeed = sharedPreferences.getString(SPEED, "");

        if (saveLevel == ("")){
            saveLevel = ("0");
        }
        if (saveSpeed == ("")){
            saveSpeed = ("2000");
        }

        saveEXTRALEVEL = saveLevel;
        saveEXTRASPEED = saveSpeed;

        bestLevel.setText(saveLevel);
        bestSpeed.setText(saveSpeed);

        //bestSpeed.setText(saveSpeed);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });

       /* buttonLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scrollView.getVisibility() == View.VISIBLE) {
                    scrollView.setVisibility(View.INVISIBLE);
                } else {
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }
    public void openMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT, saveEXTRALEVEL);
        intent.putExtra(EXTRA_SPEED, saveEXTRASPEED);
        startActivity(intent);
    }


}
