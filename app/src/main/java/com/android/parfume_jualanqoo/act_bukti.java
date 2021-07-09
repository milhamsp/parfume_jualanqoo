package com.android.parfume_jualanqoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class act_bukti extends AppCompatActivity {
    private adapter_bukti adapter;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference produk = db.collection("parfum");
    private List<act_upload> upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_bukti);
        auth = FirebaseAuth.getInstance();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = db.collection("upload");/*.whereGreaterThan("stok_produk",0)*/
        FirestoreRecyclerOptions<model_bukti> options = new FirestoreRecyclerOptions.Builder<model_bukti>()
                .setQuery(query,model_bukti.class).build();
        adapter = new adapter_bukti(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_bukti);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( adapter != null ) {
            adapter.stopListening();
        }
    }

    public void btn_tohome(View view) {
        startActivity(new Intent(getApplicationContext(),act_dash.class));
        finish();
    }
}