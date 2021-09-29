package com.efortshub.holyquran.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.efortshub.holyquran.R;
import com.efortshub.holyquran.utils.HbConst;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(MainActivity.this, HbConst.getBaseUrl(), Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, HbConst.stringFromJNI(), Toast.LENGTH_SHORT).show();


    }
}