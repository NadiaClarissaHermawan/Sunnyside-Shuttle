package com.example.tubesp3b_2.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    protected SharedPreferences sp;
    protected final static String INDIKATOR_FIRST_TIME_LAUNCH = "indicator";

    public SharedPref (Context context){
        //pertama x download
        this.sp = context.getSharedPreferences(INDIKATOR_FIRST_TIME_LAUNCH, 0);
    }


    //set indikator launch ke brp-x
    public void saveIndicator(int indicator){
        SharedPreferences.Editor editor = this.sp.edit();
        //simpan indicator baru -> ini di set == no need to
        editor.putInt(INDIKATOR_FIRST_TIME_LAUNCH, indicator);
        editor.commit();
    }
    //kembalikan launch ke brp-x
    public int getIndicator(){
        return this.sp.getInt(INDIKATOR_FIRST_TIME_LAUNCH, 0);
    }
}
