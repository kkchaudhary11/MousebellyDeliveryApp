package com.mousebelly.app.deliveryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mousebelly.app.deliveryapp.Login.LoginActivity;


public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);



        Typeface trumpitFace = Typeface.createFromAsset(getAssets(), this.getResources().getString(R.string.font_face));
        TextView mouse = (TextView) findViewById(R.id.mouse);
        mouse.setTypeface(trumpitFace);
        TextView belly = (TextView) findViewById(R.id.belly);
        belly.setTypeface(trumpitFace);

        TextView business = (TextView) findViewById(R.id.delivery);
        business.setTypeface(trumpitFace);

        new load().execute();
    }


    public class load extends AsyncTask<Void, Void, Void> {

        // ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            new SocketAccess() {
                @Override
                public void receive(Object o) {

                }
            };

            while (SocketAccess.connected == false) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            System.out.println("Inside post");

            Intent i = new Intent(Splash.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

    }

}

