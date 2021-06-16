package com.android.parfume_jualanqoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class act_faq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_faq);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }

    public void btn_tohome(View view) {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }
}