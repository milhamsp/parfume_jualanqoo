package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class act_register extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email,pass,nama,telp,alamat;
    String userID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_register);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        telp = findViewById(R.id.telp);
        alamat = findViewById(R.id.alamat);
        nama = findViewById(R.id.nama);
    }

    public void btn_register(View view) {
        add_user();
    }

    private void add_user(){
        String email_r = email.getText().toString();
        String pass_r = pass.getText().toString();
        String nama_r = nama.getText().toString();
        String telp_r = telp.getText().toString();
        String alamat_r = alamat.getText().toString();
        CollectionReference user = db.collection("user");
        if(!nama_r.isEmpty()){
            if(!alamat_r.isEmpty()){
                if(!telp_r.isEmpty()){
                    if(!email_r.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_r).matches()){
                        if(!pass_r.isEmpty() && pass_r.length()>=8){
                            auth.createUserWithEmailAndPassword(email_r,pass_r).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(act_register.this,"Akun sudah terdaftar silahkan Login",Toast.LENGTH_SHORT).show();
                                    userID = auth.getCurrentUser().getUid();
                                    Map<String,Object> data=new HashMap<>();
                                    data.put("nama",nama_r);
                                    data.put("alamat",alamat_r);
                                    data.put("telp",telp_r);
                                    data.put("email",email_r);
                                    data.put("password",pass_r);
                                    data.put("id_user",userID);
                                    data.put("role","user");
                                    user.document(userID).set(data);
                                    startActivity(new Intent(act_register.this,act_login.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(act_register.this,"Terjadi kesalahan saat mendaftar",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            pass.setError("Password harus lebih dari 7 karakter!");
                        }
                    }else if(email_r.isEmpty()){
                        email.setError("Email harus diisi!");
                    }else{
                        email.setError("Email tidak benar!");
                    }
                }else{
                    telp.setError("No. Telepon harus diisi!");
                }
            }else{
                alamat.setError("Alamat harus diisi!");
            }
        }else{
            nama.setError("Nama harus diisi!");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),act_login.class));
        finish();
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),act_login.class));
        finish();
    }
    public void btn_tologin(View view) {
        startActivity(new Intent(getApplicationContext(),act_login.class));
        finish();
    }
}