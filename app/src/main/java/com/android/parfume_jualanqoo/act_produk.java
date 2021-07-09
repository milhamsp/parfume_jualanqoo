package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.os.Bundle;

public class act_produk extends AppCompatActivity {
    TextView nama,harga,deskripsi,stok,tipe;
    EditText jumlah;
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String nama_produk,id_produk,deskripsi_produk,tipe_produk,stok_produk,harga_produk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_produk);
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

    public void btn_beli(View view) {
        auth = FirebaseAuth.getInstance();
        jumlah = findViewById(R.id.jumlah);
        String jumlah_s = jumlah.getText().toString();
        if(!jumlah_s.isEmpty()){
            int jumlah_r = Integer.parseInt(jumlah_s);
            int stok_r = Integer.parseInt(stok_produk);
            int sisa_r = Integer.parseInt(stok_produk)-jumlah_r;
            int total = Integer.parseInt(harga_produk)*jumlah_r;
            int ongkir = 15000;
            int totalan = ongkir+total;
            if(jumlah_r > stok_r){
                jumlah.setError("Jumlah pesanan melebihi stok!");
            }else{
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_produk.this);
                        alertDialogBuilder.setTitle("Konfirmasi");
                        alertDialogBuilder
                                .setMessage("Apakah anda yakin ingin membeli produk ini?" +
                                        "\nJumlah Pesanan: "+jumlah_s+
                                        "\nBiaya Pengiriman: "+ongkir+
                                        "\nTotal Biaya: "+totalan)
                                .setIcon(R.drawable.icon)
                                .setCancelable(false)
                                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        CollectionReference transaksi = db.collection("transaksi");
                                        Map<String,Object> data=new HashMap<>();
                                        transaksi.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                String transactionID = documentReference.getId();
                                                String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                                Map<String,Object> isi=new HashMap<>();
                                                isi.put("id_transaksi",transactionID);
                                                isi.put("nama_produk",nama_produk);
                                                isi.put("tipe_produk",tipe_produk);
                                                isi.put("jumlah_pesanan",jumlah_r);
                                                isi.put("tanggal_pesan",tanggal);
                                                isi.put("total",totalan);
                                                isi.put("id_user",userID);
                                                isi.put("nama_user",nama_u);
                                                isi.put("telp_user",telp_u);
                                                isi.put("alamat_user",alamat_u);
                                                transaksi.document(transactionID).set(isi);
                                                if(tipe_produk.equals("botol") || tipe_produk.equals("Botol")){
                                                    CollectionReference produk = db.collection("botol");
                                                    produk.document(id_produk).update("stok_produk",sisa_r);
                                                }else if(tipe_produk.equals("parfum") || tipe_produk.equals("Parfum")){
                                                    CollectionReference produk = db.collection("parfum");
                                                    produk.document(id_produk).update("stok_produk",sisa_r);
                                                }
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act_produk.this);
                                                alertDialogBuilder.setTitle("Notifikasi");
                                                alertDialogBuilder
                                                        .setMessage("Transaksi Berhasil! Silahkan Transfer sejumlah "
                                                                +totalan+" ke Rekening dibawah ini:\n6871507420" +
                                                                "\na/n: Farhan Difa\n\nSilahkan Upload Bukti Bayar untuk konfirmasi Pembayaran, terimakasih" +
                                                                "\n\nApabila mengalami kendala silakan klik icon '?' pada halaman utama User")
                                                        .setIcon(R.drawable.icon)
                                                        .setCancelable(false)
                                                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                                startActivity(new Intent(act_produk.this,act_home.class));
                                                                finish();
                                                            }
                                                        });
                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Toast.makeText(act_produk.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
            }
        }else{
            jumlah.setError("Jumlah pesanan harus diisi!");
        }
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }

    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }
}