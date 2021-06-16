package com.android.parfume_jualanqoo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class act_add extends AppCompatActivity {
    private EditText nama,deskripsi,stok,harga,tipe;
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_add);
        auth = FirebaseAuth.getInstance();
        deskripsi = findViewById(R.id.deskripsi);
        stok = findViewById(R.id.stok);
        harga = findViewById(R.id.harga);
        nama = findViewById(R.id.nama);
        tipe = findViewById(R.id.tipe);
    }

    private void add_product(){
        String deskripsi_r = deskripsi.getText().toString();
        int stok_r = Integer.valueOf(stok.getText().toString());
        String nama_r = nama.getText().toString();
        int harga_r = Integer.valueOf(harga.getText().toString());
        String tipe_r = tipe.getText().toString();
        String harga_s = String.valueOf(harga_r);
        String stok_s = String.valueOf(stok_r);
        if(!nama_r.isEmpty()){
            if(!stok_s.isEmpty()){
                if(!deskripsi_r.isEmpty()){
                    if(!harga_s.isEmpty()){
                        if(!tipe_r.isEmpty()){
                            if(tipe_r.equals("Botol")||tipe_r.equals("botol")){
                                CollectionReference produk = db.collection("botol");
                                Map<String,Object> data=new HashMap<>();
                                produk.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        String productID = documentReference.getId();
                                        Map<String,Object> isi=new HashMap<>();
                                        isi.put("nama_produk",nama_r);
                                        isi.put("deskripsi_produk",deskripsi_r);
                                        isi.put("tipe_produk",tipe_r);
                                        isi.put("stok_produk",stok_r);
                                        isi.put("harga_produk",harga_r);
                                        isi.put("id_produk",productID);
                                        produk.document(productID).set(isi);
                                        Toast.makeText(act_add.this,"Data produk sudah ditambahkan!",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(act_add.this,act_dash.class));
                                        finish();
                                    }
                                });
                            }else if(tipe_r.equals("Parfum")||tipe_r.equals("parfum")){
                                CollectionReference produk = db.collection("parfum");
                                Map<String,Object> data=new HashMap<>();
                                produk.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        String productID = documentReference.getId();
                                        Map<String,Object> isi=new HashMap<>();
                                        isi.put("nama_produk",nama_r);
                                        isi.put("deskripsi_produk",deskripsi_r);
                                        isi.put("tipe_produk",tipe_r);
                                        isi.put("stok_produk",stok_r);
                                        isi.put("harga_produk",harga_r);
                                        isi.put("id_produk",productID);
                                        produk.document(productID).set(isi);
                                        Toast.makeText(act_add.this,"Data produk sudah ditambahkan!",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(act_add.this,act_dash.class));
                                        finish();
                                    }
                                });
                            }
                        }else {
                            tipe.setError("Tipe produk harus diisi!");
                        }
                    }else{
                        harga.setError("Harga produk harus diisi!");
                    }
                }else{
                    deskripsi.setError("Deskripsi produk harus diisi!");
                }
            }else{
                stok.setError("Stok produk harus diisi!");
            }
        }else{
            nama.setError("Nama produk harus diisi!");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),act_dash.class));
        finish();
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),act_dash.class));
        finish();
    }
    public void btn_tologin(View view) {
        startActivity(new Intent(getApplicationContext(),act_login.class));
        finish();
    }

    public void btn_tambah(View view) {
        add_product();
    }
}