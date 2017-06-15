package com.mousebelly.app.deliveryapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Kamal Kant on 13-04-2017.
 */

public class CustomToast {


    public static void Toast(Context c, String msg) {


        LinearLayout layout = new LinearLayout(c);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimary));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.setClipToOutline(true);
        }
        layout.setBackgroundResource(R.drawable.customborder);
        layout.setPadding(20,20,20,20);

/*
        ImageView imgView = new ImageView(c);
        imgView.setImageResource(R.drawable.logo);
//                imgView.setPadding(20,20,20,10);
        imgView.setAdjustViewBounds(true);
        layout.addView(imgView);*/


    /*    LinearLayout layout2 = new LinearLayout(c);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setBackgroundColor(ContextCompat.getColor(c,R.color.colorPrimary));
        layout.addView(layout2);*/

        LinearLayout mb = new LinearLayout(c);
        mb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mb.setOrientation(LinearLayout.HORIZONTAL);
        mb.setBackgroundColor(Color.parseColor("#ffffff"));


        Typeface font = Typeface.createFromAsset(c.getAssets(), c.getResources().getString(R.string.font_face));
        TextView mouse = new TextView(c);
        mouse.setTextColor(ContextCompat.getColor(c,R.color.Tamarillo));
        mouse.setTextSize(21);
        mouse.setText("Mouse");
        mouse.setTypeface(font);
        mouse.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);

        TextView belly = new TextView(c);
        belly.setTextColor(ContextCompat.getColor(c,R.color.Yellow_Orange));
        belly.setTextSize(21);
        belly.setTypeface(font);
        belly.setText(" - Belly");
        belly.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);

        mb.addView(mouse);
        mb.addView(belly);
        mb.setPadding(20,5,20,5);

        mb.setBackgroundResource(R.drawable.customborder);
//            listText.setTextSize(18 * getResources().getDisplayMetrics().density);
        // final Typeface maze = Typeface.createFromAsset(getAssets(), "fonts/The Heart Maze Demo.ttf");
        //listText.setTypeface(maze, maze.BOLD);
        //layout.addView(mb);

        TextView listText2 = new TextView(c);
        listText2.setTextColor(ContextCompat.getColor(c,R.color.Amulet));
        listText2.setPadding(40, 30, 40, 30);
        listText2.setTextSize(18);
        listText2.setTypeface(null,Typeface.BOLD);
        listText2.setText(msg);
        //listText2.setTypeface(maze, maze.BOLD);
        layout.addView(listText2);

        final Toast toast = new Toast(c);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);


        int toastDurationInMilliSeconds = 3000;  // 3 sec
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        toastCountDown.start();

    }

}
