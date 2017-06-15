package com.mousebelly.app.deliveryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * Created by Kamal Kant on 24-05-2017.
 */

public class CustomProgressDialog {


    public static ProgressDialog getDialog(Context c, String msg){

        ProgressDialog pg = new ProgressDialog(c);

        SpannableString ss1=  new SpannableString(msg);
        ss1.setSpan(new RelativeSizeSpan(2f), 0, ss1.length(), 0);
        ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(c,R.color.Amulet)), 0, ss1.length(), 0);

        pg.getWindow().setBackgroundDrawableResource(R.drawable.customborder);

        pg.setMessage(ss1);

        return pg;
    }
}
