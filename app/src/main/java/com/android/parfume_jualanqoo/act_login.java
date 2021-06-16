package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class act_login extends AppCompatActivity{
    private FirebaseAuth auth;
    private EditText email,pass;
    private Button btn_login;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            CollectionReference user = db.collection("user");
            String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            user.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;
                    String role = doc.getString("role");
                    if(role.equals("admin")){
                        startActivity(new Intent(getApplicationContext(),act_dash.class));
                        finish();
                    }else if(role.equals("user")){
                        startActivity(new Intent(getApplicationContext(),act_home.class));
                        finish();
                    }
                }
            });
        }else{
            setContentView(R.layout.activity_act_login);
            email = findViewById(R.id.email);
            pass = findViewById(R.id.password);
            btn_login = findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
        }
    }

    private void login(){
        String email_v = email.getText().toString();
        String pass_v = pass.getText().toString();
        CollectionReference user = db.collection("user");
        if(!email_v.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_v).matches()){
            if(!pass_v.isEmpty()){
                auth.signInWithEmailAndPassword(email_v,pass_v)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(act_login.this,"Login Berhasil!",Toast.LENGTH_SHORT).show();
                                String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                user.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot doc = task.getResult();
                                        assert doc != null;
                                        String role = doc.getString("role");
                                        if(role.equals("admin")){
                                            startActivity(new Intent(getApplicationContext(),act_dash.class));
                                            finish();
                                        }else if(role.equals("user")){
                                            startActivity(new Intent(getApplicationContext(),act_home.class));
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(act_login.this,"Login Gagal!",Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                pass.setError("Password harus diisi!");
            }
        }else if(email_v.isEmpty()){
            email.setError("Email harus diisi!");
        }else{
            email.setError("Isi email dengan benar!");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_login.this);
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
                        Toast.makeText(act_login.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),act_register.class));
        finish();
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }
}