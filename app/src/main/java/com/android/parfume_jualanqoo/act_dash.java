package com.android.parfume_jualanqoo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class act_dash extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dash);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_dash.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Apakah ingin keluar dari aplikasi?")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(act_dash.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void btn_logout(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_dash.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Yakin ingin Logout?")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                })
                .setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void btn_add(View view) {
        startActivity(new Intent(getApplicationContext(),act_add.class));
        finish();
    }

    public void btn_parfum_adm(View view) {
        startActivity(new Intent(getApplicationContext(),act_parfum.class));
        finish();
    }

    public void btn_botol_adm(View view) {
        startActivity(new Intent(getApplicationContext(),act_botol.class));
        finish();
    }

    public void btn_transaksi(View view) {
        startActivity(new Intent(getApplicationContext(),act_transaksi.class));
        finish();
    }
}