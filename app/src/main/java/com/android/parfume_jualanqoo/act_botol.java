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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class act_botol extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference produk = db.collection("botol");
    private adapter_botol adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_act_botol);
        setUpRecycleView();
    }

    private void setUpRecycleView() {
        Query query = db.collection("botol")/*.whereGreaterThan("stok_produk",0)*/;
        FirestoreRecyclerOptions<model_parfum> options = new FirestoreRecyclerOptions.Builder<model_parfum>()
                .setQuery(query,model_parfum.class).build();
        adapter = new adapter_botol(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_botol);
        /*recyclerView.setHasFixedSize(true);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new adapter_botol.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                model_parfum model = documentSnapshot.toObject(model_parfum.class);
                String id = documentSnapshot.getId();
                String nama_p = documentSnapshot.getString("nama_produk");
                String deskripsi_p = documentSnapshot.getString("deskripsi_produk");
                String tipe_p = documentSnapshot.getString("tipe_produk");
                String harga_p = String.valueOf(documentSnapshot.getLong("harga_produk"));
                String stok_p = String.valueOf(documentSnapshot.getLong("stok_produk"));
                String path = documentSnapshot.getReference().getPath();
                /*Toast.makeText(act_parfum.this,"Posisi: "+position+ "ID: "+id,Toast.LENGTH_SHORT).show();
                Log.d("klik","tertekan "+id+", posisi "+position+", produk "+nama_p);*/
                CollectionReference user = db.collection("user");
                String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                user.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        assert doc != null;
                        String role = doc.getString("role");
                        if(role.equals("admin")){
                            Intent intent = new Intent(act_botol.this,act_produk_ed.class);
                            intent.putExtra("nama",nama_p);
                            intent.putExtra("id",id);
                            intent.putExtra("harga",harga_p);
                            intent.putExtra("stok",stok_p);
                            intent.putExtra("deskripsi",deskripsi_p);
                            intent.putExtra("tipe",tipe_p);
                            startActivity(intent);
                            finish();
                        }else if(role.equals("user")){
                            Intent intent = new Intent(act_botol.this,act_produk.class);
                            intent.putExtra("nama",nama_p);
                            intent.putExtra("id",id);
                            intent.putExtra("harga",harga_p);
                            intent.putExtra("stok",stok_p);
                            intent.putExtra("deskripsi",deskripsi_p);
                            intent.putExtra("tipe",tipe_p);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void btn_tohome(View view) {
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
    }

    @Override
    public void onBackPressed() {
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
    }
}