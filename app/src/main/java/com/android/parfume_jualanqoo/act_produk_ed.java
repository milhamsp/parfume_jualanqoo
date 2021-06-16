package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import android.os.Bundle;

public class act_produk_ed extends AppCompatActivity {
    EditText nama,harga,deskripsi,stok,tipe;
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String nama_produk,id_produk,deskripsi_produk,tipe_produk,stok_produk,harga_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_produk_ed);
        Intent intent = getIntent();
        nama_produk = intent.getStringExtra("nama");
        id_produk = intent.getStringExtra("id");
        deskripsi_produk = intent.getStringExtra("deskripsi");
        tipe_produk = intent.getStringExtra("tipe");
        stok_produk = intent.getStringExtra("stok");
        harga_produk = intent.getStringExtra("harga");
        nama = findViewById(R.id.nama);
        nama.setText(nama_produk);
        harga = findViewById(R.id.harga);
        harga.setText(harga_produk);
        deskripsi = findViewById(R.id.deskripsi);
        deskripsi.setText(deskripsi_produk);
        stok = findViewById(R.id.stok);
        stok.setText(stok_produk);
        tipe = findViewById(R.id.tipe);
        tipe.setText(tipe_produk);
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),act_dash.class));
        finish();
    }

    public void btn_update(View view) {
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
                                Map<String,Object> isi=new HashMap<>();
                                isi.put("nama_produk",nama_r);
                                isi.put("deskripsi_produk",deskripsi_r);
                                isi.put("tipe_produk",tipe_r);
                                isi.put("stok_produk",stok_r);
                                isi.put("harga_produk",harga_r);
                                isi.put("id_produk",id_produk);
                                produk.document(id_produk).set(isi);
                            }else if(tipe_r.equals("Parfum")||tipe_r.equals("parfum")){
                                CollectionReference produk = db.collection("parfum");
                                Map<String,Object> isi=new HashMap<>();
                                isi.put("nama_produk",nama_r);
                                isi.put("deskripsi_produk",deskripsi_r);
                                isi.put("tipe_produk",tipe_r);
                                isi.put("stok_produk",stok_r);
                                isi.put("harga_produk",harga_r);
                                isi.put("id_produk",id_produk);
                                produk.document(id_produk).set(isi);
                            }
                            Toast.makeText(act_produk_ed.this,"Data produk sudah diperbarui!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),act_dash.class));
                            finish();
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

    public void btn_delete(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_produk_ed.this);
        alertDialogBuilder.setTitle("Konfirmasi");
        alertDialogBuilder
                .setMessage("Yakin ingin menghapus produk ini?")
                .setIcon(R.drawable.icon)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String tipe_r = tipe.getText().toString();
                        if(tipe_r.equals("Botol")||tipe_r.equals("botol")){
                            db.collection("botol").document(id_produk).delete();
                        }else if(tipe_r.equals("Parfum")||tipe_r.equals("parfum")){
                            db.collection("parfum").document(id_produk).delete();
                        }
                        Toast.makeText(getApplicationContext(),"Data produk sudah dihapus!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),act_parfum.class));
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
}