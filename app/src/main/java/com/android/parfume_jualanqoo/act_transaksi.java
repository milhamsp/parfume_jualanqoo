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

public class act_transaksi extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    adapter_transaksi adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_act_transaksi);
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        if(userID.equals("TOzDAODpoLPajrALIfBUnGVYCnY2")){
            setUpRecycleView_adm();
        }else{
            setUpRecycleView();
        }
    }

    private void setUpRecycleView_adm() {
        Query query = db.collection("transaksi");
        FirestoreRecyclerOptions<model_transaksi> options = new FirestoreRecyclerOptions.Builder<model_transaksi>()
                .setQuery(query, model_transaksi.class).build();
        adapter = new adapter_transaksi(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_transaksi);
        /*recyclerView.setHasFixedSize(true);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setUpRecycleView() {
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Query query = db.collection("transaksi").whereEqualTo("id_user",userID);
        FirestoreRecyclerOptions<model_transaksi> options = new FirestoreRecyclerOptions.Builder<model_transaksi>()
                .setQuery(query, model_transaksi.class).build();
        adapter = new adapter_transaksi(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_transaksi);
        /*recyclerView.setHasFixedSize(true);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
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