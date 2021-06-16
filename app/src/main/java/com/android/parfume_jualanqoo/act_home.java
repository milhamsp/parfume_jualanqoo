package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class act_home extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView nama,telp,alamat;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home);
        auth = FirebaseAuth.getInstance();
        CollectionReference user = db.collection("user");
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        user.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                assert doc != null;
                String nama_u = doc.getString("nama");
                String telp_u = doc.getString("telp");
                String alamat_u = doc.getString("alamat");
                nama = findViewById(R.id.nama);
                nama.setText(nama_u);
                telp = findViewById(R.id.telp);
                telp.setText(telp_u);
                alamat = findViewById(R.id.alamat);
                alamat.setText(alamat_u);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_home.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Apakah ingin keluar dari aplikasi?")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(act_home.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void btn_botol(View view) {
        startActivity(new Intent(getApplicationContext(),act_botol.class));
        finish();
    }

    public void btn_logout(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_home.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Yakin ingin Logout?")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void btn_parfum(View view) {
        startActivity(new Intent(getApplicationContext(),act_parfum.class));
        finish();
    }

    public void btn_orderhistory(View view) {
        startActivity(new Intent(getApplicationContext(),act_transaksi.class));
        finish();
    }

    public void btn_upload(View view) {
        startActivity(new Intent(getApplicationContext(),act_upload.class));
        finish();
    }

    public void btn_faq(View view) {
        startActivity(new Intent(getApplicationContext(),act_faq.class));
        finish();
    }
}