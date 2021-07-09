package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class adapter_bukti extends FirestoreRecyclerAdapter<model_bukti,adapter_bukti.holder_img>{
    private Context context;
    private List <act_upload> upload;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapter_bukti(@NonNull FirestoreRecyclerOptions<model_bukti> options) {
        super(options);
    }

    /*public adapter_bukti(Context context,List<act_upload> upload){
        context = context;
        upload = upload;
    }*/

    @NonNull
    @Override
    public holder_img onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_list_bukti,parent,false);
        return new holder_img(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder_img holder, int position, @NonNull model_bukti model) {
        holder.nama_gambar.setText(model.getNama_gambar());
        Picasso.get().load(model.getUrl_gambar()).fit().centerCrop().into(holder.image);
        /*holder.image.setImageURI(model.getUrl_gambar());
        /*Picasso.with(getContext())
                .load(model.getUrl_gambar())
                .fit()
                .centerCrop()
                .into(holder.image);*/
    }

    private Context getContext() {
        context = context;
        return context;
    }

    private List<act_upload> getUpload(){
        upload = upload;
        return upload;
    }

    public class holder_img extends RecyclerView.ViewHolder{
        public TextView nama_gambar;
        public ImageView image;

        public holder_img(@NonNull View itemView) {
            super(itemView);
            nama_gambar = itemView.findViewById(R.id.nama_gambar);
            image = itemView.findViewById(R.id.image);
        }
    }

}
