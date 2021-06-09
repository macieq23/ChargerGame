package com.example.ChargerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static java.lang.StrictMath.abs;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 3200;
    float prawo, lewo, kierunek, rotacjaArrow, durationEfekt;
    int animacjaRuchLosowy = 360, score = 0;
    int losowanyint, tempInt, losowanyint2, tempInt2, losujEfectFlash, gamelevel = 1, goal = 2;
    int count = 0, counter = 0, chances = 3, changingScore = 0;
    int losowa, losowa2, intKierunek, kierunekAngle, intSpeed;
    String strLosowa, strSpeed, strChances;
    //Random random;
    int absKierunek, rotKierunek;
    boolean start, stopApp = false, bObnizycSpeed = true;
    int speed = 2000;
    private View tlo;
    TextView scoreCounter;
    private Handler mHandler = new Handler();

    public int iCountdown = 300;
    public int iCountdownStability = 0;
    public int iMills = 3200;
    public boolean bTimerRunning;

    public static final String SHARED = "shared";
    public static final String TEXT = "text";
    public static final String SPEED = "speed";

    public static CountDownTimer myCountDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView setLevel = (TextView) findViewById(R.id.setLevel);
        final TextView bestLevel = (TextView) findViewById(R.id.tvRecord2);
        final TextView setGoal = (TextView) findViewById(R.id.setGoal);

        //setLevel.setText(gamelevel);
        setGoal.setText(String.valueOf(goal));

        Button bRotLeft = (Button) findViewById(R.id.button2);
        Button bRotRight = (Button) findViewById(R.id.button);
        final Button start = (Button) findViewById(R.id.startgame);

        final ImageView imageArrow = (ImageView) findViewById(R.id.denis);
        final ImageView imageStar = (ImageView) findViewById(R.id.star);

        final TextView wynik = (TextView) findViewById(R.id.score);
        final Random random = new Random();
        final Random randEfectFlash = new Random();
        final Button btnMenu = (Button) findViewById(R.id.gomenu);
        
        final long mTimeLeftInMillis = START_TIME_IN_MILLIS;

        final ImageView efectFlash1 = (ImageView) findViewById(R.id.efekt_power_1);
        final ImageView efectFlash2 = (ImageView) findViewById(R.id.efekt_power_2);
        final ImageView efectFlash3 = (ImageView) findViewById(R.id.efekt_power_3);
        final ImageView efectFlash4 = (ImageView) findViewById(R.id.efekt_power_4);

        Thread tStart = new Thread(){
            @Override
            public void run() {
                while(stopApp == false){
                    try {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                losujEfectFlash = randEfectFlash.nextInt(4);
                                if (losujEfectFlash == 1){
                                    efectFlash1.setVisibility(View.VISIBLE); efectFlash2.setVisibility(View.INVISIBLE); efectFlash3.setVisibility(View.INVISIBLE); efectFlash4.setVisibility(View.INVISIBLE);
                                }
                                if (losujEfectFlash == 2){
                                    efectFlash2.setVisibility(View.VISIBLE); efectFlash1.setVisibility(View.INVISIBLE); efectFlash3.setVisibility(View.INVISIBLE); efectFlash4.setVisibility(View.INVISIBLE);
                                }
                                if (losujEfectFlash == 3){
                                    efectFlash3.setVisibility(View.VISIBLE); efectFlash2.setVisibility(View.INVISIBLE); efectFlash1.setVisibility(View.INVISIBLE); efectFlash4.setVisibility(View.INVISIBLE);
                                }
                                if (losujEfectFlash == 4){
                                    efectFlash4.setVisibility(View.VISIBLE); efectFlash2.setVisibility(View.INVISIBLE); efectFlash3.setVisibility(View.INVISIBLE); efectFlash1.setVisibility(View.INVISIBLE);
                                }
                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tStart.start();

        efectFlash1.setVisibility(View.VISIBLE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                efectFlash1.animate().rotationBy(360).withEndAction(this).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
                efectFlash2.animate().rotationBy(360).withEndAction(this).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
                efectFlash3.animate().rotationBy(360).withEndAction(this).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
                efectFlash4.animate().rotationBy(360).withEndAction(this).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        efectFlash1.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
        efectFlash2.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
        efectFlash3.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();
        efectFlash4.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();


        
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StopLastRotation();
                gwiazdka();
                timer();
                btnMenu.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);

            }
        });

        bRotRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prawo = imageArrow.getRotation();
                prawo = prawo + 30;
                if (prawo == 360) prawo = 0;
                imageArrow.setRotation((float) prawo);
                kierunek = prawo;
                //intKierunek = abs((int) kierunek);
                if (kierunek > 360 || kierunek < -360){
                    kierunekAngle = (int)prawo / 360;
                    prawo = (int)prawo - kierunekAngle * 360;
                    if (prawo == 360) prawo = 0;
                    imageArrow.setRotation(prawo);
                }
                countdown();

            }
        });

        bRotLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prawo = imageArrow.getRotation();
                prawo = prawo - 30;
                if (prawo == -30) prawo = 330;if (prawo == -60) prawo = 300;if (prawo == -90) prawo = 270;
                if (prawo == -120) prawo = 240;if (prawo == -150) prawo = 210;if (prawo == -180) prawo = 180;
                if (prawo == -210) prawo = 150;if (prawo == -240) prawo = 120;if (prawo == -270) prawo = 90;
                if (prawo == -330) prawo = 60;if (prawo == 360) prawo = 0;
                imageArrow.setRotation((float) prawo);
                kierunek = prawo;
                //intKierunek = abs((int) kierunek);
                if (kierunek > 360 || kierunek < -360){
                    kierunekAngle = (int)prawo / 360;
                    prawo = (int)prawo - kierunekAngle * 360;
                    if (prawo == 360) prawo = 0;
                    imageArrow.setRotation(prawo);
                }
                countdown();
            }
        });
        /*SharedPreferences myScore = this.getSharedPreferences("MyAwesomeScore", Context.MODE_PRIVATE);
        count = myScore.getInt("score", 0);
        strLosowa = String.valueOf(count);
        wynik.setText(strLosowa);*/
    }
    private Runnable iDelay = new Runnable() {
        @Override
        public void run() {
            final ImageView imageStar = (ImageView) findViewById(R.id.goodpoint);
            final ImageView imageStar2 = (ImageView) findViewById(R.id.starRed);
            imageStar.setVisibility(View.GONE);
            imageStar2.setVisibility(View.GONE);
        }
    };
    public void gwiazdka() {
        final Random random = new Random();
        final Random random2 = new Random();
        final TextView wynik = (TextView) findViewById(R.id.score);
        final TextView ms = (TextView) findViewById(R.id.speed);
        final ImageView imageStar = (ImageView) findViewById(R.id.goodpoint);
        final ImageView imageStar2 = (ImageView) findViewById(R.id.starRed);
        final ImageView imageArrow = (ImageView) findViewById(R.id.denis);
        //final ImageView imageGoodPoint = (ImageView) findViewById(R.id.goodpoint);
        Thread t = new Thread(){
            @Override
            public void run(){
                while(stopApp == false){
                    try {
                        Thread.sleep(speed);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                if (chances == 0){
                                    imageStar.setVisibility(View.INVISIBLE); imageStar2.setVisibility(View.INVISIBLE);
                                }else{
                                    imageStar.setVisibility(View.VISIBLE); imageStar2.setVisibility(View.VISIBLE);
                                }
                                rotacjaArrow = imageArrow.getRotation();
                                tempInt = losowanyint; tempInt2 = losowanyint2;
                                do{
                                    losowanyint = random.nextInt(12);
                                    losowanyint2 = random2.nextInt(12);
                                    losowa = losowanyint * 30;
                                    losowa2 = losowanyint2 * 30;
                                }while (losowa == (int)rotacjaArrow || losowa2 == (int)rotacjaArrow || tempInt == losowanyint || tempInt2 == losowanyint2 || losowanyint == losowanyint2);

                                float density = getApplicationContext().getResources().getDisplayMetrics().density;
                                float px150 = 150*density; float px129 = 129.9f*density; float px75 = 75*density; float px30 = 30*density;
                                switch (losowanyint){
                                    case 0:
                                        imageStar.setTranslationY(-px150); imageStar.setTranslationX(0);
                                        break;
                                    case 1:
                                        imageStar.setTranslationY(-px129); imageStar.setTranslationX(px75);
                                        break;
                                    case 2:
                                        imageStar.setTranslationY(-px75); imageStar.setTranslationX(px129);
                                        break;
                                    case 3:
                                        imageStar.setTranslationY(0); imageStar.setTranslationX(px150);
                                        break;
                                    case 4:
                                        imageStar.setTranslationY(px75); imageStar.setTranslationX(px129);
                                        break;
                                    case 5:
                                        imageStar.setTranslationY(px129); imageStar.setTranslationX(px75);
                                        break;
                                    case 6:
                                        imageStar.setTranslationY(px150); imageStar.setTranslationX(0);
                                        break;
                                    case 7:
                                        imageStar.setTranslationY(px129); imageStar.setTranslationX(-px75);
                                        break;
                                    case 8:
                                        imageStar.setTranslationY(px75); imageStar.setTranslationX(-px129);
                                        break;
                                    case 9:
                                        imageStar.setTranslationY(0); imageStar.setTranslationX(-px150);
                                        break;
                                    case 10:
                                        imageStar.setTranslationY(-px75); imageStar.setTranslationX(-px129);
                                        break;
                                    case 11:
                                        imageStar.setTranslationY(-px129); imageStar.setTranslationX(-px75);
                                        break;

                                }
                                imageStar.animate().rotation(360).setDuration(9000);
                                imageStar.setRotation((Integer) losowa);
                                imageStar2.setRotation((Integer) losowa2);
                                start = true;
                                counter = 0;
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        //tStart.start();
    }

    public void punkty(){
        final TextView wynik = (TextView) findViewById(R.id.score);
        TextView ms = (TextView) findViewById(R.id.speed);
        final ImageView imageStar = (ImageView) findViewById(R.id.goodpoint);
        final ImageView imageStar2 = (ImageView) findViewById(R.id.starRed);
        final ImageView imageArrow = (ImageView) findViewById(R.id.denis);
        int iAnimacja;
        final Random losujAnimacje = new Random();
        iAnimacja = losujAnimacje.nextInt(2);
        final TextView tvLevel = (TextView) findViewById(R.id.setLevel);
        final TextView tvGoal = (TextView) findViewById(R.id.setGoal);

        boolean turnLevel1 = false;

        counter = count;
            if (stopApp == false){
                /* //---------------------------ANIMACJA OBRACANIA GWIAZDKI
                if (imageStar.getRotation() == 0 || imageStar.getRotation() == 180){
                    imageStar.animate().rotationXBy(360f).setDuration(200);
                }else if (imageStar.getRotation() == 90 || imageStar.getRotation() == 270){
                    imageStar.animate().rotationYBy(360f).setDuration(200);
                }else if (iAnimacja == 1){
                    imageStar.animate().rotationYBy(360f).setDuration(200);
                }else{
                    imageStar.animate().rotationXBy(360f).setDuration(200);
                }

                mHandler.postDelayed(iDelay, 200);*/

                //countTimer.setText(String.valueOf(iCountdown));
                imageArrow.animate().scaleY(0.2f).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imageArrow.animate().scaleY(1f).setDuration(50).start();
                    }
                });

                imageStar.setVisibility(View.GONE);
                imageStar2.setVisibility(View.GONE);

                count++;
                changingScore++;

                if (bObnizycSpeed && speed != 1000 && speed != 2000){
                    if (speed > 2000) speed = speed - 50;
                    if (speed < 2000 && speed > 1000) speed = speed - 25;
                    if (speed < 1000) speed = speed - 10;
                    bObnizycSpeed = false;
                }
                if (bObnizycSpeed && speed == 2000){
                    speed = speed - 50;
                    bObnizycSpeed = false;
                }
                if (bObnizycSpeed && speed == 1000){
                    speed = speed - 10;
                    bObnizycSpeed = false;
                }
                intSpeed = speed;
                strSpeed = String.valueOf(intSpeed);
                strLosowa = String.valueOf(count);
                ms.setText(strSpeed);
                //wynik.setText(strLosowa);
                wynik.setText(String.valueOf(changingScore));


                // DODAWANIE LEVELI GDY PRZEKROCZYMY ILES PKT W DANYM LEVELU
                if (gamelevel == 1 && changingScore == 2) {gamelevel = 2; goal = 2; refreshLevel();}
                if (gamelevel == 2 && changingScore == 2) {gamelevel = 3; goal = 3; refreshLevel();}
                if (gamelevel == 3 && changingScore == 3) {gamelevel = 4; goal = 4; refreshLevel();}
                if (gamelevel == 4 && changingScore == 4) {gamelevel = 5; goal = 5; refreshLevel();}
                if (gamelevel == 5 && changingScore == 5) {gamelevel = 6; goal = 7; refreshLevel();}
                if (gamelevel == 6 && changingScore == 7) {gamelevel = 7; goal = 7; refreshLevel();}
                if (gamelevel == 7 && changingScore == 7) {gamelevel = 8; goal = 7; refreshLevel();}

            }
    }

    public void refreshLevel() {
        final TextView wynik = (TextView) findViewById(R.id.score);
        final TextView tvLevel = (TextView) findViewById(R.id.setLevel);
        final TextView tvGoal = (TextView) findViewById(R.id.setGoal);
        changingScore = 0;

        tvLevel.setText(String.valueOf(gamelevel));
        tvGoal.setText(String.valueOf(goal));
        wynik.setText(String.valueOf(changingScore));
    }

    public void punktyMinus(){
        final TextView wynik = (TextView) findViewById(R.id.score);
        final TextView chancesT = (TextView) findViewById(R.id.chances);
        final ImageView imageStar = (ImageView) findViewById(R.id.goodpoint);
        final ImageView imageStar2 = (ImageView) findViewById(R.id.starRed);
        final ImageView powerBar = (ImageView) findViewById(R.id.power);
        if (!stopApp){
            //imageStar.setRotation(losowa-0.01f);imageStar2.setRotation(losowa2-0.01f);
            imageStar.setVisibility(View.GONE);
            imageStar2.setVisibility(View.GONE);
            counter = count;
            //count = count;
            chances--;
            strChances = String.valueOf(chances);
            strLosowa = String.valueOf(count);
            //wynik.setText(strLosowa);
            chancesT.setText(strChances);
            if (chances == 0) {
                gameover();
            }
            countdown();
        }
    }

    @SuppressLint("SetTextI18n")
    public void gameover(){
        //final TextView bestLevel = (TextView) findViewById(R.id.tvRecord2);
        final Button btnMenu = (Button) findViewById(R.id.gomenu);
        final Button start = (Button) findViewById(R.id.startgame);
        final ImageView imageStar = (ImageView) findViewById(R.id.goodpoint);
        final ImageView imageStar2 = (ImageView) findViewById(R.id.starRed);
        final TextView tvLevel = (TextView) findViewById(R.id.setLevel);
        final TextView tvSpeed = (TextView) findViewById(R.id.speed);
        final ImageView powerBar = (ImageView) findViewById(R.id.power);

        String savedEXTRATEXT = getIntent().getStringExtra(Menu.EXTRA_TEXT);
        String savedEXTRASPEED = getIntent().getStringExtra(Menu.EXTRA_SPEED);

        stopApp = true;
        imageStar.setVisibility(View.INVISIBLE);
        imageStar2.setVisibility(View.INVISIBLE);
        powerBar.setVisibility(View.INVISIBLE);

        start.setText("Try again!");
        btnMenu.setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
        String strTextLevel = savedEXTRATEXT; //String.valueOf(0); //String.valueOf(savedEXTRATEXT);  //Menu.bestLevel.toString(); //String.valueOf(0);
        String strTextSpeed = savedEXTRASPEED;

        int iTEXT = Integer.parseInt(strTextLevel);
        int iSPEED = Integer.parseInt(strTextSpeed);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        if (gamelevel > iTEXT) {
            editor.putString(TEXT, tvLevel.getText().toString());
            editor.apply();
        }
        if (speed < iSPEED) {
            editor.putString(SPEED, tvSpeed.getText().toString());
            editor.apply();
        }

            //}
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentMenu = new Intent(view.getContext(), Menu.class);
                    startActivity(intentMenu);
                }
            });
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });

    }

    public void timer(){
        Thread tStart = new Thread(){
            @Override
            public void run() {
                while(stopApp == false){
                    try {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                absKierunek = abs((int) prawo);
                                if (start == true && absKierunek == losowa) {
                                    counter ++;
                                    if (counter == 1)
                                        bObnizycSpeed = true;
                                        punkty(); start = false;
                                }else if (start == true && absKierunek == losowa2){
                                    counter ++;
                                    if (counter == 1)
                                        punktyMinus(); start = false;
                                }
                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tStart.start();
    }

    public void countdown(){
        final ImageView powerBar = (ImageView) findViewById(R.id.power);
        final ImageView efektPowerBar = (ImageView) findViewById(R.id.efekt_power);
        final TextView countTimer = (TextView) findViewById(R.id.countdown);
        if (myCountDownTimer != null){
            //powerBar.setVisibility(View.VISIBLE);
            myCountDownTimer.cancel();
        }
        final int iCountdownConst;
        iCountdown = 300;
        iCountdownConst = iCountdown;
        countTimer.setText(String.valueOf(iCountdown));


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                efektPowerBar.animate().rotationBy(360).withEndAction(this).setDuration(1000).setInterpolator(new LinearInterpolator()).start();
            }
        };
        efektPowerBar.animate().rotationBy(360).withEndAction(runnable).setDuration(2000).setInterpolator(new LinearInterpolator()).start();

        // ZMIANA KOLORU ZIOMEK powerBar.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY );


        myCountDownTimer = new CountDownTimer(iMills, 100) {
            public void onTick(long millisUntilFinished) {
                countTimer.setText(String.valueOf(iCountdown));
                iCountdown = iCountdown - 10;

                powerBar.animate().scaleY((float)iCountdown/iCountdownConst).setDuration(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        powerBar.animate().scaleY((float)iCountdown/iCountdownConst).setDuration(10).start();
                    }
                });

                powerBar.animate().scaleX((float)iCountdown/iCountdownConst).setDuration(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        powerBar.animate().scaleX((float)iCountdown/iCountdownConst).setDuration(10).start();
                    }
                });

            }

            @Override
            public void onFinish() {
                punktyMinus();
            }
        }.start();
    }

}
