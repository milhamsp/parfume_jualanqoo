package com.android.parfume_jualanqoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class adapter_botol extends FirestoreRecyclerAdapter<model_parfum, adapter_botol.holder_parfum>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private OnItemClickListener listener;
    public adapter_botol(@NonNull FirestoreRecyclerOptions<model_parfum> options) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_act_list_botol,parent,false);
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
