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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class adapter_parfum extends FirestoreRecyclerAdapter<model_parfum, adapter_parfum.holder_parfum>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnItemClickListener listener;
    public adapter_parfum(@NonNull FirestoreRecyclerOptions<model_parfum> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder_parfum holder, int position, @NonNull model_parfum model) {
        holder.nama_parfum.setText(model.getNama_produk());
        holder.stok_parfum.setText(model.getStok_produk()+"");
        holder.harga_parfum.setText(model.getHarga_produk()+"");
    }

    @NonNull
    @Override
    public holder_parfum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_list_parfum,parent,false);
        return new holder_parfum(view);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class holder_parfum extends RecyclerView.ViewHolder{
        TextView nama_parfum,stok_parfum,harga_parfum;
        public holder_parfum(@NonNull View itemView) {
            super(itemView);
            nama_parfum = itemView.findViewById(R.id.nama_parfum);
            harga_parfum = itemView.findViewById(R.id.harga_parfum);
            stok_parfum = itemView.findViewById(R.id.stok_parfum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
