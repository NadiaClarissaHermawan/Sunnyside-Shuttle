package com.example.tubesp3b_2.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPref {
    protected SharedPreferences sp;
    protected final static String INDIKATOR_FIRST_TIME_LAUNCH = "indicator";

    public SharedPref (Context context){
        this.sp = context.getSharedPreferences(INDIKATOR_FIRST_TIME_LAUNCH, 0);
    }

    //set indikator launch ke brp-x
    public void saveIndicator(int indicator, String idIndicator){
        SharedPreferences.Editor editor = this.sp.edit();
        //simpan indicator baru -> ini di set == no need to
        editor.putInt(idIndicator, indicator);
        editor.commit();
    }
    //kembalikan launch ke brp-x
    public int getIndicator(String idIndicator){
        return this.sp.getInt(idIndicator, 0);
    }
}
